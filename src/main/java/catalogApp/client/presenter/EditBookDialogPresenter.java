package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.server.dao.constants.Types;
import catalogApp.shared.model.BaseObject;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Type;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;

import static catalogApp.client.view.constants.LibraryConstants.AUTHOR_LABEL;

public class EditBookDialogPresenter implements Presenter {

    public interface Display {
        Button getCancelButton();

        Button getSubmitButton();

        void showDialog();

        void hideDialog();

        FlexTable getTable();

        Widget asWidget();

        void showData(BaseObject object);

        String getNewName();

    }

    private ListDataProvider<Book> dataProvider;

    private Book book;

    private Display display;

    private BookWebService bookWebService;

    private TextBox authorBox = new TextBox();

    private String oldName;

    public EditBookDialogPresenter(Display display, BookWebService bookWebService, ListDataProvider<Book> dataProvider, Book book) {
        this.book = book;
        this.dataProvider = dataProvider;
        this.display = display;
        this.bookWebService = bookWebService;
    }

    @Override
    public void go(Panel container) {
        authorBox.setEnabled(false);
        display.getTable().setWidget(2, 0, new Label(AUTHOR_LABEL));
        display.getTable().setWidget(2, 1, authorBox);

        bind();

        display.showDialog();
    }

    private void bind() {

        if (!dataProvider.getList().isEmpty()) {
            display.showData(book);
            authorBox.setText(book.getName());
            oldName = book.getName();
        }

        display.getSubmitButton().addClickHandler(event -> {
            String newName = display.getNewName().trim();
            if (!(oldName.equals(newName) || newName.isEmpty())) {
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
