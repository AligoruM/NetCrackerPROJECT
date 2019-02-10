package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.client.view.dialogs.AddAuthorDialogView;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class AddBookDialogPresenter implements Presenter {

    public interface Display {
        HasClickHandlers getSubmitButton();

        HasClickHandlers getCancelButton();

        HasClickHandlers getPlusButton();

        void showDialog();

        void hideDialog();

        List<String> getAddInfo();

        ListBox getListBox();

        void setListBoxData(List<String> suggestions);
    }

    private AddAuthorDialogPresenter addAuthorDialogPresenter;

    private List<String> authorList = new ArrayList<>();

    private final Display display;
    private final BookWebService bookService;
    private final BookTabPresenter bookTabPresenter;

    public AddBookDialogPresenter(Display view, BookWebService bookService, BookTabPresenter bookTabPresenter) {
        this.bookService = bookService;
        this.display = view;
        this.bookTabPresenter = bookTabPresenter;
        addAuthorDialogPresenter = new AddAuthorDialogPresenter(new AddAuthorDialogView(), bookService);
    }

    @Override
    public void go(Panel container) {
        bind();
        display.showDialog();
    }

    private void bind() {
        display.getListBox().addFocusHandler(event -> display.setListBoxData(authorList));

        bookService.getAllAuthor(new MethodCallback<List<String>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("AuthorNames doesnt work");
            }

            @Override
            public void onSuccess(Method method, List<String> strings) {
                authorList=strings;
                display.setListBoxData(authorList);
            }
        });

        display.getPlusButton().addClickHandler(event -> {
            addAuthorDialogPresenter.setAuthors(authorList);
            addAuthorDialogPresenter.go(RootPanel.get());
        });

        display.getSubmitButton().addClickHandler(event -> {
            List<String> tmp = display.getAddInfo();
            if (tmp.size() == 2) {
                String name = tmp.get(0);
                String author = tmp.get(1);
                if (name != null && !name.isEmpty() && author != null && !author.isEmpty()) {
                    bookService.addBook(tmp, new MethodCallback<Book>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {
                            GWT.log("Adding doesnt work", throwable);
                        }

                        @Override
                        public void onSuccess(Method method, Book book) {
                            GWT.log(method.getResponse().getText());
                            if(book!=null) {
                                bookTabPresenter.getBookListDataProvider().getList().add(book);
                                display.hideDialog();
                            }else {
                                Window.alert("Book is null. Item already exist or problem with access to database");
                            }
                        }
                    });
                } else {
                    Window.alert("Fields cannot be empty!");
                }
            }
        });

        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
