package catalogApp.client.view.components;

import catalogApp.client.view.components.utils.BookCellTableColumns;
import catalogApp.shared.model.Book;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;

import static catalogApp.client.view.constants.LibraryConstants.AUTHOR_LABEL;

public class BookCellTable extends AbstractCatalogCellTable<Book> {

    public BookCellTable(ListDataProvider<Book> dataProvider) {
        super(dataProvider);

        Column<Book, String> authorColumn = BookCellTableColumns.getBookAuthorNameColumn(true);

        addColumn(authorColumn, AUTHOR_LABEL);
        addSorter(authorColumn, Comparator.comparing(e-> e.getAuthor().getName()));
    }
}
