package catalogApp.client.presenter;

import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.DockPanel;
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

    }

    public List<Integer> getSelectedIDs(){
        List<Integer> tmp = new ArrayList<>();
        display.getSelectedItems().forEach(e -> tmp.add(e.getId()));
        return tmp;
    }

    public Set<Song> getSelectedItems(){
        return display.getSelectedItems();
    }

    public void loadData() {
        if (!loaded) {
            songWebService.getAllSongs(new MethodCallback<List<Song>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log("getAllSongs", throwable);
                }

                @Override
                public void onSuccess(Method method, List<Song> songs) {
                    songListDataProvider.getList().addAll(songs);
                    loaded = true;
                }
            });
        }
    }

    public ListDataProvider<Song> getSongListDataProvider() {
        return songListDataProvider;
    }
}
