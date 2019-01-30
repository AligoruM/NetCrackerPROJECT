package catalogApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowLibraryEvent extends GwtEvent<ShowLibraryEventHandler> {
    public static Type<ShowLibraryEventHandler> TYPE = new Type<>();

    @Override
    public Type<ShowLibraryEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowLibraryEventHandler handler) {
        handler.onShowLibraryEvent(this);
    }
}
