package catalogApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowUsersEvent extends GwtEvent<ShowUsersEventHandler> {
    public static final Type<ShowUsersEventHandler> TYPE = new Type<>();

    @Override
    public Type<ShowUsersEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowUsersEventHandler handler) {
        handler.onShowUsersEvent(this);
    }
}
