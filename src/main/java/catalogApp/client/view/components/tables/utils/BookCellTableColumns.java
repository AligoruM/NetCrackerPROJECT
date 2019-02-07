package catalogApp.client.view.components.tables.utils;

import catalogApp.shared.model.Book;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

public class BookCellTableColumns {
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
}
