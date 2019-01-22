package catalogApp.client.view.components;

import catalogApp.shared.model.BaseObject;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

public class CellTableColumns<T extends BaseObject> {

    Column<T, String> getNameColumn(){
        return new Column<T, String>(new TextCell()) {
            @Override
            public String getValue(BaseObject object) {
                return object.getName();
            }

            @Override
            public boolean isSortable() {
                return true;
            }

        };
    }

    Column<T, String> getIdColumn(){
        return new TextColumn<T>() {
            @Override
            public String getValue(BaseObject object) {
                return String.valueOf(object.getId());
            }

            @Override
            public boolean isSortable() {
                return true;
            }

        };
    }

    public static Column<Book, String> getBookAuthorNameColumn(boolean sortable){
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

    public static Column<Song, String> getSongDurationColumn(boolean sortable){
        return new TextColumn<Song>() {
            @Override
            public String getValue(Song object) {
                return object.getDuration()>0? String.valueOf(object.getDuration()) : "???";
            }

            @Override
            public boolean isSortable() {
                return sortable;
            }

        };
    }

    public static Column<Song, String> getSongGenreColumn(boolean sortable){
        return new TextColumn<Song>() {
            @Override
            public String getValue(Song object) {
                return object.getGenre().getName();
            }

            @Override
            public boolean isSortable() {
                return sortable;
            }

        };
    }

}
