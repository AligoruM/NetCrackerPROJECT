package catalogApp.client.view.mainPage.tabs;

import catalogApp.client.presenter.BookTabPresenter;
import catalogApp.client.view.components.AbstractCatalogCellTable;
import catalogApp.client.view.components.CellTableColumns;
import catalogApp.shared.model.BaseObject;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;
import java.util.logging.Logger;

public class BookTabView extends Composite implements BookTabPresenter.Display {

    interface BookTabViewUiBinder extends UiBinder<HTMLPanel, BookTabView> {
    }

    private AbstractCatalogCellTable<Book> table = new AbstractCatalogCellTable<>();

    Logger logger = java.util.logging.Logger.getLogger("bookTab");
    @UiField
    SimplePanel simplePanel;
    @UiField
    Button addButton;

    private static BookTabViewUiBinder ourUiBinder = GWT.create(BookTabViewUiBinder.class);

    public BookTabView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        simplePanel.add(table);
    }

    private void initializeTable(ListDataProvider<Book> dataProvider){
        table.setDataProvider(dataProvider);
        table.addColumn(CellTableColumns.getBookAuthorNameColumn(true), "Author");

        ColumnSortEvent.ListHandler<Book> authorSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        authorSorter.setComparator(table.getColumn(2), Comparator.comparing(BaseObject::getId));
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
}