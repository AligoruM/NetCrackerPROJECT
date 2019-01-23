package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class UserLibPanelPresenter implements Presenter {

    private HandlerManager eventBus;
    private BookTabPresenter.Display bookView;
    private SongTabPresenter.Display songView;
    private SongWebService songWebService;
    private BookWebService bookWebService;

    private ListDataProvider<Book> bookListDataProvider = new ListDataProvider<>();
    private ListDataProvider<Song> songListDataProvider = new ListDataProvider<>();

    public UserLibPanelPresenter(BookTabPresenter.Display bookView, SongTabPresenter.Display songView,
                                 BookWebService bookWebService, SongWebService songWebService, HandlerManager eventBus) {
        this.bookView = bookView;
        this.songView = songView;
        this.songWebService = songWebService;
        this.bookWebService = bookWebService;
        this.eventBus = eventBus;

        this.bookWebService.getUserBooks(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getUserBooks doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, List<Book> response) {
                bookListDataProvider.getList().addAll(response);
                bookView.setDataProviderAndInitialize(bookListDataProvider);
            }
        });

        this.songWebService.getUserSongs(new MethodCallback<List<Song>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getUserSongs doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, List<Song> response) {
                songListDataProvider.getList().addAll(response);
                songView.setDataProviderAndInitialize(songListDataProvider);
            }
        });
    }

    @Override
    public void go(DockPanel container) {
        DockPanel dockPanel = new DockPanel();

        dockPanel.setBorderWidth(3);
        //dockPanel.getElement().getStyle().setPadding(4, Style.Unit.PX);

        dockPanel.setSpacing(4);

        dockPanel.add(bookView.asWidget(), DockPanel.EAST);
        dockPanel.add(songView.asWidget(), DockPanel.WEST);

        container.add(dockPanel, DockPanel.CENTER);
    }
}
