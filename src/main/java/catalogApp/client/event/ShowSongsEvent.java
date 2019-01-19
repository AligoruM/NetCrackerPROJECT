package catalogApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowSongsEvent extends GwtEvent<ShowSongsEventHandler> {
    public static Type<ShowSongsEventHandler> TYPE = new Type<>();
    @Override
    public Type<ShowSongsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowSongsEventHandler handler) {
        handler.onShowSongsEvent(this);
    }
}
