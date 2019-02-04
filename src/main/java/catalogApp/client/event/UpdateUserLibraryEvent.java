package catalogApp.client.event;

import catalogApp.shared.model.BaseObject;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

public class UpdateUserLibraryEvent extends GwtEvent<UpdateUserLibraryEventHandler> {
    public static Type<UpdateUserLibraryEventHandler> TYPE = new Type<>();

    private ITEM_TYPE type;
    private List selectedItems;

    public UpdateUserLibraryEvent(UpdateUserLibraryEvent.ITEM_TYPE type, List<? extends BaseObject> selectedItems) {
        super();
        this.type = type;
        this.selectedItems=selectedItems;
    }

    @Override
    public Type<UpdateUserLibraryEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UpdateUserLibraryEventHandler handler) {
        handler.onUpdateUserLibrary(this);
    }

    public UpdateUserLibraryEvent.ITEM_TYPE getType() {
        return type;
    }

    public List getSelectedItems() {
        return selectedItems;
    }

    public enum ITEM_TYPE{
        BOOK, SONG
    }
}
