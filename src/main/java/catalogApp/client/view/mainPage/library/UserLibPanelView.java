package catalogApp.client.view.mainPage.library;

import catalogApp.client.presenter.UserLibPanelPresenter;
import catalogApp.client.view.components.AbstractCatalogCellTable;
import catalogApp.client.view.mainPage.library.tabs.BookTabView;
import catalogApp.client.view.mainPage.library.tabs.SongTabView;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

public class UserLibPanelView extends Composite implements UserLibPanelPresenter.Display {
    interface UserLibPanelViewUiBinder extends UiBinder<HTMLPanel, UserLibPanelView> {

    }
    @UiField
    BookTabView bookView;
    @UiField
    SongTabView songView;
    @UiField
    Button deleteBooksButton;
    @UiField
    Button deleteSongsButton;
    @UiField
    HTMLPanel mainPanel;
    @UiField
    SimplePanel objectContainer;

    private static UserLibPanelViewUiBinder ourUiBinder = GWT.create(UserLibPanelViewUiBinder.class);

    public UserLibPanelView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        mainPanel.getElement().getStyle().setPaddingLeft(100, Style.Unit.PX);
        objectContainer.getElement().getStyle().setPaddingLeft(50, Style.Unit.PX);
    }

    @Override
    public void setBookDataProvider(ListDataProvider<Book> dataProvider, boolean popupEnabled) {
        bookView.setDataProviderAndInitialize(dataProvider, popupEnabled);
    }

    @Override
    public void setSongDataProvider(ListDataProvider<Song> dataProvider, boolean popupEnabled) {
        songView.setDataProviderAndInitialize(dataProvider, popupEnabled);
    }

    @Override
    public AbstractCatalogCellTable<Book> getBookTable() {
        return bookView.getTable();
    }

    @Override
    public AbstractCatalogCellTable<Song> getSongTable() {
        return songView.getTable();
    }

    @Override
    public MultiSelectionModel<Book> getSelectionBooksModel() {
        return bookView.getSelectionModel();
    }

    @Override
    public MultiSelectionModel<Song> getSelectionSongsModel() {
        return songView.getSelectionModel();
    }

    @Override
    public Button getDeleteBooksButton() {
        return deleteBooksButton;
    }

    @Override
    public Button getDeleteSongsButton() {
        return deleteSongsButton;
    }

    @Override
    public SimplePanel getObjectContainer() {
        return objectContainer;
    }
}