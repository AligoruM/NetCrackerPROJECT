package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookTabPresenter implements Presenter {
    public interface Display {

        void setDataProviderAndInitialize(ListDataProvider<Book> dataProvider);

        MultiSelectionModel<Book> getSelectionModel();

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

        /*bookService.getAllBooks(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log("getAllBooks doesnt work", throwable);
            }

            @Override
            public void onSuccess(Method method, List<Book> songs) {
                bookListDataProvider.getList().addAll(songs);
                loaded = true;
            }
        });*/

        bind();
    }

    @Override
    public void go(Panel container) {

    }

    private void bind() {
        display.setDataProviderAndInitialize(bookListDataProvider);
    }

    public List<Integer> getSelectedIDs() {
        List<Integer> tmp = new ArrayList<>();
        display.getSelectedItems().forEach(e -> tmp.add(e.getId()));
        return tmp;
    }

    public Set<Book> getSelectedSet() {
        return display.getSelectedItems();
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

    public ListDataProvider<Book> getBookListDataProvider() {
        return bookListDataProvider;
    }
}
