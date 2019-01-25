package catalogApp.client.presenter;

import catalogApp.client.event.ClosedDialogEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
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

        Widget asWidget();

        void setSuggestions(List<String> suggestions);
    }

    private final HandlerManager eventBus;
    private final Display display;
    private final BookWebService bookService;

    public AddBookDialogPresenter(Display view, BookWebService bookService, HandlerManager eventBus) {
        this.bookService = bookService;
        this.eventBus = eventBus;
        this.display = view;
    }

    @Override
    public void go(DockPanel container) {
        bind();
        RootPanel.get().add(display.asWidget());
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
            //TODO validation
            List<String> tmp = display.getAddInfo();
            bookService.addBook(tmp, new MethodCallback<Book>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log("Adding doesnt work", throwable);
                }

                @Override
                public void onSuccess(Method method, Book book) {
                    BookTabPresenter.getBookListDataProvider().getList().add(book);
                    display.hideDialog();
                    eventBus.fireEvent(new ClosedDialogEvent());
                }
            });
        });

        display.getCancelButton().addClickHandler(event -> {
            eventBus.fireEvent(new ClosedDialogEvent());
            display.hideDialog();
        });
    }
}
