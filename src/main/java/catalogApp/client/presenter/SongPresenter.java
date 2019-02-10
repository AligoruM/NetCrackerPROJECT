package catalogApp.client.presenter;

import catalogApp.client.services.SongWebService;
import catalogApp.client.view.components.RatingPanel;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class SongPresenter implements Presenter {
    public interface Display {
        RatingPanel getRatingPanel();

        void setData(Song song);

        Widget asWidget();
    }

    Display display;

    private Song song;

    public SongPresenter(Display display, SongWebService songWebService) {
        this.display = display;
        display.getRatingPanel().getOneButton().addClickHandler(event -> songWebService.markSong(song.getId(), 1, getMethodSongCallback()));
        display.getRatingPanel().getTwoButton().addClickHandler(event -> songWebService.markSong(song.getId(), 2, getMethodSongCallback()));
        display.getRatingPanel().getThreeButton().addClickHandler(event -> songWebService.markSong(song.getId(), 3, getMethodSongCallback()));
        display.getRatingPanel().getFourButton().addClickHandler(event -> songWebService.markSong(song.getId(), 4, getMethodSongCallback()));
        display.getRatingPanel().getFiveButton().addClickHandler(event -> songWebService.markSong(song.getId(), 5, getMethodSongCallback()));
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(display.asWidget());
    }

    public void changeSong(Song song) {
        this.song = song;
        display.setData(song);
    }

    private MethodCallback<Double> getMethodSongCallback() {
        return new MethodCallback<Double>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("something in marking went wrong...");
            }

            @Override
            public void onSuccess(Method method, Double response) {
                song.setMarked(true);
                song.setRating(response);
                display.setData(song);
            }
        };
    }

}
