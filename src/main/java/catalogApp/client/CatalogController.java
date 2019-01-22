package catalogApp.client;

import catalogApp.client.event.AddBookEvent;
import catalogApp.client.event.ClosedDialogEvent;
import catalogApp.client.event.ShowBooksEvent;
import catalogApp.client.event.ShowSongsEvent;
import catalogApp.client.presenter.AddBookDialogPresenter;
import catalogApp.client.presenter.Presenter;
import catalogApp.client.presenter.ProfileBarPresenter;
import catalogApp.client.presenter.TabsPresenter;
import catalogApp.client.services.AuthWebService;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.dialogs.AddBookDialogView;
import catalogApp.client.view.mainPage.ProfileBarView;
import catalogApp.client.view.mainPage.TabPanelView;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class CatalogController implements Presenter, ValueChangeHandler<String> {

    private static SimpleUser user;

    private HasWidgets container;
    private final HandlerManager eventBus;
    private final BookWebService bookWebService;
    private final SongWebService songWebService;
    private final AuthWebService authWebService;

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
                GWT.log("simpleUser doesnt work",exception);
            }

            @Override
            public void onSuccess(Method method, SimpleUser response) {
                user = new SimpleUser(response.getId(), response.getUsername(), response.getRole());
                go(RootPanel.get());
            }
        });
    }

    public void go(HasWidgets container) {
        this.container = container;

        bind();
        ProfileBarPresenter profileBarPresenter = new ProfileBarPresenter(new ProfileBarView(), eventBus);
        tabsPresenter = new TabsPresenter(new TabPanelView(), eventBus, bookWebService, songWebService);
        profileBarPresenter.setData(user);
        profileBarPresenter.go(container);
        tabsPresenter.go(container);

    }

    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(AddBookEvent.TYPE, event -> doAddNewBook());
        eventBus.addHandler(ClosedDialogEvent.TYPE, event -> doClosedDialog());
        eventBus.addHandler(ShowBooksEvent.TYPE, event -> doShowBooks());
        eventBus.addHandler(ShowSongsEvent.TYPE, event -> doShowSongs());
    }

    private void doAddNewBook() {
        History.newItem("addBook");
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
                case "showBooks":
                    tabsPresenter.showBooks();
                    break;
                case "showSongs":
                    tabsPresenter.showSongs();
                    break;
                case ".....":
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
