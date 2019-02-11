package catalogApp.client.presenter;

import catalogApp.client.services.SongWebService;
import catalogApp.client.view.components.RatingPanel;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
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

    private ListDataProvider<Song> dataProvider;

    public SongPresenter(Display display, SongWebService songWebService, ListDataProvider<Song> dataProvider) {
        this.display = display;
        this.dataProvider = dataProvider;
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

    private MethodCallback<Float> getMethodSongCallback() {
        return new MethodCallback<Float>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("something in song marking went wrong...");
            }

            @Override
            public void onSuccess(Method method, Float response) {
                song.setRating(response);
                display.setData(song);
                if (dataProvider != null)
                    dataProvider.refresh();
            }
        };
    }

}
