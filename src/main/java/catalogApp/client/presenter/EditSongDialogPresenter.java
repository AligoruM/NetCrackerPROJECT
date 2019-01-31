package catalogApp.client.presenter;

import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;

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
        RootPanel.get().add(display.asWidget());
        genreBox.setEnabled(false);
        display.getTable().setWidget(2, 0, new Label("Genre"));
        display.getTable().setWidget(2, 1, genreBox);
        display.getTable().setWidget(3, 0, new Label("Duration"));
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
            HashMap<String, String> newValuesMap = new HashMap<>();
            if(!(newName.equals(oldName) || newName.isEmpty())){
                newValuesMap.put("name", newName);
            }
            if(!(newDuration.equals(oldDuration)|| newDuration.isEmpty())){
                newValuesMap.put("duration", newDuration);
            }

            if(!newValuesMap.isEmpty()){
                int selected_id = song.getId();
                songWebService.updateSong(selected_id, newValuesMap, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateSong doesnt work", exception);

                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        if(newValuesMap.containsKey("name"))
                            song.setName(newValuesMap.get("name"));
                        if(newValuesMap.containsKey("duration"))
                            song.setDuration(Integer.parseInt(newValuesMap.get("duration")));
                        dataProvider.refresh();
                        display.hideDialog();
                    }
                });
            }

        });


        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
