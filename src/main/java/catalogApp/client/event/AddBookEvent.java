package catalogApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddBookEvent extends GwtEvent<AddBookEventHandler> {
    public static final Type<AddBookEventHandler> TYPE = new Type<>();

    @Override
    public Type<AddBookEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddBookEventHandler handler) {
        handler.onAddBookTask(this);
    }
}
