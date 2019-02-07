package catalogApp.client.view.mainPage.library.tabs;

import catalogApp.client.presenter.SongTabPresenter;
import catalogApp.client.view.components.AbstractCatalogCellTable;
import catalogApp.client.view.components.SongCellTable;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

public class SongTabView extends Composite implements SongTabPresenter.Display {

    interface SongTabViewUiBinder extends UiBinder<HTMLPanel, SongTabView> {
    }

    private SongCellTable table;

    @UiField
    SimplePanel simplePanel;
    @UiField
    SimplePager pager;

    private static SongTabViewUiBinder ourUiBinder = GWT.create(SongTabViewUiBinder.class);

    public SongTabView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void initializeTable(ListDataProvider<Song> dataProvider, boolean popupEnabled) {
        table = new SongCellTable(dataProvider, popupEnabled);
        pager.setDisplay(table);
        simplePanel.add(table);
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