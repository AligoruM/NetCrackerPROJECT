package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.shared.exception.ItemAlreadyExistException;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class AddBookDialogPresenter implements Presenter {

    public interface Display {
        HasClickHandlers getSubmitButton();

        HasClickHandlers getCancelButton();

        void showDialog();

        void hideDialog();

        List<String> getAddInfo();

        void setSuggestions(List<String> suggestions);
    }

    private final Display display;
    private final BookWebService bookService;
    private final BookTabPresenter bookTabPresenter;

    public AddBookDialogPresenter(Display view, BookWebService bookService, BookTabPresenter bookTabPresenter) {
        this.bookService = bookService;
        this.display = view;
        this.bookTabPresenter = bookTabPresenter;
    }

    @Override
    public void go(Panel container) {
        bind();
        display.showDialog();
    }

    private void bind() {
        bookService.getAllAuthor(new MethodCallback<List<String>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("AuthorNames doesnt work");
            }

            @Override
            public void onSuccess(Method method, List<String> strings) {
                display.setSuggestions(strings);
            }
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
