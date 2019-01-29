package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class UserLibPanelPresenter implements Presenter {

    private BookTabPresenter.Display bookView;
    private SongTabPresenter.Display songView;

    private Button deleteBooks = new Button("Delete books from lib");
    private Button deleteSongs = new Button("Delete songs from lib");

    private SongWebService songWebService;
    private BookWebService bookWebService;

    private ListDataProvider<Book> bookListDataProvider = new ListDataProvider<>();
    private ListDataProvider<Song> songListDataProvider = new ListDataProvider<>();

    public UserLibPanelPresenter(BookTabPresenter.Display bookView, SongTabPresenter.Display songView,
                                 BookWebService bookWebService, SongWebService songWebService) {
        this.bookView = bookView;
        this.songView = songView;
        this.songWebService = songWebService;
        this.bookWebService = bookWebService;

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
        dockPanel.setSpacing(4);
        bind();
        //HorizontalPanel horizontalPanel = new HorizontalPanel();
        //horizontalPanel.add(deleteSongs);
        //horizontalPanel.add(deleteBooks);
        dockPanel.add(deleteSongs, DockPanel.WEST);
        dockPanel.add(songView.asWidget(), DockPanel.WEST);
        dockPanel.add(deleteBooks, DockPanel.EAST);
        dockPanel.add(bookView.asWidget(), DockPanel.EAST);
        container.add(dockPanel, DockPanel.CENTER);
    }

    private void bind() {
        deleteBooks.addClickHandler(event -> {
            List<Integer> tmp = new ArrayList<>();
            bookView.getSelectedItems().forEach(e -> tmp.add(e.getId()));
            if (!tmp.isEmpty()) {
                bookWebService.deleteBookFromLib(tmp, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("delete book doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        bookView.getSelectionModel().clear();
                        bookListDataProvider.getList().removeIf(book -> tmp.contains(book.getId()));
                        GWT.log("deleted book ids " + tmp.toString());
                    }
                });
            }
        });

        deleteSongs.addClickHandler(event -> {
            List<Integer> tmp = new ArrayList<>();
            songView.getSelectedItems().forEach(e -> tmp.add(e.getId()));
            if (!tmp.isEmpty()) {
                songWebService.deleteSongFromLib(tmp, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("delete song doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        songView.getSelectionModel().clear();
                        songListDataProvider.getList().removeIf(song -> tmp.contains(song.getId()));
                        GWT.log("deleted song ids " + tmp.toString());
                    }
                });
            }
        });
    }
}
