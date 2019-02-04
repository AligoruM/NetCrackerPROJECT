package catalogApp.client.event;


import com.google.gwt.event.shared.EventHandler;

public interface UpdateUserLibraryEventHandler extends EventHandler {
    void onUpdateUserLibrary(UpdateUserLibraryEvent event);
}
