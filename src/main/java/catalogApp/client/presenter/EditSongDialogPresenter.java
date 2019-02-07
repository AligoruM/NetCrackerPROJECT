package catalogApp.client.presenter;

import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class EditSongDialogPresenter implements Presenter {

    public interface Display {
        Button getCancelButton();

        Button getSubmitButton();

        void showDialog();

        void hideDialog();

        Widget asWidget();

        void showData(Song object);

        String getNewName();

        int getNewDuration();
    }


    private ListDataProvider<Song> dataProvider;

    private Song song;

    private Display display;

    private SongWebService songWebService;


    public EditSongDialogPresenter(Display display, SongWebService songWebService,
                                   ListDataProvider<Song> dataProvider, Song song) {
        this.song = song;
        this.dataProvider = dataProvider;
        this.display = display;
        this.songWebService = songWebService;
        bind();
    }

    @Override
    public void go(Panel container) {
        display.showDialog();
    }

    private void bind() {
        display.showData(song);

        display.getSubmitButton().addClickHandler(event -> {
            boolean isChanged = false;
            String newName = display.getNewName().trim();
            int newDuration = display.getNewDuration();
            Song newSong = new Song();

            newSong.setId(song.getId());
            if (song.getDuration() != newDuration) {
                newSong.setDuration(newDuration);
                isChanged = true;
            }
            if (!song.getName().equals(newName)) {
                newSong.setName(newName);
                isChanged = true;
            }

            if (isChanged) {
                songWebService.updateSong(newSong, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateSong doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        song.setName(newName);
                        song.setDuration(newDuration);
                        dataProvider.refresh();
                        display.hideDialog();
                    }
                });
            }

        });


        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
