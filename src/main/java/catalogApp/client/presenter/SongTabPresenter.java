package catalogApp.client.presenter;

import catalogApp.client.event.UpdateUserLibraryEvent;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.dialogs.EditDialogView;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SongTabPresenter implements Presenter {
    public interface Display {

        void setDataProviderAndInitialize(ListDataProvider<Song> dataProvider);

        MultiSelectionModel<Song> getSelectionModel();

        Widget asWidget();
    }

    private final Display display;
    private final HandlerManager eventBus;
    private final SongWebService songWebService;
    private final static ListDataProvider<Song> songListDataProvider = new ListDataProvider<>();


    public SongTabPresenter(Display display, HandlerManager eventBus, SongWebService songWebService) {
        this.display = display;
        this.eventBus = eventBus;
        this.songWebService = songWebService;
        bind();
    }

    @Override
    public void go(Panel container) {

    }

    private void bind() {
        display.setDataProviderAndInitialize(songListDataProvider);
    }

    private List<Integer> getSelectedIDs() {
        List<Integer> tmp = new ArrayList<>();
        display.getSelectionModel().getSelectedSet().forEach(e -> tmp.add(e.getId()));
        return tmp;
    }

    private Set<Song> getSelectedSet() {
        return display.getSelectionModel().getSelectedSet();
    }

    void loadData() {
        songWebService.getAllSongs(new MethodCallback<List<Song>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log("getAllSongs", throwable);
            }

            @Override
            public void onSuccess(Method method, List<Song> songs) {
                songListDataProvider.getList().addAll(songs);
            }
        });
    }

    void doAddSongsToLib() {
        List<Integer> selectedSongsIDs = getSelectedIDs();
        if (!selectedSongsIDs.isEmpty()) {
            songWebService.addSongsToUserLib(selectedSongsIDs, new MethodCallback<List<Song>>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("addSongsToLib doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, List<Song> response) {
                    GWT.log("Added to user's lib");
                    eventBus.fireEvent(new UpdateUserLibraryEvent(UpdateUserLibraryEvent.ITEM_TYPE.SONG, response));
                }
            });
        } else {
            GWT.log("nothing selected or all items already are in library");
        }
    }

    void doEditSong() {
        Set<Song> selectedSongs = getSelectedSet();
        if (selectedSongs.size() == 1) {
            new EditSongDialogPresenter(new EditDialogView(), songWebService,
                    songListDataProvider, (Song) selectedSongs.toArray()[0]).go(null);
        } else Window.alert("Select only one item!");
    }

    ListDataProvider<Song> getSongListDataProvider() {
        return songListDataProvider;
    }
}
