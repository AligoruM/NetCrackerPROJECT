package catalogApp.client.presenter;

import catalogApp.client.event.AddBookEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.client.view.components.AbstractCatalogCellTable;
import catalogApp.shared.model.Book;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
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
        void setDataProviderAndInitialize(ListDataProvider<Book> dataProvider);
        Widget asWidget();
    }

    private final Display display;
    private final HandlerManager eventBus;
    private final BookWebService bookService;

    private final ListDataProvider<Book> bookListDataProvider = new ListDataProvider<>();

    public BookTabPresenter(Display display, HandlerManager eventBus, BookWebService bookService) {
        this.display = display;
        this.eventBus = eventBus;
        this.bookService = bookService;
    }

    @Override
    public void go(HasWidgets container) {
        bind();
    }

    private void bind(){
            display.setDataProviderAndInitialize(bookListDataProvider);
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

        display.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddBookEvent()));
    }

}
