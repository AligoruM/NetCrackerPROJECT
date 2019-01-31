package catalogApp.client.view.components;

import catalogApp.client.view.components.utils.SongCellTableColumns;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

public class SongCellTable extends AbstractCatalogCellTable{
    @SuppressWarnings("unchecked")
    public SongCellTable(ListDataProvider dataProvider) {
        super(dataProvider);
        Column genreColumn = SongCellTableColumns.getSongGenreColumn(true);
        Column durationColumn = SongCellTableColumns.getSongDurationColumn(true);
        addColumn(genreColumn, "Genre");
        addColumn(durationColumn, "Duration");
    }
}
