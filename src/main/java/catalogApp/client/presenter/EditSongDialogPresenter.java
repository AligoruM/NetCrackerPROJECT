package catalogApp.client.presenter;

import catalogApp.client.presenter.helper.EditorInitializeHelper;
import catalogApp.client.services.SongWebService;
import catalogApp.client.view.components.AdditionalInfo;
import catalogApp.client.view.components.FileUploader;
import catalogApp.shared.model.Song;
import catalogApp.shared.model.SongGenre;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class EditSongDialogPresenter implements Presenter {

    public interface Display {
        AdditionalInfo getAdditionalInfo();

        Button getCancelButton();

        Button getSubmitButton();

        void showDialog();

        void hideDialog();

        Widget asWidget();

        void showData(Song object, List<String> genres);

        String getNewName();

        String getNewGenre();

        int getNewDuration();
    }

    private boolean isLoaded;

    private String oldName;

    private String oldComment;

    private String oldGenreName;

    private ListDataProvider<Song> dataProvider;

    private Song song;

    private Display display;

    private SongWebService songWebService;

    private List<String> genres = new ArrayList<>();


    public EditSongDialogPresenter(Display display, SongWebService songWebService,
                                   ListDataProvider<Song> dataProvider, Song song) {
        this.song = song;
        this.dataProvider = dataProvider;
        this.display = display;
        this.songWebService = songWebService;
        oldName = song.getName();
        oldComment = song.getComment()!=null ? song.getComment() : "";
        oldGenreName = song.getGenre().getName();
        songWebService.getGenreNames(new MethodCallback<List<String>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getGenres doesnt work");
            }

            @Override
            public void onSuccess(Method method, List<String> response) {
                genres.addAll(response);
                display.showData(song, genres);

            }
        });
        bind();
    }

    @Override
    public void go(Panel container) {
        display.showDialog();
    }

    private void bind() {

        FileUploader fileUploader = display.getAdditionalInfo().getFileUploader();

        EditorInitializeHelper.initFileUploader(display.getAdditionalInfo().getUploadButton(), fileUploader);

        fileUploader.addSubmitCompleteHandler(event -> {
            isLoaded = true;
            Window.alert("loaded");
        });

        display.getSubmitButton().addClickHandler(event -> {
            boolean isChanged = false;
            String newComment = display.getAdditionalInfo().getComment();
            String newName = display.getNewName();
            String newGenreName = display.getNewGenre();
            String imagePath = display.getAdditionalInfo().getFileUploader().getFileUpload().getFilename();
            imagePath = imagePath.substring(imagePath.lastIndexOf('\\') + 1);
            int newDuration = display.getNewDuration();

            Song newSong = new Song();

            newSong.setId(song.getId());
            if(!oldComment.equals(newComment)){
                newSong.setComment(newComment);
                isChanged = true;
            }
            if (song.getDuration() != newDuration) {
                newSong.setDuration(newDuration);
                isChanged = true;
            }
            if(!oldGenreName.equalsIgnoreCase(newGenreName) && !newGenreName.isEmpty()) {
                SongGenre newGenre = new SongGenre();
                newGenre.setName(newGenreName);
                newSong.setGenre(newGenre);
                isChanged=true;
            }
            if (!oldName.equals(newName)) {
                newSong.setName(newName);
                isChanged = true;
            }
            if(!imagePath.isEmpty() && isLoaded){
                newSong.setImagePath(imagePath);
                isChanged=true;
            }

            if (isChanged) {
                songWebService.updateSong(newSong, new MethodCallback<Song>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateSong doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Song response) {
                        if(response!=null) {
                            song.setName(response.getName());
                            song.setComment(response.getComment());
                            if(response.getImagePath()!=null){
                                song.setImagePath(response.getImagePath());
                            }
                            if(response.getGenre()!=null){
                                song.setGenre(response.getGenre());
                            }

                            song.setDuration(response.getDuration());

                        }
                        dataProvider.refresh();
                        display.hideDialog();
                    }
                });
            }

        });

        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
