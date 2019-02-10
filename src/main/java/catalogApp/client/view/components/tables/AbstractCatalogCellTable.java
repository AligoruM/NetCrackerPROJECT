package catalogApp.client.view.components.tables;


import catalogApp.client.CatalogController;
import catalogApp.client.view.components.tables.utils.BaseCellTableColumns;
import catalogApp.shared.model.BaseObject;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static catalogApp.client.view.constants.LibraryConstants.*;


public abstract class AbstractCatalogCellTable<T extends BaseObject> extends CellTable<T> {

    private BaseCellTableColumns<T> baseCellTableColumns = new BaseCellTableColumns<>();

    private Column<T, String> nameColumn = baseCellTableColumns.getNameColumn();

    ObjectPopup objectPopup = new ObjectPopup();

    private List<T> list;

    @UiConstructor
    AbstractCatalogCellTable(List<T> list) {
        super(element -> element.getId());
        this.list = list;
        MultiSelectionModel<T> selectionModel = new MultiSelectionModel<>(element -> element.getId());

        setSelectionModel(selectionModel, DefaultSelectionEventManager.createCheckboxManager());
        Column<T, Boolean> selectionColumn = baseCellTableColumns.getSelectionColumn(selectionModel);
        addColumn(selectionColumn);
        setColumnWidth(selectionColumn, 55, com.google.gwt.dom.client.Style.Unit.PX);
        if (CatalogController.isAdmin()) {
            Column<T, String> idColumn = baseCellTableColumns.getIdColumn();
            addColumn(baseCellTableColumns.getArchivedColumn(), ARCHIVED_LABEL);
            addColumn(idColumn, ID_COL_LABEL);
            addSorter(idColumn, Comparator.comparing(BaseObject::getId));
        }




        setColumnWidth(nameColumn, 140, com.google.gwt.dom.client.Style.Unit.PX);
        addColumn(nameColumn, NAME_COL_LABEL);
        addSorter(nameColumn, Comparator.comparing(BaseObject::getName));

    }


    final void addSorter(Column<T, String> column, Comparator<T> comparator) {
        ColumnSortEvent.ListHandler<T> sorter = new ColumnSortEvent.ListHandler<>(list);
        sorter.setComparator(column, comparator);
        addColumnSortHandler(sorter);
    }

    public void setNameColumnFieldUpdater(FieldUpdater<T, String> fieldUpdater) {
        nameColumn.setFieldUpdater(fieldUpdater);
    }

    public void enablePopup(){
        nameColumn.setFieldUpdater((index, object, value) -> {
            int left = getRowElement(index).getAbsoluteLeft();
            int top = getRowElement(index).getAbsoluteTop();
            objectPopup.setData(object);
            objectPopup.setPopupPositionAndShow(left, top);
        });
    }

    private class ObjectPopup {
        final PopupPanel contactPopup = new PopupPanel(true, false);
        final HTML comment = new HTML();
        final Image img = new Image();

        ObjectPopup() {
            HorizontalPanel popupContainer = new HorizontalPanel();
            popupContainer.setSpacing(5);
            popupContainer.add(img);
            popupContainer.add(comment);
            contactPopup.setWidget(popupContainer);
        }

        void setData(BaseObject object) {
            if (object.getImagePath() != null) {
                if (!img.getUrl().contains(object.getImagePath())) {
                    img.setUrl(object.getImagePath());
                }
            } else if (!img.getUrl().contains(DEFAULT_OBJECT_IMAGE)) {
                img.setUrl(DEFAULT_OBJECT_IMAGE);
            }
            img.setSize(POPUP_IMG_SIZE, POPUP_IMG_SIZE);
            comment.setHTML(object.getComment() != null ? object.getComment() : EMPTY_COMMENT);
        }

        void setPopupPositionAndShow(int left, int top) {
            contactPopup.setPopupPosition(left + 50, top + 10);
            contactPopup.show();
        }
    }

}
