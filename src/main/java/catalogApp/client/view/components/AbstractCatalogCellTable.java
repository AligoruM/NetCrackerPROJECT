package catalogApp.client.view.components;


import catalogApp.client.CatalogController;
import catalogApp.client.view.components.utils.BaseCellTableColumns;
import catalogApp.shared.model.BaseObject;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

import java.util.Comparator;


public class AbstractCatalogCellTable<T extends BaseObject> extends CellTable<T> {

    private final SelectionModel<T> selectionModel = new MultiSelectionModel<>(element -> element.getId());
    @UiConstructor
    public AbstractCatalogCellTable(ListDataProvider<T> dataProvider) {
        super(element->element.getId());

        dataProvider.addDataDisplay(this);

        setWidth("400px", true);
        setSelectionModel(selectionModel, DefaultSelectionEventManager.createCheckboxManager());

        setPageSize(3);

        Column<T, Boolean> selectionColumn = new Column<T, Boolean>(new CheckboxCell()) {
            @Override
            public Boolean getValue(T object) {
                return selectionModel.isSelected(object);
            }
        };
        addColumn(selectionColumn);
        setColumnWidth(selectionColumn, 40, com.google.gwt.dom.client.Style.Unit.PX);
        Column<T, String> nameColumn;
        boolean isAdmin = CatalogController.isAdmin();
        if (isAdmin) {
            Column<T, String> idColumn = new BaseCellTableColumns().getIdColumn();
            addColumn(idColumn, "ID");
            setColumnWidth(idColumn, 60, com.google.gwt.dom.client.Style.Unit.PX);
            ColumnSortEvent.ListHandler<T> idSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
            idSorter.setComparator(idColumn, Comparator.comparing(BaseObject::getId));
            addColumnSortHandler(idSorter);
            nameColumn = new BaseCellTableColumns().getNameColumn();
        }else {
            nameColumn = new BaseCellTableColumns().getNameColumn();
        }
        addColumn(nameColumn, "Name");
        ColumnSortEvent.ListHandler<T> nameSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        nameSorter.setComparator(nameColumn, Comparator.comparing(BaseObject::getName));
        addColumnSortHandler(nameSorter);
    }

}
