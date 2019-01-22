package catalogApp.client.view.components;


import catalogApp.client.CatalogController;
import catalogApp.shared.model.BaseObject;
import com.google.gwt.dom.client.Style;
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

    boolean isAdmin = CatalogController.getUser().getRole().equals("ADMIN");

    public AbstractCatalogCellTable() {
        if(isAdmin){
            addColumn(idColumn, "ID");
            setColumnWidth(idColumn, 50, com.google.gwt.dom.client.Style.Unit.PX);
            idSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
            idSorter.setComparator(idColumn, Comparator.comparing(BaseObject::getId));
            addColumnSortHandler(idSorter);
        }
        addColumn(nameColumn, "Name");
        setColumnWidth(nameColumn, 200, com.google.gwt.dom.client.Style.Unit.PX);
        nameSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        nameSorter.setComparator(nameColumn, Comparator.comparing(BaseObject::getName));
        addColumnSortHandler(nameSorter);

    }


    public void setDataProvider(ListDataProvider<T> dataProvider) {
        this.dataProvider = dataProvider;
        this.dataProvider.addDataDisplay(this);
        nameSorter.setList(dataProvider.getList());
        if(isAdmin)
            idSorter.setList(dataProvider.getList());

    }
}
