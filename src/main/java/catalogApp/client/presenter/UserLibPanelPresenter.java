package catalogApp.client.presenter;

import catalogApp.client.event.UpdateUserLibraryEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.components.AbstractCatalogCellTable;
import catalogApp.client.view.mainPage.library.objectViews.BookView;
import catalogApp.client.view.mainPage.library.objectViews.SongView;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class UserLibPanelPresenter implements Presenter {
    public interface Display{
        void setBookDataProvider(ListDataProvider<Book> dataProvider, boolean popupEnabled);
        void setSongDataProvider(ListDataProvider<Song> dataProvider, boolean popupEnabled);

        AbstractCatalogCellTable<Book> getBookTable();
        AbstractCatalogCellTable<Song> getSongTable();

        MultiSelectionModel<Book> getSelectionBooksModel();
        MultiSelectionModel<Song> getSelectionSongsModel();

        Button getDeleteBooksButton();
        Button getDeleteSongsButton();

        SimplePanel getObjectContainer();

        Widget asWidget();
    }

    private Display display;
    private SongWebService songWebService;
    private BookWebService bookWebService;
    private HandlerManager eventBus;

    private ListDataProvider<Book> bookListDataProvider = new ListDataProvider<>();
    private ListDataProvider<Song> songListDataProvider = new ListDataProvider<>();

    private BookPresenter bookPresenter = new BookPresenter(new BookView());
    private SongPresenter songPresenter = new SongPresenter(new SongView());

    public UserLibPanelPresenter(Display display, BookWebService bookWebService, SongWebService songWebService, HandlerManager eventBus) {
        this.display = display;
        this.songWebService = songWebService;
        this.bookWebService = bookWebService;
        this.eventBus = eventBus;

        this.bookWebService.getUserBooks(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getUserBooks doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, List<Book> response) {
                bookListDataProvider.getList().addAll(response);
                display.setBookDataProvider(bookListDataProvider,false);
                setBookFiledHandler();
            }
        });

        this.songWebService.getUserSongs(new MethodCallback<List<Song>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getUserSongs doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, List<Song> response) {
                songListDataProvider.getList().addAll(response);
                display.setSongDataProvider(songListDataProvider, false);
                setSongFieldHandler();
            }
        });
        bind();
    }

    @Override
    public void go(Panel container) {
        container.add(display.asWidget());
    }

    private void bind() {
        eventBus.addHandler(UpdateUserLibraryEvent.TYPE, event -> {
            switch (event.getType()) {
                case BOOK: {
                    for (Object x : event.getSelectedItems()) {
                        if (!bookListDataProvider.getList().contains(x))
                            bookListDataProvider.getList().add((Book) x);
                    }
                    break;
                }
                case SONG: {
                    for (Object x : event.getSelectedItems()) {
                        if (!songListDataProvider.getList().contains(x))
                            songListDataProvider.getList().add((Song) x);
                    }
                    break;
                }
            }
        });

        display.getDeleteBooksButton().addClickHandler(event -> {
            List<Integer> listOfSelectedBooksIds = new ArrayList<>();
            display.getSelectionBooksModel().getSelectedSet().forEach(e -> listOfSelectedBooksIds.add(e.getId()));
            if (!listOfSelectedBooksIds.isEmpty()) {
                bookWebService.deleteBookFromLib(listOfSelectedBooksIds, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("delete book doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        display.getSelectionBooksModel().clear();
                        bookListDataProvider.getList().removeIf(book -> listOfSelectedBooksIds.contains(book.getId()));
                        GWT.log("deleted book ids " + listOfSelectedBooksIds.toString());
                    }
                });
            }
        });

        display.getDeleteSongsButton().addClickHandler(event -> {
            List<Integer> listOfSelectedSongsIds = new ArrayList<>();
            display.getSelectionSongsModel().getSelectedSet().forEach(e -> listOfSelectedSongsIds.add(e.getId()));
            if (!listOfSelectedSongsIds.isEmpty()) {
                songWebService.deleteSongFromLib(listOfSelectedSongsIds, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("delete song doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        display.getSelectionSongsModel().clear();
                        songListDataProvider.getList().removeIf(song -> listOfSelectedSongsIds.contains(song.getId()));
                        GWT.log("deleted song ids " + listOfSelectedSongsIds.toString());
                    }
                });
            }
        });
    }

    private void setBookFiledHandler(){

        display.getBookTable().setNameColumnFieldUpdater((index, object, value) -> {
            bookPresenter.changeBook(object);
            if(display.getObjectContainer().getWidget()==null || !(display.getObjectContainer().getWidget() instanceof BookView))
                bookPresenter.go(display.getObjectContainer());
        });
    }
    private void setSongFieldHandler(){
        display.getSongTable().setNameColumnFieldUpdater((index, object, value) -> {
            songPresenter.changeSong(object);
            if(display.getObjectContainer().getWidget()==null || !(display.getObjectContainer().getWidget() instanceof SongView))
                songPresenter.go(display.getObjectContainer());
        });
    }
}
