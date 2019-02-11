package catalogApp.client.presenter;

import catalogApp.client.event.UpdateUserLibraryEvent;
import catalogApp.client.presenter.helper.FieldUpdaters;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.mainPage.library.objectViews.BookView;
import catalogApp.client.view.mainPage.library.objectViews.SongView;
import catalogApp.client.view.mainPage.library.tabs.BookTabView;
import catalogApp.client.view.mainPage.library.tabs.SongTabView;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class UserLibPanelPresenter implements Presenter {
    public interface Display{
        SimplePanel getBookContainer();
        SimplePanel getSongContainer();

        Button getDeleteBooksButton();
        Button getDeleteSongsButton();

        SimplePanel getObjectContainer();

        Widget asWidget();
    }

    private Display display;
    private SongWebService songWebService;
    private BookWebService bookWebService;
    private HandlerManager eventBus;

    private BookTabPresenter bookTabPresenter;
    private SongTabPresenter songTabPresenter;

    private BookPresenter bookPresenter;
    private SongPresenter songPresenter;

    public UserLibPanelPresenter(Display display, BookWebService bookWebService, SongWebService songWebService, HandlerManager eventBus) {
        this.display = display;
        this.songWebService = songWebService;
        this.bookWebService = bookWebService;
        this.eventBus = eventBus;



        bookTabPresenter = new BookTabPresenter(new BookTabView(), eventBus, bookWebService);
        songTabPresenter = new SongTabPresenter(new SongTabView(), eventBus, songWebService);

        bookPresenter = new BookPresenter(new BookView(), bookWebService, bookTabPresenter.getBookListDataProvider());
        songPresenter = new SongPresenter(new SongView(), songWebService, songTabPresenter.getSongListDataProvider());

        bookTabPresenter.loadData(false);
        bookTabPresenter.go(display.getBookContainer());

        songTabPresenter.loadData(false);
        songTabPresenter.go(display.getSongContainer());
        bind();
    }

    @Override
    public void go(Panel container) {
        container.add(display.asWidget());
    }

    private void bind() {
        songTabPresenter.getDisplay().getTable().setNameColumnFieldUpdater(FieldUpdaters.songFieldUpdater(songPresenter, display));
        bookTabPresenter.getDisplay().getTable().setNameColumnFieldUpdater(FieldUpdaters.bookFieldUpdater(bookPresenter, display));

        display.getDeleteBooksButton().addClickHandler(event -> {
            List<Integer> listOfSelectedBooksIds = new ArrayList<>();
            bookTabPresenter.getSelectedSet().forEach(e -> listOfSelectedBooksIds.add(e.getId()));
            if (!listOfSelectedBooksIds.isEmpty()) {
                bookWebService.deleteBookFromLib("books", listOfSelectedBooksIds, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("delete book doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        bookTabPresenter.getSelectionModel().clear();
                        bookTabPresenter.getBookListDataProvider().getList().removeIf(book -> listOfSelectedBooksIds.contains(book.getId()));
                        GWT.log("deleted book ids " + listOfSelectedBooksIds.toString());
                    }
                });
            }
        });

        display.getDeleteSongsButton().addClickHandler(event -> {
            List<Integer> listOfSelectedSongsIds = new ArrayList<>();
            songTabPresenter.getSelectedSet().forEach(e -> listOfSelectedSongsIds.add(e.getId()));
            if (!listOfSelectedSongsIds.isEmpty()) {
                songWebService.deleteSongFromLib("songs", listOfSelectedSongsIds, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("delete song doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        songTabPresenter.getSelectionModel().clear();
                        songTabPresenter.getSongListDataProvider().getList().removeIf(song -> listOfSelectedSongsIds.contains(song.getId()));
                        GWT.log("deleted song ids " + listOfSelectedSongsIds.toString());
                    }
                });
            }
        });
    }
}
