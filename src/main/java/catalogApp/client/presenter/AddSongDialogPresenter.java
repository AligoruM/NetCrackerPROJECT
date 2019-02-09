package catalogApp.client.presenter;

import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class AddSongDialogPresenter implements Presenter {

    public interface Display {
        HasClickHandlers getSubmitButton();

        HasClickHandlers getCancelButton();

        void showDialog();

        void hideDialog();

        List<String> getAddInfo();

        void setSuggestions(List<String> suggestions);
    }

    private final Display display;
    private final SongWebService songWebService;
    private final SongTabPresenter songTabPresenter;

    public AddSongDialogPresenter(Display display, SongWebService songWebService, SongTabPresenter songTabPresenter) {
        this.display = display;
        this.songWebService = songWebService;
        this.songTabPresenter = songTabPresenter;
        bind();
    }

    @Override
    public void go(Panel container) {
        display.showDialog();
    }

    private void bind() {
        songWebService.getGenreNames(new MethodCallback<List<String>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log("GenreNames doesnt work", throwable);
            }

            @Override
            public void onSuccess(Method method, List<String> strings) {
                display.setSuggestions(strings);
            }
        });

        display.getSubmitButton().addClickHandler(event -> {
            List<String> tmp = display.getAddInfo();
            if (tmp.size() == 3) {
                String name = tmp.get(0);
                String genre = tmp.get(1);
                String duration = tmp.get(2);
                if (name != null && !name.isEmpty() && genre != null && !genre.isEmpty() && duration!=null && !duration.isEmpty()) {
                    songWebService.addSong(tmp, new MethodCallback<Song>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {
                            GWT.log("Adding song doesnt work", throwable);
                        }

                        @Override
                        public void onSuccess(Method method, Song song) {
                            songTabPresenter.getSongListDataProvider().getList().add(song);
                            display.hideDialog();
                        }
                    });
                }else {
                    Window.alert("Fields cannot be empty!");
                }
            }
        });

        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
