package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class EditBookDialogPresenter implements Presenter {

    public interface Display {
        Button getCancelButton();

        Button getSubmitButton();

        void showDialog();

        void hideDialog();

        Widget asWidget();

        void showData(Book object);

        String getNewName();

    }

    private ListDataProvider<Book> dataProvider;

    private Book book;

    private Display display;

    private BookWebService bookWebService;

    private String oldName;

    public EditBookDialogPresenter(Display display, BookWebService bookWebService, ListDataProvider<Book> dataProvider, Book book) {
        this.book = book;
        this.dataProvider = dataProvider;
        this.display = display;
        this.bookWebService = bookWebService;
        bind();
    }

    @Override
    public void go(Panel container) {
        display.showDialog();
    }

    private void bind() {

        if (!dataProvider.getList().isEmpty()) {
            display.showData(book);
            oldName = book.getName();
        }

        display.getSubmitButton().addClickHandler(event -> {
            String newName = display.getNewName().trim();
            if (!oldName.equals(newName) && !newName.isEmpty()) {
                Book newBook = new Book(book.getId(), newName);

                bookWebService.updateBook(newBook, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateBook doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        book.setName(newName);
                        dataProvider.refresh();
                        display.hideDialog();
                    }
                });
            }
        });

        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
