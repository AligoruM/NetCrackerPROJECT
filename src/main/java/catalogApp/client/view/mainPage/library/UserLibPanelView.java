package catalogApp.client.view.mainPage.library;

import catalogApp.client.presenter.UserLibPanelPresenter;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
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
    Button deleteBooksButton;
    @UiField
    Button deleteSongsButton;
    @UiField
    HTMLPanel mainPanel;
    @UiField
    SimplePanel objectContainer;
    @UiField
    SimplePanel bookPanel;
    @UiField
    SimplePanel songPanel;


    private static UserLibPanelViewUiBinder ourUiBinder = GWT.create(UserLibPanelViewUiBinder.class);

    public UserLibPanelView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        mainPanel.getElement().getStyle().setPaddingLeft(30, Style.Unit.PX);
        objectContainer.getElement().getStyle().setPaddingLeft(30, Style.Unit.PX);
    }

    @Override
    public SimplePanel getBookContainer() {
        return bookPanel;
    }

    @Override
    public SimplePanel getSongContainer() {
        return songPanel;
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