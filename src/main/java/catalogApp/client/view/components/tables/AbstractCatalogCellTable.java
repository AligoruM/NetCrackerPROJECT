package catalogApp.client.view.components.tables;


import catalogApp.client.CatalogController;
import catalogApp.client.view.components.tables.utils.BaseCellTableColumns;
import catalogApp.shared.model.BaseObject;
import com.google.gwt.cell.client.FieldUpdater;
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

import java.util.Comparator;

import static catalogApp.client.view.constants.LibraryConstants.*;


public abstract class AbstractCatalogCellTable<T extends BaseObject> extends CellTable<T> {

    private ListDataProvider<T> dataProvider;

    private BaseCellTableColumns<T> baseCellTableColumns = new BaseCellTableColumns<>();

    private Column<T, String> nameColumn = baseCellTableColumns.getNameColumn();

    @UiConstructor
    AbstractCatalogCellTable(ListDataProvider<T> dataProvider, boolean popupEnabled) {
        super(element->element.getId());

        this.dataProvider=dataProvider;
        dataProvider.addDataDisplay(this);

        MultiSelectionModel<T> selectionModel = new MultiSelectionModel<>(element -> element.getId());

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
            ObjectPopup objectPopup = new ObjectPopup();
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


    final void addSorter(Column<T, String> column, Comparator<T> comparator){
        ColumnSortEvent.ListHandler<T> sorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        sorter.setComparator(column, comparator);
        addColumnSortHandler(sorter);
    }

    public void setNameColumnFieldUpdater(FieldUpdater<T, String> fieldUpdater){
        nameColumn.setFieldUpdater(fieldUpdater);
    }

    private class ObjectPopup {
        final PopupPanel contactPopup = new PopupPanel(true, false);
        final HTML comment = new HTML();
        final Image img = new Image();
        ObjectPopup(){
            HorizontalPanel popupContainer  = new HorizontalPanel();
            popupContainer.setSpacing(5);
            popupContainer.add(img);
            popupContainer.add(comment);
            contactPopup.setWidget(popupContainer);
        }

        void setData(T object){
            if (object.getImagePath() != null) {
                if (!img.getUrl().contains(object.getImagePath())) {
                    img.setUrl(object.getImagePath());
                }
            } else if(!img.getUrl().contains(DEFAULT_OBJECT_IMAGE)) {
                img.setUrl(DEFAULT_OBJECT_IMAGE);
            }
            img.setSize(POPUP_IMG_SIZE, POPUP_IMG_SIZE);
            comment.setHTML(object.getComment()!=null ? object.getComment() : EMPTY_COMMENT);
        }

        void setPopupPositionAndShow(int left, int top){
            contactPopup.setPopupPosition(left+50, top+10);
            contactPopup.show();
        }
    }

}
