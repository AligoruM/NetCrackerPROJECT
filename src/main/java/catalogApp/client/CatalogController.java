package catalogApp.client;

import catalogApp.client.event.*;
import catalogApp.client.presenter.*;
import catalogApp.client.services.AuthWebService;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.mainPage.ProfileView;
import catalogApp.client.view.mainPage.MainPageView;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class CatalogController implements Presenter, ValueChangeHandler<String> {

    private static SimpleUser user;

    private final HandlerManager eventBus;
    private final BookWebService bookWebService;
    private final SongWebService songWebService;
    private final AuthWebService authWebService;

    private SimplePanel mainContainer;

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
                go(RootPanel.get());
            }
        });
    }
    @Override
    public void go(Panel container) {
        bind();

        MainPageView mainPageView = new MainPageView();

        MainPagePresenter mainPagePresenter = new MainPagePresenter(mainPageView,  eventBus);
        mainContainer = mainPagePresenter.getPanel();

        container.add(mainPageView);
    }

    private void bind() {
        History.addValueChangeHandler(this);
        eventBus.addHandler(ShowProfileEvent.TYPE, event -> doShowProfile());
        eventBus.addHandler(ShowLibraryEvent.TYPE, event -> doShowLibrary());
        eventBus.addHandler(ShowUsersEvent.TYPE, event -> doShowUsers());
    }

    private void doShowProfile() {
        History.newItem("profile");
    }


    private void doShowLibrary() {
        History.newItem("library");
    }

    private void doShowUsers() {
        History.newItem("users");
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token != null) {
            Presenter presenter = null;
            switch (token) {
                case "library":
                    presenter = new LibraryPresenter(bookWebService, songWebService, eventBus);
                    break;
                case "users":
                    if (isAdmin()) {
                        presenter = new UserPanelPresenter(new UserPanelView(), authWebService);
                    }
                    break;
                case "profile":
                    presenter = new ProfilePresenter(new ProfileView(), authWebService, eventBus);
                    break;
                default:
            }

            if (presenter != null) {
                presenter.go(mainContainer);
            }
        }

    }

    public static boolean isAdmin() {
        return "ADMIN".equals(user.getRole());
    }

    public static SimpleUser getUser() {
        return user;
    }
}
