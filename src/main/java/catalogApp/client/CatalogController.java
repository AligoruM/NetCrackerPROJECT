package catalogApp.client;

import catalogApp.client.event.ShowLibraryEvent;
import catalogApp.client.event.ShowProfileEvent;
import catalogApp.client.event.ShowUsersEvent;
import catalogApp.client.presenter.*;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.services.UserWebService;
import catalogApp.client.view.mainPage.MainPageView;
import catalogApp.client.view.mainPage.profile.ProfileView;
import catalogApp.client.view.mainPage.users.UserPanelView;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class CatalogController implements Presenter, ValueChangeHandler<String> {

    private static SimpleUser user;

    private final HandlerManager eventBus;
    private final BookWebService bookWebService;
    private final SongWebService songWebService;
    private final UserWebService userWebService;

    private SimplePanel mainContainer;

    private LibraryPresenter libraryPresenter;
    private ProfilePresenter profilePresenter;
    private UserPanelPresenter userPanelPresenter;

    public CatalogController(HandlerManager eventBus, BookWebService bookService,
                             SongWebService songWebService, UserWebService userWebService) {
        this.userWebService = userWebService;
        this.songWebService = songWebService;
        this.bookWebService = bookService;
        this.eventBus = eventBus;


        userWebService.getSimpleUser(new MethodCallback<SimpleUser>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("simpleUser doesnt work", exception);
                Cookies.removeCookie("JSESSIONID");
                Window.Location.replace(GWT.getHostPageBaseURL() + "login");
            }

            @Override
            public void onSuccess(Method method, SimpleUser response) {
                user = response;
                go(RootPanel.get());
            }
        });
    }

    @Override
    public void go(Panel container) {
        bind();

        MainPageView mainPageView = new MainPageView();

        MainPagePresenter mainPagePresenter = new MainPagePresenter(mainPageView, eventBus);
        mainContainer = mainPagePresenter.getPanel();

        container.add(mainPageView);
        History.fireCurrentHistoryState();
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
                    if (libraryPresenter == null) {
                        libraryPresenter = new LibraryPresenter(bookWebService, songWebService, eventBus);
                    }
                    presenter = libraryPresenter;
                    break;
                case "users":
                    if (isAdmin()) {
                        if (userPanelPresenter == null) {
                            userPanelPresenter = new UserPanelPresenter(new UserPanelView(), userWebService);
                        }
                        presenter = userPanelPresenter;
                    }
                    break;
                case "profile":
                    if (profilePresenter == null) {
                        profilePresenter = new ProfilePresenter(new ProfileView(), userWebService);
                    }
                    presenter = profilePresenter;
                    break;
                default:
            }

            if (presenter != null) {
                presenter.go(mainContainer);
            }
        }

    }

    public static boolean isAdmin() {
        if (user != null) {
            return user.getRoles().contains("ADMIN");
        }
        return false;
    }

    public static SimpleUser getUser() {
        return user;
    }
}
