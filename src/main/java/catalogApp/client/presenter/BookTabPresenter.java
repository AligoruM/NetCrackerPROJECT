package catalogApp.client.presenter;

import catalogApp.client.event.UpdateUserLibraryEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.dialogs.EditBookDialogView;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
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

        TextBox getSearchField();

        Button getSearchButton();

        AbstractCatalogCellTable getTable();

        void setDataProviderAndInitialize(ListDataProvider<Book> dataProvider, boolean popupEnabled);

        MultiSelectionModel<Book> getSelectionModel();

        Widget asWidget();
    }
    ArrayList<Book> list;

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
        display.setDataProviderAndInitialize(bookListDataProvider, true);
        bookListDataProvider.getList();

        display.getSearchField().addKeyUpHandler(event -> {
            ArrayList<Book> foundedBooks = new ArrayList<>();
            String str = display.getSearchField().getText();
            if(!str.isEmpty()) {
                list.forEach(item->{
                    if(item.getName().contains(str) || item.getAuthor().getName().contains(str)){
                        foundedBooks.add(item);
                    }
                });
                bookListDataProvider.setList(foundedBooks);
            }else {
                bookListDataProvider.setList(list);
            }
            bookListDataProvider.refresh();
        });

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
                list = new ArrayList<>(songs);
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
        } else {
            Window.alert("Select only one item!");
        }
    }

    void doArchiveBooks() {
        List<Integer> selectedIds = getSelectedIDs();
        if (selectedIds.size() > 0) {
            bookWebService.archiveBooks(selectedIds, new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("restoreBooks doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, Void response) {
                    bookListDataProvider.getList().forEach(item -> {
                        if (selectedIds.contains(item.getId())) {
                            item.setArchived(true);
                        }
                        display.getSelectionModel().clear();
                    });
                }
            });
        }
    }

    void doRestoreBooks(){
        List<Integer> selectedIds = getSelectedIDs();
        if (selectedIds.size() > 0) {
            bookWebService.restoreBooks(selectedIds, new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("restoreBooks doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, Void response) {
                    bookListDataProvider.getList().forEach(item -> {
                        if (selectedIds.contains(item.getId())) {
                            item.setArchived(false);
                        }
                        display.getSelectionModel().clear();
                    });
                }
            });
        }
    }

    ListDataProvider<Book> getBookListDataProvider() {
        return bookListDataProvider;
    }
}
