package catalogApp.client.view.components.tables;

import catalogApp.client.view.components.tables.utils.BookCellTableColumns;
import catalogApp.shared.model.Book;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;
import java.util.List;

import static catalogApp.client.view.constants.LibraryConstants.AUTHOR_COL_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.RATING_COL_LABEL;

public class BookCellTable extends AbstractCatalogCellTable<Book> {

    public BookCellTable(List<Book> dataProvider) {
        super(dataProvider);

        Column<Book, String> authorColumn = BookCellTableColumns.getBookAuthorNameColumn(true);
        setColumnWidth(authorColumn, 100, com.google.gwt.dom.client.Style.Unit.PX);
        addColumn(authorColumn, AUTHOR_COL_LABEL);
        addSorter(authorColumn, Comparator.comparing(e-> e.getAuthor().getName()));
        Column<Book, String> ratingColumn = BookCellTableColumns.getBookRatingColumn(true);
        addColumn(ratingColumn, RATING_COL_LABEL);
        addSorter(ratingColumn, Comparator.comparing(Book::getRating));
    }

}
