package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.dialogs.AddBookDialogView;
import catalogApp.client.view.dialogs.AddSongDialogView;
import catalogApp.client.view.dialogs.EditDialogView;
import catalogApp.client.view.mainPage.tabs.BookTabView;
import catalogApp.client.view.mainPage.tabs.SongTabView;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;
import java.util.Set;

public class TabPanelPresenter implements Presenter {

    private Display display;

    private boolean booksIsLoaded = false;
    private boolean songsIsLoaded = false;

    public interface Display {
        Button getAddButton();

        Button getAddToLibButton();

        Button getEditButton();

        TabPanel getTabPanel();

        Widget asWidget();
    }

    private BookTabPresenter bookTabPresenter;
    private SongTabPresenter songTabPresenter;

    private HandlerManager eventBus;
    private BookWebService bookWebService;
    private SongWebService songWebService;

    public TabPanelPresenter(Display view, HandlerManager eventBus, BookWebService bookWebService, SongWebService songWebService) {
        this.display = view;
        this.eventBus = eventBus;
        this.bookWebService = bookWebService;
        this.songWebService = songWebService;

        BookTabView bookTabView = new BookTabView();
        bookTabPresenter = new BookTabPresenter(bookTabView, eventBus, bookWebService);

        SongTabView songTabView = new SongTabView();
        songTabPresenter = new SongTabPresenter(songTabView, eventBus, songWebService);
        display.getTabPanel().addSelectionHandler(event -> {
            switch (event.getSelectedItem()) {
                case 0:
                    if (!booksIsLoaded) {
                        bookTabPresenter.loadData();
                        booksIsLoaded = true;
                    }
                    break;
                case 1:
                    if (!songsIsLoaded) {
                        songTabPresenter.loadData();
                        songsIsLoaded = true;
                    }
                    break;
                default:

            }
        });

        display.getTabPanel().add(bookTabView, "Books");
        display.getTabPanel().add(songTabView, "Songs");
        display.getTabPanel().selectTab(0);

        bind();
    }

    @Override
    public void go(Panel container) {
        container.add(display.asWidget());
    }

    private void bind() {
        display.getAddButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    new AddBookDialogPresenter(new AddBookDialogView(), bookWebService, bookTabPresenter, eventBus).go(RootPanel.get());
                    break;
                case 1:
                    new AddSongDialogPresenter(new AddSongDialogView(), songWebService, songTabPresenter, eventBus).go(RootPanel.get());
                    break;
                default:
                    break;
            }
        });

        display.getAddToLibButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    List<Integer> selectedBooks = bookTabPresenter.getSelectedIDs();
                    if (!selectedBooks.isEmpty()) {
                        bookWebService.addBooksToUserLib(selectedBooks, new MethodCallback<Void>() {
                            @Override
                            public void onFailure(Method method, Throwable exception) {
                                GWT.log("addBookToLib doesnt work", exception);
                            }

                            @Override
                            public void onSuccess(Method method, Void response) {
                                Window.alert("Added to user's lib");
                            }
                        });
                    } else {
                        GWT.log("nothing selected");
                    }
                    break;
                case 1:
                    List<Integer> selectedSongs = songTabPresenter.getSelectedIDs();
                    if (!selectedSongs.isEmpty()) {
                        songWebService.addSongsToUserLib(selectedSongs, new MethodCallback<Void>() {
                            @Override
                            public void onFailure(Method method, Throwable exception) {
                                GWT.log("addSongsToLib doesnt work", exception);
                            }

                            @Override
                            public void onSuccess(Method method, Void response) {
                                Window.alert("Added to user's lib");
                            }
                        });
                    } else {
                        GWT.log("nothing selected");
                    }
                    break;
                default:
                    break;
            }
        });

        display.getEditButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    Set<Book> selectedBooks = bookTabPresenter.getSelectedSet();
                    if (selectedBooks.size() == 1) {
                        new EditBookDialogPresenter(new EditDialogView(), bookWebService,
                                bookTabPresenter.getBookListDataProvider(), (Book) selectedBooks.toArray()[0]).go(null);
                    } else Window.alert("Select only one item!");
                    break;
                case 1:
                    Set<Song> selectedSongs = songTabPresenter.getSelectedItems();
                    if (selectedSongs.size() == 1) {
                        new EditSongDialogPresenter(new EditDialogView(), songWebService,
                                songTabPresenter.getSongListDataProvider(), (Song) selectedSongs.toArray()[0]).go(null);
                    } else Window.alert("Select only one item!");
                    break;

                default:
                    break;
            }
        });
    }

}
