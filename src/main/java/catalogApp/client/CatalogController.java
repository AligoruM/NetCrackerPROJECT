package catalogApp.client;

import catalogApp.client.event.*;
import catalogApp.client.presenter.*;
import catalogApp.client.services.AuthWebService;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.dialogs.AddBookDialogView;
import catalogApp.client.view.dialogs.AddSongDialogView;
import catalogApp.client.view.mainPage.ProfileBarView;
import catalogApp.client.view.mainPage.TabPanelView;
import catalogApp.client.view.mainPage.UserPanelView;
import catalogApp.client.view.mainPage.tabs.BookTabView;
import catalogApp.client.view.mainPage.tabs.SongTabView;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class CatalogController implements Presenter, ValueChangeHandler<String> {

    private static SimpleUser user;

    private DockPanel container;
    private final HandlerManager eventBus;
    private final BookWebService bookWebService;
    private final SongWebService songWebService;
    private final AuthWebService authWebService;
    private DockPanel dock = new DockPanel();

    private TabsPresenter tabsPresenter;

    public CatalogController(HandlerManager eventBus, BookWebService bookService,
                             SongWebService songWebService, AuthWebService authWebService) {
        this.authWebService = authWebService;
        this.songWebService = songWebService;
        this.bookWebService = bookService;
        this.eventBus = eventBus;

        authWebService.getSimpleUser(new MethodCallback<SimpleUser>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("simpleUser doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, SimpleUser response) {
                user = new SimpleUser(response.getId(), response.getUsername(), response.getRole());
                go(dock);
            }
        });
    }

    public void go(DockPanel container) {
        this.container = container;
        bind();


        RootPanel.get().add(dock);

        dock.setSpacing(4);
        dock.setWidth("100%");
        dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
        dock.setBorderWidth(3);

        ProfileBarPresenter profileBarPresenter = new ProfileBarPresenter(new ProfileBarView());
        tabsPresenter = new TabsPresenter(new TabPanelView(), eventBus, bookWebService, songWebService);
        profileBarPresenter.setData(user);
        profileBarPresenter.go(container);

        tabsPresenter.go(dock);

        UserLibPanelPresenter userLibPanelPresenter = new UserLibPanelPresenter(new BookTabView(), new SongTabView(), bookWebService, songWebService);
        userLibPanelPresenter.go(dock);

        if (isAdmin()) {
            UserPanelPresenter userPanelPresenter = new UserPanelPresenter(new UserPanelView(), authWebService);
            userPanelPresenter.go(dock);
        }
    }

    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(AddBookEvent.TYPE, event -> doAddNewBook());
        eventBus.addHandler(AddSongEvent.TYPE, event -> doAddNewSong());
        eventBus.addHandler(ClosedDialogEvent.TYPE, event -> doClosedDialog());
        eventBus.addHandler(ShowBooksEvent.TYPE, event -> doShowBooks());
        eventBus.addHandler(ShowSongsEvent.TYPE, event -> doShowSongs());
    }

    private void doAddNewBook() {
        History.newItem("addBook");
    }

    private void doAddNewSong() {
        History.newItem("addSong");
    }

    private void doClosedDialog() {
        History.newItem("");
    }

    private void doShowBooks() {
        History.newItem("showBooks");
    }

    private void doShowSongs() {
        History.newItem("showSongs");
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token != null) {
            Presenter presenter = null;
            switch (token) {
                case "addBook":
                    presenter = new AddBookDialogPresenter(new AddBookDialogView(), bookWebService, eventBus);
                    break;
                case "addSong":
                    presenter = new AddSongDialogPresenter(new AddSongDialogView(), songWebService, eventBus);
                    break;
                case "showBooks":
                    tabsPresenter.showBooks();
                    break;
                case "showSongs":
                    tabsPresenter.showSongs();
                    break;
                default:
            }

            if (presenter != null) {
                presenter.go(container);
            }
        }

    }

    public static boolean isAdmin() {
        return "ADMIN".equals(user.getRole());
    }
}
