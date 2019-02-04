package catalogApp.client.view.components;

import catalogApp.client.view.components.utils.SongCellTableColumns;
import catalogApp.shared.model.Song;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;

import static catalogApp.client.view.constants.LibraryConstants.DURATION_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.GENRE_LABEL;

public class SongCellTable extends AbstractCatalogCellTable<Song>{

    public SongCellTable(ListDataProvider<Song> dataProvider) {
        super(dataProvider);

        Column<Song, String> genreColumn = SongCellTableColumns.getSongGenreColumn(true);
        Column<Song, String> durationColumn = SongCellTableColumns.getSongDurationColumn(true);

        addColumn(genreColumn, GENRE_LABEL);
        addSorter(genreColumn, Comparator.comparing(item->item.getGenre().toString()));
        addColumn(durationColumn, DURATION_LABEL);
        addSorter(durationColumn, Comparator.comparing(item->item.getDuration()));
    }
}
