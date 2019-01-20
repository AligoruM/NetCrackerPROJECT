package catalogApp.client.presenter;

import catalogApp.client.event.AddBookEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.shared.model.Book;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class BookTabPresenter implements Presenter {
    public interface Display {
        HasClickHandlers getAddButton();

        void setDataProviderAndInitialize(ListDataProvider<Book> dataProvider);

        Widget asWidget();
    }

    private boolean loaded = false;
    private final Display display;
    private final HandlerManager eventBus;
    private final BookWebService bookService;
    private final static ListDataProvider<Book> bookListDataProvider = new ListDataProvider<>();

    public BookTabPresenter(Display display, HandlerManager eventBus, BookWebService bookService) {
        this.display = display;
        this.eventBus = eventBus;
        this.bookService = bookService;
    }

    @Override
    public void go(HasWidgets container) {
        bind();
    }

    private void bind() {
        display.setDataProviderAndInitialize(bookListDataProvider);
        display.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddBookEvent()));
    }

    public void loadData() {
        if (!loaded) {
            bookService.getAllBooks(new MethodCallback<List<Book>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    StringBuilder string = new StringBuilder();
                    for (StackTraceElement element : throwable.getStackTrace()) {
                        string.append(element).append("\n");
                    }
                    Window.alert("getAllBooks doesnt work\n" + throwable.getMessage() + "\n" + string);

                }

                @Override
                public void onSuccess(Method method, List<Book> songs) {
                    bookListDataProvider.getList().addAll(songs);
                    loaded = true;
                }
            });
        }
    }

    public static ListDataProvider<Book> getBookListDataProvider() {
        return bookListDataProvider;
    }
}
