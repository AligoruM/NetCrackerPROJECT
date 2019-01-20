package catalogApp.client.presenter;

import catalogApp.client.event.ShowBooksEvent;
import catalogApp.client.event.ShowSongsEvent;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.mainPage.tabs.BookTabView;
import catalogApp.client.view.mainPage.tabs.SongTabView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabsPresenter implements Presenter {

    private Display display;

    public interface Display {
        TabPanel getTabPanel();
        Widget asWidget();
    }

    private BookTabPresenter bookTabPresenter;
    private SongTabPresenter songTabPresenter;

    private HandlerManager eventBus;
    private BookWebService bookWebService;
    private SongWebService songWebService;

    public TabsPresenter(Display view, HandlerManager eventBus, BookWebService bookWebService, SongWebService songWebService) {
        this.display = view;
        this.eventBus = eventBus;
        this.bookWebService = bookWebService;
        this.songWebService = songWebService;
    }

    public void go(final HasWidgets container) {
        container.add(display.asWidget());

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

        bookTabPresenter.go(container);
        songTabPresenter.go(container);
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
