package catalogApp.client.view.mainPage.library.tabs;

import catalogApp.client.presenter.SongTabPresenter;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.components.tables.SongCellTable;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import static catalogApp.client.view.constants.LibraryConstants.TABLE_HEIGHT;

public class SongTabView extends Composite implements SongTabPresenter.Display {

    interface SongTabViewUiBinder extends UiBinder<HTMLPanel, SongTabView> {
    }

    private SongCellTable table;

    @UiField
    SimplePanel simplePanel;


    private static SongTabViewUiBinder ourUiBinder = GWT.create(SongTabViewUiBinder.class);

    public SongTabView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void initializeTable(ListDataProvider<Song> dataProvider, boolean popupEnabled) {
        table = new SongCellTable(dataProvider, popupEnabled);
        simplePanel.add(table);
        simplePanel.setHeight(TABLE_HEIGHT);
    }

    @Override
    public AbstractCatalogCellTable<Song> getTable(){
        return table;
    }



    @Override
    public void setDataProviderAndInitialize(ListDataProvider<Song> dataProvider, boolean popupEnabled) {
        initializeTable(dataProvider, popupEnabled);
    }

    public MultiSelectionModel<Song> getSelectionModel(){
        return ((MultiSelectionModel<Song>) table.getSelectionModel());
    }
}