package catalogApp.client.presenter;

import catalogApp.client.event.AddBookEvent;
import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class SongTabPresenter implements Presenter {
    public interface Display{
        HasClickHandlers getAddButton();
        void setDataProviderAndInitialize(ListDataProvider<Song> dataProvider);
        Widget asWidget();
    }

    private final SongTabPresenter.Display display;
    private final HandlerManager eventBus;
    private final SongWebService songWebService;

    private final ListDataProvider<Song> songListDataProvider = new ListDataProvider<>();

    public SongTabPresenter(Display display, HandlerManager eventBus, SongWebService songWebService) {
        this.display = display;
        this.eventBus = eventBus;
        this.songWebService = songWebService;
    }

    @Override
    public void go(HasWidgets container) {
        bind();
    }

    private void bind(){
        display.setDataProviderAndInitialize(songListDataProvider);
        songWebService.getAllSongs(new MethodCallback<List<Song>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("getAllSongs doesnt work");
            }

            @Override
            public void onSuccess(Method method, List<Song> books) {
                songListDataProvider.getList().addAll(books);
            }
        });

        display.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddBookEvent()));
    }
}
