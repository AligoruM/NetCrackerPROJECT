package catalogApp.client;

import catalogApp.client.event.AddBookEvent;
import catalogApp.client.event.ClosedDialogEvent;
import catalogApp.client.presenter.AddBookDialogPresenter;
import catalogApp.client.presenter.Presenter;
import catalogApp.client.presenter.TabsPresenter;
import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.mainPage.TabPanelView;
import catalogApp.client.view.dialogs.AddBookDialogView;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class CatalogController implements Presenter, ValueChangeHandler<String> {

    private final HandlerManager eventBus;

    private HasWidgets container;
    private final BookWebService bookWebService;
    private final SongWebService songWebService;

    public CatalogController(HandlerManager eventBus, BookWebService bookService, SongWebService songWebService) {
        this.songWebService = songWebService;
        this.bookWebService = bookService;
        this.eventBus = eventBus;
    }

    public void go(HasWidgets container) {
        this.container = container;
        bind();
        TabsPresenter pr = new TabsPresenter(new TabPanelView(), eventBus, bookWebService, songWebService);
        pr.go(container);
    }

    private void bind(){
        History.addValueChangeHandler(this);

        eventBus.addHandler(AddBookEvent.TYPE, event -> doAddNewBook());
        eventBus.addHandler(ClosedDialogEvent.TYPE, event -> doClosedDialog());
    }

    private void doAddNewBook() {
        History.newItem("addBook");
    }

    private void doClosedDialog(){
        History.newItem("");
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
                    break;
                case "....":
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
}
