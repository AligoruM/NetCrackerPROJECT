package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.dialogs.AddBookDialogView;
import catalogApp.client.view.dialogs.AddSongDialogView;
import catalogApp.client.view.mainPage.library.tabs.BookTabView;
import catalogApp.client.view.mainPage.library.tabs.SongTabView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

import static catalogApp.client.view.constants.LibraryConstants.BOOKS_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.SONGS_LABEL;


public class TabPanelPresenter implements Presenter {


    public interface Display {
        Button getAddButton();

        Button getAddToLibButton();

        Button getEditButton();

        Button getArchiveButton();

        Button getRestoreButton();

        TabPanel getTabPanel();

        Widget asWidget();
    }

    private Display display;

    private boolean booksIsLoaded;
    private boolean songsIsLoaded;

    private BookTabPresenter bookTabPresenter;
    private SongTabPresenter songTabPresenter;

    private BookWebService bookWebService;
    private SongWebService songWebService;

    public TabPanelPresenter(Display view, HandlerManager eventBus, BookWebService bookWebService, SongWebService songWebService) {
        this.display = view;
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
                        bookTabPresenter.loadData(true);
                        booksIsLoaded = true;
                    }
                    break;
                case 1:
                    if (!songsIsLoaded) {
                        songTabPresenter.loadData(true);
                        songsIsLoaded = true;
                    }
                    break;
                default:

            }
        });

        display.getTabPanel().add(bookTabView, BOOKS_LABEL);
        display.getTabPanel().add(songTabView, SONGS_LABEL);
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
                    new AddBookDialogPresenter(new AddBookDialogView(), bookWebService, bookTabPresenter).go(null);
                    break;
                case 1:
                    new AddSongDialogPresenter(new AddSongDialogView(), songWebService, songTabPresenter).go(null);
                    break;
                default:
                    break;
            }
        });

        display.getAddToLibButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    bookTabPresenter.doAddBooksToLib();
                    break;
                case 1:
                    songTabPresenter.doAddSongsToLib();
                    break;
                default:
                    break;
            }
        });

        display.getEditButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    bookTabPresenter.doEditBook();
                    break;
                case 1:
                    songTabPresenter.doEditSong();
                    break;
                default:
                    break;
            }
        });

        display.getArchiveButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    bookTabPresenter.doArchiveBooks();
                    break;
                case 1:
                    songTabPresenter.doArchiveSongs();
                    break;
                default:
                    break;
            }
        });

        display.getRestoreButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    bookTabPresenter.doRestoreBooks();
                    break;
                case 1:
                    songTabPresenter.doRestoreSongs();
                    break;
                default:
                    break;
            }
        });
    }

}
