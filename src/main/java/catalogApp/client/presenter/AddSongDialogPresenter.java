package catalogApp.client.presenter;

import catalogApp.client.event.ClosedDialogEvent;
import catalogApp.client.services.SongWebService;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class AddSongDialogPresenter implements Presenter{

    public interface Display {
        HasClickHandlers getSubmitButton();

        HasClickHandlers getCancelButton();

        void showDialog();

        void hideDialog();

        List<String> getAddInfo();

        Widget asWidget();

        void setSuggestions(List<String> suggestions);
    }


    private final HandlerManager eventBus;
    private final Display display;
    private final SongWebService songWebService;

    public AddSongDialogPresenter(Display display,  SongWebService songWebService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.display = display;
        this.songWebService = songWebService;
    }

    @Override
    public void go(DockPanel container) {
        bind();
        RootPanel.get().add(display.asWidget());
        display.showDialog();
    }

    private void bind(){
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
            //TODO validation
            List<String> tmp = display.getAddInfo();
            songWebService.addSong(tmp, new MethodCallback<Song>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log("Adding song doesnt work", throwable);
                }

                @Override
                public void onSuccess(Method method, Song song) {
                    SongTabPresenter.getSongListDataProvider().getList().add(song);
                    display.hideDialog();
                    eventBus.fireEvent(new ClosedDialogEvent());
                }
            });
        });

        display.getCancelButton().addClickHandler(event -> {
            eventBus.fireEvent(new ClosedDialogEvent());
            display.hideDialog();
        });
    }
}
