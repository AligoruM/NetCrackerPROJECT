package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.event.AddSongEvent;
import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SongTabPresenter implements Presenter {
    public interface Display {
        HasClickHandlers getAddButton();

        void setDataProviderAndInitialize(ListDataProvider<Song> dataProvider);

        Set<Song> getSelectedItems();

        Widget asWidget();
    }

    private boolean loaded = false;
    private final Display display;
    private final HandlerManager eventBus;
    private final SongWebService songWebService;
    private final static ListDataProvider<Song> songListDataProvider = new ListDataProvider<>();


    public SongTabPresenter(Display display, HandlerManager eventBus, SongWebService songWebService) {
        this.display = display;
        this.eventBus = eventBus;
        this.songWebService = songWebService;
    }

    @Override
    public void go(DockPanel container) {
        bind();
    }

    private void bind() {
        display.setDataProviderAndInitialize(songListDataProvider);
        if(CatalogController.isAdmin())
            display.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddSongEvent()));
        else{
            display.getAddButton().addClickHandler(event -> {
                List<Integer> tmp = new ArrayList<>();
                display.getSelectedItems().forEach(e -> tmp.add(e.getId()));
                if(!tmp.isEmpty()) {
                    songWebService.addSongsToUserLib(tmp, new MethodCallback<Void>() {
                        @Override
                        public void onFailure(Method method, Throwable exception) {
                            GWT.log("addSongsToLib doesnt work", exception);
                        }

                        @Override
                        public void onSuccess(Method method, Void response) {
                            Window.alert("Added to user's lib");
                        }
                    });
                }
            });
        }
    }

    public void loadData() {
        if (!loaded) {
            songWebService.getAllSongs(new MethodCallback<List<Song>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log("getAllSongs", throwable);
                    Window.alert("getAllSongs doesnt work\n" + throwable.getMessage());
                }

                @Override
                public void onSuccess(Method method, List<Song> songs) {
                    GWT.log(songs.get(0).toString());
                    songListDataProvider.getList().addAll(songs);
                    loaded = true;
                }
            });
        }
    }

    public static ListDataProvider<Song> getSongListDataProvider() {
        return songListDataProvider;
    }
}
