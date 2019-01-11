package catalogApp.client.presenter;

import catalogApp.client.event.AddBookEvent;
import catalogApp.client.services.TestService;
import catalogApp.shared.model.Book;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class BookTabPresenter implements Presenter {
    public interface Display{
        HasClickHandlers getAddButton();
        CellTable<Book> getTable();
        Widget asWidget();
    }

    private final Display display;
    private final HandlerManager eventBus;
    private final TestService bookService;

    private static final ListDataProvider<Book> bookListDataProvider = new ListDataProvider<>();

    public BookTabPresenter(Display display, HandlerManager eventBus, TestService bookService) {
        this.display = display;
        this.eventBus = eventBus;
        this.bookService = bookService;
    }

    @Override
    public void go(HasWidgets container) {
        bind();
        //container.add(display.asWidget());
    }

    private void bind(){

            bookService.getAllBooks(new MethodCallback<List<Book>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert("getAllBooks doesnt work");
                }

                @Override
                public void onSuccess(Method method, List<Book> books) {
                    bookListDataProvider.getList().addAll(books);
                }
            });

            bookListDataProvider.addDataDisplay(display.getTable());

        display.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddBookEvent()));
    }

}