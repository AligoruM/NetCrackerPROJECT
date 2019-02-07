package catalogApp.client.view.components.tables.utils;

import catalogApp.shared.model.Song;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

public class SongCellTableColumns {
    public static Column<Song, String> getSongDurationColumn(boolean sortable) {
        return new TextColumn<Song>() {
            @Override
            public String getValue(Song object) {
                return object.getDuration() > 0 ? String.valueOf(object.getDuration()) : "???";
            }

            @Override
            public boolean isSortable() {
                return sortable;
            }

        };
    }

    public static Column<Song, String> getSongGenreColumn(boolean sortable) {
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
