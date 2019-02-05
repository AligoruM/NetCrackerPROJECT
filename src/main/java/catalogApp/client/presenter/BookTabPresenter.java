package catalogApp.client.presenter;

import catalogApp.client.event.UpdateUserLibraryEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.client.view.dialogs.EditBookDialogView;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
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

        Widget asWidget();
    }

    private final Display display;
    private final HandlerManager eventBus;
    private final BookWebService bookWebService;
    private final static ListDataProvider<Book> bookListDataProvider = new ListDataProvider<>();

    BookTabPresenter(Display display, HandlerManager eventBus, BookWebService bookWebService) {
        this.display = display;
        this.eventBus = eventBus;
        this.bookWebService = bookWebService;
        bind();
    }

    @Override
    public void go(Panel container) {

    }

    private void bind() {
        display.setDataProviderAndInitialize(bookListDataProvider);
    }

    private List<Integer> getSelectedIDs() {
        List<Integer> tmp = new ArrayList<>();
        display.getSelectionModel().getSelectedSet().forEach(e -> tmp.add(e.getId()));
        return tmp;
    }

    private Set<Book> getSelectedSet() {
        return display.getSelectionModel().getSelectedSet();
    }

    void loadData() {

        bookWebService.getAllBooks(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log("getAllBooks doesnt work", throwable);
            }

            @Override
            public void onSuccess(Method method, List<Book> songs) {
                bookListDataProvider.getList().addAll(songs);

            }
        });
    }

    void doAddBooksToLib() {
        List<Integer> selectedBooksIDs = getSelectedIDs();
        if (!selectedBooksIDs.isEmpty()) {
            bookWebService.addBooksToUserLib(selectedBooksIDs, new MethodCallback<List<Book>>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("addBookToLib doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, List<Book> response) {
                    GWT.log("Added to user's lib");
                    eventBus.fireEvent(new UpdateUserLibraryEvent(UpdateUserLibraryEvent.ITEM_TYPE.BOOK, response));
                }
            });
        } else {
            GWT.log("nothing selected or all items already are in library");
        }
    }

    void doEditBook() {
        Set<Book> selectedBooks = getSelectedSet();
        if (selectedBooks.size() == 1) {
            new EditBookDialogPresenter(new EditBookDialogView(), bookWebService,
                    bookListDataProvider, (Book) selectedBooks.toArray()[0]).go(null);
        } else Window.alert("Select only one item!");
    }

    ListDataProvider<Book> getBookListDataProvider() {
        return bookListDataProvider;
    }
}
