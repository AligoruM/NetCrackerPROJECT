package catalogApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ClosedDialogEvent extends GwtEvent<ClosedDialogEventHandler> {
    public static Type<ClosedDialogEventHandler> TYPE = new Type<>();

    @Override
    public Type<ClosedDialogEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ClosedDialogEventHandler handler) {
        handler.onClosedDialog(this);
    }
}
