package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.event.AddBookEvent;
import catalogApp.client.event.AddSongEvent;
import catalogApp.client.event.ShowBooksEvent;
import catalogApp.client.event.ShowSongsEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.dialogs.EditDialogView;
import catalogApp.client.view.mainPage.tabs.BookTabView;
import catalogApp.client.view.mainPage.tabs.SongTabView;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class TabPanelPresenter implements Presenter {

    private Display display;

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
    }

    public void go(final DockPanel container) {
        container.add(display.asWidget(), DockPanel.WEST);
        container.setCellWidth(display.asWidget(), "400px");

        BookTabView bookTabView = new BookTabView();
        bookTabPresenter = new BookTabPresenter(bookTabView, eventBus, bookWebService);

        SongTabView songTabView = new SongTabView();
        songTabPresenter = new SongTabPresenter(songTabView, eventBus, songWebService);

        display.getTabPanel().addSelectionHandler(event -> {
            switch (event.getSelectedItem()) {
                case 0:
                    eventBus.fireEvent(new ShowBooksEvent());
                    break;
                case 1:
                    eventBus.fireEvent(new ShowSongsEvent());
                    break;
                default:

            }
        });

        display.getTabPanel().add(bookTabView, "Books");
        display.getTabPanel().add(songTabView, "Songs");
        display.getTabPanel().selectTab(0);

        bind();

        bookTabPresenter.go(container);
        songTabPresenter.go(container);
    }

    private void bind() {
        display.getAddButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    eventBus.fireEvent(new AddBookEvent());
                    break;
                case 1:
                    eventBus.fireEvent(new AddSongEvent());
                    break;
                default:
                    break;
            }
        });

        display.getAddToLibButton().addClickHandler(event -> {
            int x = display.getTabPanel().getTabBar().getSelectedTab();
            switch (x) {
                case 0:
                    List<Integer> selectedBooks = bookTabPresenter.getSelectedItems();
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
                    }else{
                        GWT.log("nothing selected");
                    }
                    break;
                case 1:
                    List<Integer> selectedSongs = songTabPresenter.getSelectedItems();
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
                    }else {
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
                    new EditBookDialogPresenter(new EditDialogView(), bookWebService, bookTabPresenter.getBookListDataProvider()).go(null);
                    break;
                case 1:
                    new EditSongDialogPresenter(new EditDialogView(), songWebService, songTabPresenter.getSongListDataProvider()).go(null);
                    break;
                default:
                    break;
            }
        });
    }

    public BookTabPresenter getBookPresenter(){
        return bookTabPresenter;
    }

    public SongTabPresenter getSongPresenter(){
        return songTabPresenter;
    }

    public void showBooks() {
        bookTabPresenter.loadData();
        display.getTabPanel().selectTab(0);
    }

    public void showSongs() {
        songTabPresenter.loadData();
        display.getTabPanel().selectTab(1);
    }
}
