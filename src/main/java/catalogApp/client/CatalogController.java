package catalogApp.client;

import catalogApp.client.event.AddBookEvent;
import catalogApp.client.event.ClosedDialogEvent;
import catalogApp.client.presenter.AddBookDialogPresenter;
import catalogApp.client.presenter.Presenter;
import catalogApp.client.presenter.TabsPresenter;
import catalogApp.client.services.TestService;
import catalogApp.client.view.TabsView;
import catalogApp.client.view.dialogs.AddBookDialog;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;

public class CatalogController implements Presenter, ValueChangeHandler<String> {

    private final HandlerManager eventBus;

    private HasWidgets container;
    private final TestService bookService;

    public CatalogController(HandlerManager eventBus, TestService rest) {
        this.bookService = rest;
        this.eventBus = eventBus;
        go(RootPanel.get());
    }

    public void go(HasWidgets container) {
        this.container = container;
        bind();
        TabsPresenter pr = new TabsPresenter(new TabsView(), eventBus, bookService);
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
        Window.alert("History change to " + token);
        if (token != null) {
            Presenter presenter = null;
            switch (token) {
                case "addBook":
                    presenter = new AddBookDialogPresenter(bookService, eventBus, new AddBookDialog());
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
