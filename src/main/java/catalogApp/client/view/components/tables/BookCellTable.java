package catalogApp.client.view.components.tables;

import catalogApp.client.view.components.tables.utils.BookCellTableColumns;
import catalogApp.shared.model.Book;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;

import static catalogApp.client.view.constants.LibraryConstants.AUTHOR_COL_LABEL;

public class BookCellTable extends AbstractCatalogCellTable<Book> {

    public BookCellTable(ListDataProvider<Book> dataProvider, boolean popupEnabled) {
        super(dataProvider, popupEnabled);

        Column<Book, String> authorColumn = BookCellTableColumns.getBookAuthorNameColumn(true);

        addColumn(authorColumn, AUTHOR_COL_LABEL);
        addSorter(authorColumn, Comparator.comparing(e-> e.getAuthor().getName()));
    }

}
