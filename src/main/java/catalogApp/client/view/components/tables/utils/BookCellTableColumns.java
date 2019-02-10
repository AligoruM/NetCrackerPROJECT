package catalogApp.client.view.components.tables.utils;

import catalogApp.shared.model.Book;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

public class BookCellTableColumns {
    private static NumberFormat formatter = NumberFormat.getFormat("#.##");

    public static Column<Book, String> getBookAuthorNameColumn(boolean sortable) {
        return new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getAuthor().getName();
            }

            @Override
            public boolean isSortable() {
                return sortable;
            }

        };
    }
    public static Column<Book, String> getBookRatingColumn(boolean sortable){
        return new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return (object.getRating()<1 ? "-" : formatter.format(object.getRating()));
            }

            @Override
            public boolean isSortable() {
                return sortable;
            }
        };
    }
}
