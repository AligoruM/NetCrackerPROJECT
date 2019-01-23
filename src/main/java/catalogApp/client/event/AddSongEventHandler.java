package catalogApp.client.event;


import com.google.gwt.event.shared.EventHandler;

public interface AddSongEventHandler extends EventHandler {
    void onAddSongEvent(AddSongEvent event);
}
