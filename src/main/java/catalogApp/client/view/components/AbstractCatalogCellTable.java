package catalogApp.client.view.components;


import catalogApp.shared.model.BaseObject;

import com.google.gwt.user.cellview.client.CellTable;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;


public class AbstractCatalogCellTable<T extends BaseObject> extends CellTable<T> {
    private ListDataProvider<T> dataProvider = new ListDataProvider<>();

    private ColumnSortEvent.ListHandler<T> nameSorter;
    private ColumnSortEvent.ListHandler<T> idSorter;

    private Column nameColumn = new CellTableColumns().getNameColumn(true);
    private Column idColumn = new CellTableColumns().getIdColumn(true);

    public AbstractCatalogCellTable() {
        addColumn(idColumn, "ID");
        addColumn(nameColumn, "Name");

        nameSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        nameSorter.setComparator(getColumn(1), Comparator.comparing(BaseObject::getName));
        addColumnSortHandler(nameSorter);

        idSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        nameSorter.setComparator(getColumn(0), Comparator.comparing(BaseObject::getId));
        addColumnSortHandler(idSorter);

    }


    public void setDataProvider(ListDataProvider<T> dataProvider) {
        this.dataProvider = dataProvider;
        this.dataProvider.addDataDisplay(this);
        nameSorter.setList(dataProvider.getList());
        idSorter.setList(dataProvider.getList());

    }
}
