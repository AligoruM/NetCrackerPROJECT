package catalogApp.client.view.mainPage.tabs;

import catalogApp.client.presenter.SongTabPresenter;
import catalogApp.client.view.components.AbstractCatalogCellTable;
import catalogApp.client.view.components.CellTableColumns;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;

import java.util.logging.Logger;

public class SongTabView extends Composite implements SongTabPresenter.Display {

    interface SongTabViewUiBinder extends UiBinder<HTMLPanel, SongTabView> {
    }

    private AbstractCatalogCellTable<Song> table = new AbstractCatalogCellTable<>();

    @UiField
    SimplePanel simplePanel;
    @UiField
    Button addButton;
    @UiField
    SimplePager pager;

    private static SongTabViewUiBinder ourUiBinder = GWT.create(SongTabViewUiBinder.class);

    public SongTabView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        simplePanel.add(table);
    }

    private void initializeTable(ListDataProvider<Song> dataProvider){
        pager.setDisplay(table);
        table.setPageSize(3);
        table.setDataProvider(dataProvider);
        table.addColumn(CellTableColumns.getSongGenreColumn(true), "Genre");
        table.addColumn(CellTableColumns.getSongDurationColumn(true), "Duration");
    }


    @Override
    public HasClickHandlers getAddButton() {
        return addButton;
    }

    @Override
    public void setDataProviderAndInitialize(ListDataProvider<Song> dataProvider) {
        initializeTable(dataProvider);
    }
}