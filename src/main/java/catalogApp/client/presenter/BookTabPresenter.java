package catalogApp.client.presenter;

import catalogApp.client.event.UpdateUserLibraryEvent;
import catalogApp.client.presenter.helper.FieldUpdaters;
import catalogApp.client.services.BookWebService;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.dialogs.EditBookDialogView;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
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

        AbstractCatalogCellTable<Book> getTable();

        void setListAndInitialize(List<Book> list);

        MultiSelectionModel<Book> getSelectionModel();

        Widget asWidget();
    }

    private ArrayList<Book> list;

    private final Display display;
    private final HandlerManager eventBus;
    private final BookWebService bookWebService;
    private final ListDataProvider<Book> bookListDataProvider = new ListDataProvider<>();

    private boolean isGlobalPanel;

    BookTabPresenter(Display display, HandlerManager eventBus, BookWebService bookWebService) {
        this.display = display;
        this.eventBus = eventBus;
        this.bookWebService = bookWebService;
        display.setListAndInitialize(bookListDataProvider.getList());
        bookListDataProvider.addDataDisplay(display.getTable());
    }

    @Override
    public void go(Panel container) {
        container.add(display.asWidget());
    }

    private void bind() {
        if (!isGlobalPanel) {
            eventBus.addHandler(UpdateUserLibraryEvent.TYPE, event -> {

                if (event.getType() == UpdateUserLibraryEvent.ITEM_TYPE.BOOK) {
                    for (Object x : event.getSelectedItems()) {
                        if (!bookListDataProvider.getList().contains(x)) {
                            bookListDataProvider.getList().add((Book) x);
                        }
                    }
                }
            });
        } else {
            display.getTable().enablePopup();
        }
        display.getSearchField().addKeyUpHandler(event -> {
            ArrayList<Book> foundedBooks = new ArrayList<>();
            String str = display.getSearchField().getText().toLowerCase();
            if (!str.isEmpty()) {
                list.forEach(item -> {
                    if (item.getName().toLowerCase().contains(str) || item.getAuthor().getName().toLowerCase().contains(str)) {
                        foundedBooks.add(item);
                    }else{
                        if(getSelectionModel().isSelected(item)){
                            getSelectionModel().setSelected(item, false);
                        }
                    }
                });
                bookListDataProvider.getList().clear();
                bookListDataProvider.getList().addAll(foundedBooks);
            } else {
                bookListDataProvider.getList().clear();
                bookListDataProvider.getList().addAll(list);
            }
            bookListDataProvider.refresh();
        });

    }

    List<Integer> getSelectedIDs() {
        List<Integer> tmp = new ArrayList<>();
        display.getSelectionModel().getSelectedSet().forEach(e -> tmp.add(e.getId()));
        return tmp;
    }

    Set<Book> getSelectedSet() {
        return display.getSelectionModel().getSelectedSet();
    }

    void loadData(boolean isGlobal) {
        isGlobalPanel = isGlobal;
        if (isGlobal) {
            bookWebService.getAllBooks(new MethodCallback<List<Book>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log("getAllBooks doesnt work", throwable);
                }

                @Override
                public void onSuccess(Method method, List<Book> books) {
                    bookListDataProvider.getList().addAll(books);
                    list = new ArrayList<>(books);
                    bind();
                }
            });
        } else {
            bookWebService.getUserBooks(new MethodCallback<List<Book>>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("getUserBooks doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, List<Book> books) {
                    bookListDataProvider.getList().addAll(books);
                    list = new ArrayList<>(books);
                    bind();
                }
            });
        }
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

    void doRestoreBooks() {
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
    MultiSelectionModel<Book> getSelectionModel(){
        return display.getSelectionModel();
    }

    public Display getDisplay() {
        return display;
    }
}
