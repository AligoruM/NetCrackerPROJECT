package catalogApp.client.view.components;


import catalogApp.client.CatalogController;
import catalogApp.client.view.components.utils.BaseCellTableColumns;
import catalogApp.shared.model.BaseObject;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

import java.util.Comparator;

import static catalogApp.client.view.constants.LibraryConstants.ID_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.NAME_LABEL;


public class AbstractCatalogCellTable<T extends BaseObject> extends CellTable<T> {

    private ListDataProvider<T> dataProvider;

    @UiConstructor
    AbstractCatalogCellTable(ListDataProvider<T> dataProvider) {
        super(element->element.getId());
        this.dataProvider=dataProvider;
        dataProvider.addDataDisplay(this);

        SelectionModel<T> selectionModel = new MultiSelectionModel<>(element -> element.getId());

        setSelectionModel(selectionModel, DefaultSelectionEventManager.createCheckboxManager());

        setPageSize(3);

        BaseCellTableColumns<T> baseCellTableColumns = new BaseCellTableColumns<>();

        addColumn(baseCellTableColumns.getSelectionColumn(selectionModel));

        if (CatalogController.isAdmin()) {
            Column<T, String> idColumn = baseCellTableColumns.getIdColumn();
            addColumn(idColumn, ID_LABEL);
            addSorter(idColumn, Comparator.comparing(BaseObject::getId));
        }

        Column<T, String> nameColumn = baseCellTableColumns.getNameColumn();
        addColumn(nameColumn, NAME_LABEL);
        addSorter(nameColumn, Comparator.comparing(BaseObject::getName));
    }

    void addSorter(Column<T, String> column, Comparator<T> comparator){
        ColumnSortEvent.ListHandler<T> sorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        sorter.setComparator(column, comparator);
        addColumnSortHandler(sorter);
    }

}
