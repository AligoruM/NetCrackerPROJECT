package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.mainPage.library.TabPanelView;
import catalogApp.client.view.mainPage.library.UserLibPanelView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

public class LibraryPresenter implements Presenter {

    private HorizontalPanel libraryPanel = new HorizontalPanel();

    public LibraryPresenter(BookWebService bookWebService, SongWebService songWebService, HandlerManager eventBus) {
        TabPanelPresenter tabPanelPresenter = new TabPanelPresenter(new TabPanelView(), eventBus, bookWebService, songWebService);
        tabPanelPresenter.go(libraryPanel);
        UserLibPanelPresenter userLibPanelPresenter = new UserLibPanelPresenter(new UserLibPanelView(), bookWebService, songWebService, eventBus);
        userLibPanelPresenter.go(libraryPanel);
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(libraryPanel);
    }
}
