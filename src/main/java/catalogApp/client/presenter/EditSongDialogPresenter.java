package catalogApp.client.presenter;

import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;

import static catalogApp.client.view.constants.LibraryConstants.DURATION_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.GENRE_LABEL;

public class EditSongDialogPresenter implements Presenter {

    private ListDataProvider<Song> dataProvider;

    private Song song;

    EditBookDialogPresenter.Display display;

    private SongWebService songWebService;

    private TextBox genreBox = new TextBox();

    private TextBox durationBox = new TextBox();

    private String oldName;

    private String oldDuration;

    public EditSongDialogPresenter(EditBookDialogPresenter.Display display, SongWebService songWebService,
                                   ListDataProvider<Song> dataProvider, Song song) {
        this.song = song;
        this.dataProvider = dataProvider;
        this.display = display;
        this.songWebService = songWebService;
    }

    @Override
    public void go(Panel container) {
        genreBox.setEnabled(false);
        display.getTable().setWidget(2, 0, new Label(GENRE_LABEL));
        display.getTable().setWidget(2, 1, genreBox);
        display.getTable().setWidget(3, 0, new Label(DURATION_LABEL));
        display.getTable().setWidget(3, 1, durationBox);

        bind();

        display.showDialog();
    }

    private void bind(){

        if (!dataProvider.getList().isEmpty()) {
            display.showData(song);

            genreBox.setText(song.getGenre().getName());
            durationBox.setText(String.valueOf(song.getDuration()));

            oldName = song.getName();
            oldDuration = String.valueOf(song.getDuration());
        }

        display.getSubmitButton().addClickHandler(event -> {
            String newName = display.getNewName().trim();
            String newDuration = durationBox.getText();
            Song newSong = new Song(song.getId(), newName);

            newSong.setDuration(Integer.parseInt(newDuration));

            if(!newSong.equals(song)){
                songWebService.updateSong(newSong, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateSong doesnt work", exception);

                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        song.setName(newName);
                        song.setDuration(Integer.parseInt(newDuration));
                        dataProvider.refresh();
                        display.hideDialog();
                    }
                });
            }

        });


        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
