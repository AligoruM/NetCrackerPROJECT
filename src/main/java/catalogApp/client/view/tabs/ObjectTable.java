package catalogApp.client.view.tabs;


import catalogApp.shared.model.IBaseInterface;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

public class ObjectTable extends CellTable {

    public static interface GetValue<C> {
        C getValue(IBaseInterface object);
    }

    private ListDataProvider<IBaseInterface> dataProvider = new ListDataProvider<>();

    public ObjectTable(List data) {
        dataProvider.getList().addAll(data);
        //addCustomColumn("Name", IBaseInterface::getName, new TextCell());
        //addCustomColumn("Author", IBaseInterface::getAuthorName, new TextCell());
    }

    public <T extends IBaseInterface, C> Column addCustomColumn(String name, final GetValue<C> getter,
                                                                Cell<C> cell, boolean sortable){
        Column<T, C> column = new Column<T, C>(cell) {
            @Override
            public C getValue(T object) {
                return getter.getValue(object);
            }
        };

        column.setSortable(sortable);

        //ColumnSortEvent.ListHandler<T> sort = new ColumnSortEvent.ListHandler<T>(dataProvider.getList());

        addColumn(column, name);
        return column;
    }
}
