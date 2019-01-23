package catalogApp.client.event;


import com.google.gwt.event.shared.EventHandler;

public interface AddBookEventHandler extends EventHandler {
    void onAddBookEvent(AddBookEvent event);
}
