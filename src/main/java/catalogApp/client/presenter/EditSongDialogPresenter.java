package catalogApp.client.presenter;

import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;

public class EditSongDialogPresenter implements Presenter {

    ListDataProvider<Song> dataProvider;

    EditBookDialogPresenter.Display display;

    SongWebService songWebService;

    private TextBox genreBox = new TextBox();

    private TextBox durationBox = new TextBox();

    private String oldName;

    private String oldDuration;

    public EditSongDialogPresenter(EditBookDialogPresenter.Display display, SongWebService songWebService, ListDataProvider<Song> dataProvider) {
        this.dataProvider = dataProvider;
        this.display = display;
        this.songWebService = songWebService;
    }

    @Override
    public void go(DockPanel container) {
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
        display.updateListBox(dataProvider.getList());

        if (!dataProvider.getList().isEmpty()) {
            display.showData(dataProvider.getList().get(0));

            genreBox.setText(dataProvider.getList().get(0).getGenre().getName());
            durationBox.setText(String.valueOf(dataProvider.getList().get(0).getDuration()));

            oldName = dataProvider.getList().get(0).getName();
            oldDuration = String.valueOf(dataProvider.getList().get(0).getDuration());
        }

        display.getListBox().addChangeHandler(event -> {
            int selected_id = Integer.parseInt(display.getListBox().getSelectedValue());
            for (Song x : dataProvider.getList()) {
                if (x.getId() == selected_id) {
                    oldName = x.getName();
                    oldDuration = String.valueOf(x.getDuration());
                    display.showData(x);
                    genreBox.setText(x.getGenre().getName());
                    durationBox.setText(String.valueOf(x.getDuration()));
                    break;
                }
            }
        });

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
                int selected_id = Integer.valueOf(display.getListBox().getSelectedValue());
                songWebService.updateSong(selected_id, newValuesMap, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateSong doesnt work", exception);

                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        for (Song x : dataProvider.getList()) {
                            if (x.getId() == selected_id) {
                                if(newValuesMap.containsKey("name"))
                                    x.setName(newValuesMap.get("name"));
                                if(newValuesMap.containsKey("duration"))
                                    x.setDuration(Integer.parseInt(newValuesMap.get("duration")));
                                break;
                            }
                        }
                        display.updateListBox(dataProvider.getList());
                    }
                });
            }

        });


        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
