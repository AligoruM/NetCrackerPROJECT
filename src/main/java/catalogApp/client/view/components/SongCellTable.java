package catalogApp.client.view.components;

import catalogApp.client.view.components.utils.SongCellTableColumns;
import catalogApp.shared.model.Song;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;

import static catalogApp.client.view.constants.LibraryConstants.DURATION_COL_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.GENRE_COL_LABEL;

public class SongCellTable extends AbstractCatalogCellTable<Song>{

    public SongCellTable(ListDataProvider<Song> dataProvider, boolean popupEnabled) {
        super(dataProvider, popupEnabled);

        Column<Song, String> genreColumn = SongCellTableColumns.getSongGenreColumn(true);
        Column<Song, String> durationColumn = SongCellTableColumns.getSongDurationColumn(true);

        addColumn(genreColumn, GENRE_COL_LABEL);
        addSorter(genreColumn, Comparator.comparing(item->item.getGenre().toString()));
        addColumn(durationColumn, DURATION_COL_LABEL);
        addSorter(durationColumn, Comparator.comparing(item->item.getDuration()));
    }

}
