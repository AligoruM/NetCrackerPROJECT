package catalogApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddSongEvent extends GwtEvent<AddSongEventHandler> {
    public static Type<AddSongEventHandler> TYPE = new Type<>();

    @Override
    public Type<AddSongEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddSongEventHandler handler) {
        handler.onAddSongEvent(this);
    }
}
