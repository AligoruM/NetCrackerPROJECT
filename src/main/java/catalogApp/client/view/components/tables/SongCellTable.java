package catalogApp.client.view.components.tables;

import catalogApp.client.view.components.tables.utils.SongCellTableColumns;
import catalogApp.shared.model.Song;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;
import java.util.List;

import static catalogApp.client.view.constants.LibraryConstants.DURATION_COL_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.GENRE_COL_LABEL;

public class SongCellTable extends AbstractCatalogCellTable<Song>{

    public SongCellTable(List<Song> list) {
        super(list);

        Column<Song, String> genreColumn = SongCellTableColumns.getSongGenreColumn(true);
        Column<Song, String> durationColumn = SongCellTableColumns.getSongDurationColumn(true);

        addColumn(genreColumn, GENRE_COL_LABEL);
        addSorter(genreColumn, Comparator.comparing(item->item.getGenre().toString()));
        addColumn(durationColumn, DURATION_COL_LABEL);
        addSorter(durationColumn, Comparator.comparing(item->item.getDuration()));
    }

}
