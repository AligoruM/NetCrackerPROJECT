package catalogApp.client.presenter;

import catalogApp.shared.model.Song;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class SongPresenter implements Presenter{
    public interface Display{
        void setData(Song song);
        Widget asWidget();
    }

    Display display;

    public SongPresenter(Display display) {
        this.display = display;
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(display.asWidget());
    }

    public void changeSong(Song song){
        display.setData(song);
    }
}
