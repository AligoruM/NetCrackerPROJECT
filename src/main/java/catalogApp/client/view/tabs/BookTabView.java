package catalogApp.client.view.tabs;

import catalogApp.client.presenter.BookTabPresenter;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
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

    private ListDataProvider<Book> bookDataProvider;
    //private AbstractCatalogCellTable table = new AbstractCatalogCellTable(bookDataProvider.getList());
    private CellTable<Book> table = new CellTable<>();

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

    private void initializeTable(){
        //table.addCustomColumn("Name", IBaseInterface::getName, new TextCell(), true);
        //table.addCustomColumn("Author", IBaseInterface::getAuthorName, new TextCell(), true);


        TextColumn<Book> nameColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return book.getName();
            }
        };
        TextColumn<Book> authorColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return book.getAuthor().getName();
            }
        };
        table.addColumn(nameColumn, "Name");
        table.addColumn(authorColumn, "Author");
        table.getColumn(0).setSortable(true);
        table.getColumn(1).setSortable(true);

        ColumnSortEvent.ListHandler<Book> nameSorter = new ColumnSortEvent.ListHandler<>(bookDataProvider.getList());
        nameSorter.setComparator(nameColumn, Comparator.comparing(Book::getName));

        ColumnSortEvent.ListHandler<Book> authorSorter = new ColumnSortEvent.ListHandler<>(bookDataProvider.getList());
        authorSorter.setComparator(authorColumn, Comparator.comparing(o -> o.getAuthor().getName()));

        table.addColumnSortHandler(nameSorter);
        table.addColumnSortHandler(authorSorter);
    }

    @Override
    public HasClickHandlers getAddButton() {
        return addButton;
    }

    @Override
    public CellTable<Book> getTable() {
        return table;
    }

    @Override
    public void setDataProviderAndInitialize(ListDataProvider<Book> dataProvider) {
        bookDataProvider = dataProvider;
        initializeTable();
    }
}