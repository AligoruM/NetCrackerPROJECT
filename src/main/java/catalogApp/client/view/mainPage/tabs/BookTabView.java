package catalogApp.client.view.mainPage.tabs;

import catalogApp.client.presenter.BookTabPresenter;
import catalogApp.client.view.components.AbstractCatalogCellTable;
import catalogApp.client.view.components.CellTableColumns;
import catalogApp.shared.model.BaseObject;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class BookTabView extends Composite implements BookTabPresenter.Display {

    interface BookTabViewUiBinder extends UiBinder<HTMLPanel, BookTabView> {
    }

    private AbstractCatalogCellTable<Book> table = new AbstractCatalogCellTable<>();

    @UiField
    SimplePanel simplePanel;
    @UiField
    Button addButton;
    @UiField
    SimplePager pager;

    private static BookTabViewUiBinder ourUiBinder = GWT.create(BookTabViewUiBinder.class);

    public BookTabView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        simplePanel.add(table);
    }

    private void initializeTable(ListDataProvider<Book> dataProvider){
        pager.setDisplay(table);
        table.setPageSize(3);
        table.setDataProvider(dataProvider);

        Column authorColumn = CellTableColumns.getBookAuthorNameColumn(true);
        table.addColumn(authorColumn, "Author");
        //table.setColumnWidth(authorColumn, 200, Style.Unit.PX);
        ColumnSortEvent.ListHandler<Book> authorSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        authorSorter.setComparator(authorColumn, Comparator.comparing(BaseObject::getId));
        table.addColumnSortHandler(authorSorter);
    }

    @Override
    public HasClickHandlers getAddButton() {
        return addButton;
    }

    @Override
    public void setDataProviderAndInitialize(ListDataProvider<Book> dataProvider) {
        initializeTable(dataProvider);
    }

    @Override
    public Set<Book> getSelectedItems() {
        return ((MultiSelectionModel<Book>)table.getSelectionModel()).getSelectedSet();
    }
}