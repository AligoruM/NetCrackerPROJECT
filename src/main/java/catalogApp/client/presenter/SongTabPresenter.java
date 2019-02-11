package catalogApp.client.presenter;

import catalogApp.client.event.UpdateUserLibraryEvent;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.dialogs.EditSongDialogView;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
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
        AbstractCatalogCellTable<Song> getTable();

        TextBox getSearchField();

        void setListAndInitialize(List<Song> list);

        MultiSelectionModel<Song> getSelectionModel();

        Widget asWidget();
    }

    private ArrayList<Song> list;

    private final Display display;
    private final HandlerManager eventBus;
    private final SongWebService songWebService;
    private final ListDataProvider<Song> songListDataProvider = new ListDataProvider<>();

    private boolean isGlobalPanel;

    public SongTabPresenter(Display display, HandlerManager eventBus, SongWebService songWebService) {
        this.display = display;
        this.eventBus = eventBus;
        this.songWebService = songWebService;
        display.setListAndInitialize(songListDataProvider.getList());
        songListDataProvider.addDataDisplay(display.getTable());
    }

    @Override
    public void go(Panel container) {
        container.add(display.asWidget());
    }

    private void bind() {
        if (!isGlobalPanel) {
            eventBus.addHandler(UpdateUserLibraryEvent.TYPE, event -> {
                if (event.getType() == UpdateUserLibraryEvent.ITEM_TYPE.SONG) {
                    for (Object x : event.getSelectedItems()) {
                        if (!songListDataProvider.getList().contains(x)) {
                            songListDataProvider.getList().add((Song) x);
                        }
                    }
                }
            });
        }else {
            display.getTable().enablePopup();
        }


        display.getSearchField().addKeyUpHandler(event -> {
            ArrayList<Song> foundedSongs = new ArrayList<>();
            String str = display.getSearchField().getText().toLowerCase();
            if (!str.isEmpty()) {
                list.forEach(item -> {
                    if (item.getName().toLowerCase().contains(str) || item.getGenre().getName().toLowerCase().contains(str)) {
                        foundedSongs.add(item);
                    }else{
                        if(getSelectionModel().isSelected(item)){
                           getSelectionModel().setSelected(item, false);
                        }
                    }
                });
                songListDataProvider.getList().clear();
                songListDataProvider.getList().addAll(foundedSongs);
            } else {
                songListDataProvider.getList().clear();
                songListDataProvider.getList().addAll(list);
            }
            songListDataProvider.refresh();
        });
    }

    private List<Integer> getSelectedIDs() {
        List<Integer> tmp = new ArrayList<>();
        display.getSelectionModel().getSelectedSet().forEach(e -> tmp.add(e.getId()));
        return tmp;
    }

    Set<Song> getSelectedSet() {
        return display.getSelectionModel().getSelectedSet();
    }

    void loadData(boolean isGlobal) {
        isGlobalPanel = isGlobal;
        if (isGlobal)
            songWebService.getAllSongs(new MethodCallback<List<Song>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log("getAllSongs", throwable);
                }

                @Override
                public void onSuccess(Method method, List<Song> songs) {
                    songListDataProvider.getList().addAll(songs);
                    list = new ArrayList<>(songs);
                    bind();
                }
            });
        else {
            songWebService.getUserSongs(new MethodCallback<List<Song>>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("getAllUserSongs doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, List<Song> response) {
                    songListDataProvider.getList().addAll(response);
                    list = new ArrayList<>(response);
                    bind();
                }
            });
        }
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
            new EditSongDialogPresenter(new EditSongDialogView(), songWebService,
                    songListDataProvider, (Song) selectedSongs.toArray()[0]).go(null);
        } else {
            Window.alert("Select only one item!");
        }
    }

    void doArchiveSongs() {
        List<Integer> selectedIds = getSelectedIDs();
        if (selectedIds.size() > 0) {
            songWebService.archiveSongs(selectedIds, new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("archiveSongs doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, Void response) {
                    songListDataProvider.getList().forEach(item -> {
                        if (selectedIds.contains(item.getId())) {
                            item.setArchived(true);
                        }
                        display.getSelectionModel().clear();
                    });
                }
            });
        }
    }

    void doRestoreSongs() {
        List<Integer> selectedIds = getSelectedIDs();
        if (selectedIds.size() > 0) {
            songWebService.restoreSongs(selectedIds, new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("archiveSongs doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, Void response) {
                    songListDataProvider.getList().forEach(item -> {
                        if (selectedIds.contains(item.getId())) {
                            item.setArchived(false);
                        }
                        display.getSelectionModel().clear();
                    });
                }
            });
        }
    }

    ListDataProvider<Song> getSongListDataProvider() {
        return songListDataProvider;
    }

    MultiSelectionModel<Song> getSelectionModel() {
        return display.getSelectionModel();
    }

    public Display getDisplay() {
        return display;
    }
}
