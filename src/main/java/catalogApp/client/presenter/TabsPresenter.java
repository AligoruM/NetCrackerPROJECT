package catalogApp.client.presenter;

import catalogApp.client.services.TestService;
import catalogApp.client.view.tabs.BookTabView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabsPresenter implements Presenter{

    private Display display;

    public interface Display {
        TabPanel getTabPanel();
        Widget asWidget();
    }

    HandlerManager eventBus;
    TestService bookService;
    public TabsPresenter(HandlerManager eventBus, TestService bookService, Display view) {
        this.display = view;
        this.eventBus = eventBus;
        this.bookService = bookService;
    }

    public void go(final HasWidgets container) {
        container.add(display.asWidget());
        BookTabView bookTabView = new BookTabView();
        BookTabPresenter bookTabPresenter= new BookTabPresenter(bookTabView, eventBus, bookService);
        display.getTabPanel().add(bookTabView, "Books");
        display.getTabPanel().selectTab(0);
        bookTabPresenter.go(container);
        //bookTabPresenter.go();
    }
}
