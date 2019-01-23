package catalogApp.client.view.components;


import catalogApp.client.CatalogController;
import catalogApp.shared.model.BaseObject;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

import java.util.Comparator;


public class AbstractCatalogCellTable<T extends BaseObject> extends CellTable<T> {
    private ListDataProvider<T> dataProvider = new ListDataProvider<>();

    private ColumnSortEvent.ListHandler<T> nameSorter;
    private ColumnSortEvent.ListHandler<T> idSorter;

    private Column<T, String> nameColumn = new CellTableColumns().getNameColumn();
    private Column<T, String> idColumn = new CellTableColumns().getIdColumn();

    private boolean isAdmin = CatalogController.isAdmin();

    private final SelectionModel<T> selectionModel = new MultiSelectionModel<>(element -> element.getId());

    public AbstractCatalogCellTable() {
        super(item -> item.getId());
        setWidth("500px", true);
        //super.getElement().getStyle().setBorderWidth(1, com.google.gwt.dom.client.Style.Unit.PX);
        setSelectionModel(selectionModel, DefaultSelectionEventManager.createCheckboxManager());
        Column<T, Boolean> selectionColumn = new Column<T, Boolean>(new CheckboxCell()) {
            @Override
            public Boolean getValue(T object) {
                return selectionModel.isSelected(object);
            }
        };

        addColumn(selectionColumn);
        setColumnWidth(selectionColumn, 40, com.google.gwt.dom.client.Style.Unit.PX);

        if (isAdmin) {
            addColumn(idColumn, "ID");
            setColumnWidth(idColumn, 60, com.google.gwt.dom.client.Style.Unit.PX);
            idSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
            idSorter.setComparator(idColumn, Comparator.comparing(BaseObject::getId));
            addColumnSortHandler(idSorter);
        }
        addColumn(nameColumn, "Name");
        nameSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        nameSorter.setComparator(nameColumn, Comparator.comparing(BaseObject::getName));
        addColumnSortHandler(nameSorter);

    }


    public void setDataProvider(ListDataProvider<T> dataProvider) {
        this.dataProvider = dataProvider;
        this.dataProvider.addDataDisplay(this);
        nameSorter.setList(dataProvider.getList());
        if (isAdmin)
            idSorter.setList(dataProvider.getList());

    }
}
