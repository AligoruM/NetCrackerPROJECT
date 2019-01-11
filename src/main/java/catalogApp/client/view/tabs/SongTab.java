package catalogApp.client.view.tabs;

import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class SongTab extends Composite {
    interface SongPanelUiBinder extends UiBinder<HTMLPanel, SongTab> {
    }

    private final static ListDataProvider<Song> songDataProvider = new ListDataProvider<>();
    //private static TestService testService = TabsView.getTestService();
    //private ObjectTable table = new ObjectTable(songDataProvider.getList());
    private CellTable<Song> table = new CellTable<>();

    Logger logger = java.util.logging.Logger.getLogger("songTab");
    @UiField
    SimplePanel simplePanel;
    @UiField
    Button addButton;

    private static SongPanelUiBinder ourUiBinder = GWT.create(SongPanelUiBinder.class);

    public SongTab() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initContent();
    }

    private void initContent() {
        /*testService.getAllSongs(new MethodCallback<List<Song>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                logger.log(Level.SEVERE, throwable.toString());
                Window.alert("Song doesnt work!");
            }

            @Override
            public void onSuccess(Method method, List<Song> books) {
                songDataProvider.getList().addAll(books);
                initializeTable();
            }
        });
        songDataProvider.addDataDisplay(table);
        simplePanel.add(table);*/
    }

    private void initializeTable(){
        //table.addCustomColumn("Name", IBaseInterface::getName, new TextCell(), true);
        //table.addCustomColumn("Author", IBaseInterface::getAuthorName, new TextCell(), true);

        List<Song> listRef = songDataProvider.getList();
        TextColumn<Song> nameColumn = new TextColumn<Song>() {
            @Override
            public String getValue(Song song) {
                return song.getName();
            }
        };
        TextColumn<Song> genreColumn = new TextColumn<Song>() {
            @Override
            public String getValue(Song song) {
                return song.getGenre().getName();
            }
        };
        TextColumn<Song> albumColumn = new TextColumn<Song>() {
            @Override
            public String getValue(Song song) {
                return song.getAlbum().getName();
            }
        };
        TextColumn<Song> durationColumn = new TextColumn<Song>() {
            @Override
            public String getValue(Song song) {
                return song.getDuration()== 0 ? "???" :Integer.toString(song.getDuration());
            }
        };

        table.addColumn(nameColumn, "Name");
        table.addColumn(genreColumn, "Genre");
        table.addColumn(albumColumn, "Album");
        table.addColumn(durationColumn, "Duration, sec");
        table.getColumn(0).setSortable(true);
        table.getColumn(1).setSortable(true);
        table.getColumn(2).setSortable(true);
        table.getColumn(3).setSortable(true);

        ColumnSortEvent.ListHandler<Song> nameSorter = new ColumnSortEvent.ListHandler<>(listRef);
        nameSorter.setComparator(nameColumn, Comparator.comparing(Song::getName));

        ColumnSortEvent.ListHandler<Song> genreSorter = new ColumnSortEvent.ListHandler<>(listRef);
        genreSorter.setComparator(genreColumn, Comparator.comparing(o -> o.getGenre().getName()));

        ColumnSortEvent.ListHandler<Song> albumSorter = new ColumnSortEvent.ListHandler<>(listRef);
        albumSorter.setComparator(albumColumn, Comparator.comparing(o -> o.getAlbum().getName()));

        ColumnSortEvent.ListHandler<Song> durationSorter = new ColumnSortEvent.ListHandler<>(listRef);
        durationSorter.setComparator(durationColumn, Comparator.comparing(Song::getDuration));

        table.addColumnSortHandler(nameSorter);
        table.addColumnSortHandler(genreSorter);
        table.addColumnSortHandler(albumSorter);
        table.addColumnSortHandler(durationSorter);
    }
}