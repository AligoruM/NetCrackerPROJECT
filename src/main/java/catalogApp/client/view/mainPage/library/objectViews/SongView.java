package catalogApp.client.view.mainPage.library.objectViews;

import catalogApp.client.presenter.SongPresenter;
import catalogApp.client.view.components.RatingPanel;
import catalogApp.client.view.components.tables.utils.DurationFormatter;
import catalogApp.shared.model.Song;
import com.google.gwt.user.client.ui.Label;

import static catalogApp.client.view.constants.LibraryConstants.*;

public class SongView extends ObjectView implements SongPresenter.Display {

    private Label genreLabel = new Label();
    private Label durationLabel = new Label();

    public SongView() {
        super();
        initTable();
    }

    private void initTable() {
        super.initTable(SONG_LABEL);
        int row = table.insertRow(1);
        table.setWidget(row, 0, new Label(GENRE_LABEL));
        table.setWidget(row, 1, genreLabel);
        table.setWidget(row + 1, 0, new Label(DURATION_LABEL));
        table.setWidget(row + 1, 1, durationLabel);
    }

    @Override
    public void setData(Song song) {
        super.setData(song);
        genreLabel.setText(song.getGenre().getName());
        durationLabel.setText(DurationFormatter.formatDuration(song.getDuration()));
        ratingPanel.setRating(song.getRating());
    }


    public RatingPanel getRatingPanel(){
        return ratingPanel;
    }

}