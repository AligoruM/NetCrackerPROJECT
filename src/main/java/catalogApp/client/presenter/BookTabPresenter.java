package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.event.AddBookEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookTabPresenter implements Presenter {
    public interface Display {
        HasClickHandlers getAddButton();

        void setDataProviderAndInitialize(ListDataProvider<Book> dataProvider);

        Set<Book> getSelectedItems();

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
        if(CatalogController.isAdmin()){
            display.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddBookEvent()));
        }else {
            display.getAddButton().addClickHandler(event -> {
                List<Integer> tmp = new ArrayList<>();
                display.getSelectedItems().forEach(e-> tmp.add(e.getId()));
                bookService.addBooksToUserLib(tmp, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("addBookToLib doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        Window.alert("Added to user's lib");
                    }
                });
            });
        }
    }

    public void loadData() {
        if (!loaded) {
            bookService.getAllBooks(new MethodCallback<List<Book>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log("getAllBooks doesnt work", throwable);
                }

                @Override
                public void onSuccess(Method method, List<Book> songs) {
                    bookListDataProvider.getList().addAll(songs);
                    loaded = true;
                }
            });
        }
    }

    static ListDataProvider<Book> getBookListDataProvider() {
        return bookListDataProvider;
    }
}
