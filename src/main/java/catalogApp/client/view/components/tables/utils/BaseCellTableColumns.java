package catalogApp.client.view.components.tables.utils;

import catalogApp.shared.model.BaseObject;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.SelectionModel;

public class BaseCellTableColumns<T extends BaseObject> {

    public Column<T, String> getNameColumn() {
        return new Column<T, String>(new ClickableTextCell()) {
            @Override
            public String getValue(BaseObject object) {
                return object.getName();
            }

            @Override
            public boolean isSortable() {
                return true;
            }

        };
    }

    public Column<T, String> getIdColumn() {
        return new TextColumn<T>() {
            @Override
            public String getValue(BaseObject object) {
                return String.valueOf(object.getId());
            }

            @Override
            public boolean isSortable() {
                return true;
            }

        };
    }


    public Column<T, Boolean> getSelectionColumn(SelectionModel<T> selectionModel) {
        return new Column<T, Boolean>(new CheckboxCell()) {
            @Override
            public Boolean getValue(T object) {
                return selectionModel.isSelected(object);
            }
        };
    }

    public Column<T, String> getArchivedColumn(){
        return new TextColumn<T>() {
            @Override
            public String getValue(T object) {
                return String.valueOf(object.isArchived());
            }
        };


    }
}
