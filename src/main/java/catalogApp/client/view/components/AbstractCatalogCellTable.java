package catalogApp.client.view.components;


import catalogApp.client.CatalogController;
import catalogApp.client.view.components.utils.BaseCellTableColumns;
import catalogApp.shared.model.BaseObject;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

import java.util.Comparator;

import static catalogApp.client.view.constants.LibraryConstants.*;


public abstract class AbstractCatalogCellTable<T extends BaseObject> extends CellTable<T> {

    private ListDataProvider<T> dataProvider;

    private BaseCellTableColumns<T> baseCellTableColumns = new BaseCellTableColumns<>();

    private Column<T, String> nameColumn = baseCellTableColumns.getNameColumn();

    @UiConstructor
    AbstractCatalogCellTable(ListDataProvider<T> dataProvider, boolean popupEnabled) {
        super(element->element.getId());

        ObjectPopup objectPopup = new ObjectPopup();

        this.dataProvider=dataProvider;
        dataProvider.addDataDisplay(this);

        SelectionModel<T> selectionModel = new MultiSelectionModel<>(element -> element.getId());

        setSelectionModel(selectionModel, DefaultSelectionEventManager.createCheckboxManager());

        setPageSize(3);


        addColumn(baseCellTableColumns.getSelectionColumn(selectionModel));

        if (CatalogController.isAdmin()) {
            Column<T, String> idColumn = baseCellTableColumns.getIdColumn();
            addColumn(baseCellTableColumns.getArchivedColumn(), ARCHIVED_LABEL);
            addColumn(idColumn, ID_COL_LABEL);
            addSorter(idColumn, Comparator.comparing(BaseObject::getId));
        }

        if(popupEnabled) {
            nameColumn.setFieldUpdater((index, object, value) -> {
                int left = getElement().getAbsoluteLeft();
                int top = getElement().getAbsoluteTop();
                objectPopup.setData(object);
                objectPopup.setPopupPositionAndShow(left, top);
            });
        }

        addColumn(nameColumn, NAME_COL_LABEL);
        addSorter(nameColumn, Comparator.comparing(BaseObject::getName));
    }

    void addSorter(Column<T, String> column, Comparator<T> comparator){
        ColumnSortEvent.ListHandler<T> sorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        sorter.setComparator(column, comparator);
        addColumnSortHandler(sorter);
    }

    public void setNameColumnFieldUpdater(FieldUpdater<T, String> fieldUpdater){
        nameColumn.setFieldUpdater(fieldUpdater);
    }

    private class ObjectPopup {
        final PopupPanel contactPopup = new PopupPanel(true, false);
        final HTML contactInfo = new HTML();
        final Image img = new Image();
        ObjectPopup(){
            HorizontalPanel contactPopupContainer  = new HorizontalPanel();
            contactPopupContainer.setSpacing(5);
            contactPopupContainer.add(img);
            contactPopupContainer.add(contactInfo);
            contactPopup.setWidget(contactPopupContainer);
        }

        void setData(T object){
            img.setUrl(object.getImagePath()!=null? object.getImagePath() : DEFAULT_OBJECT_IMAGE);
            img.setSize(POPUP_IMG_SIZE, POPUP_IMG_SIZE);
            contactInfo.setHTML(object.getComment()!=null ? object.getComment() : EMPTY_COMMENT);
        }

        void setPopupPositionAndShow(int left, int top){
            contactPopup.setPopupPosition(left+50, top+10);
            contactPopup.show();
        }
    }

}
