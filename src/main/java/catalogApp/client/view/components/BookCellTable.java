package catalogApp.client.view.components;

import catalogApp.client.view.components.utils.BaseCellTableColumns;
import catalogApp.client.view.components.utils.BookCellTableColumns;
import catalogApp.shared.model.Book;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;

public class BookCellTable extends AbstractCatalogCellTable {
    @SuppressWarnings("unchecked")
    public BookCellTable(ListDataProvider<Book> dataProvider) {
        super(dataProvider);
        Column authorColumn = BookCellTableColumns.getBookAuthorNameColumn(true);
        addColumn(authorColumn, "Author");
        ColumnSortEvent.ListHandler<Book> authorSorter = new ColumnSortEvent.ListHandler<>(dataProvider.getList());
        authorSorter.setComparator(authorColumn, Comparator.comparing(e-> e.getAuthor().getName()));
        addColumnSortHandler(authorSorter);
    }
}
