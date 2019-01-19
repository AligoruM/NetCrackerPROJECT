package catalogApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowBooksEvent extends GwtEvent<ShowBooksEventHandler> {
    public static Type<ShowBooksEventHandler> TYPE = new Type<>();
    @Override
    public Type<ShowBooksEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowBooksEventHandler handler) {
        handler.onShowBooksEvent(this);
    }
}
