package catalogApp.client.view.mainPage.library.tabs;

import catalogApp.client.presenter.SongTabPresenter;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.components.tables.SongCellTable;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.MultiSelectionModel;

import java.util.List;

import static catalogApp.client.view.constants.LibraryConstants.SEARCH_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.TABLE_HEIGHT;

public class SongTabView extends Composite implements SongTabPresenter.Display {

    interface SongTabViewUiBinder extends UiBinder<HTMLPanel, SongTabView> {
    }

    private SongCellTable table;

    @UiField
    SimplePanel simplePanel;
    @UiField
    TextBox searchField;
    @UiField
    Label searchLabel;


    private static SongTabViewUiBinder ourUiBinder = GWT.create(SongTabViewUiBinder.class);

    public SongTabView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void initializeTable(List<Song> list) {
        table = new SongCellTable(list);
        simplePanel.add(table);
        simplePanel.setHeight(TABLE_HEIGHT);
        searchLabel.setText(SEARCH_LABEL);
    }

    @Override
    public AbstractCatalogCellTable<Song> getTable(){
        return table;
    }

    @Override
    public TextBox getSearchField() {
        return searchField;
    }


    public void setListAndInitialize(List<Song> list) {
        initializeTable(list);
    }

    public MultiSelectionModel<Song> getSelectionModel(){
        return ((MultiSelectionModel<Song>) table.getSelectionModel());
    }
}