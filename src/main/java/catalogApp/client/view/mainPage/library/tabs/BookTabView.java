package catalogApp.client.view.mainPage.library.tabs;

import catalogApp.client.presenter.BookTabPresenter;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.components.tables.BookCellTable;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

import java.util.List;

import static catalogApp.client.view.constants.LibraryConstants.SEARCH_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.TABLE_HEIGHT;

public class BookTabView extends Composite implements BookTabPresenter.Display {

    interface BookTabViewUiBinder extends UiBinder<HTMLPanel, BookTabView> {
    }

    private BookCellTable table;

    @UiField
    SimplePanel simplePanel;
    @UiField
    TextBox searchField;
    @UiField
    Label searchLabel;


    private static BookTabViewUiBinder ourUiBinder = GWT.create(BookTabViewUiBinder.class);

    public BookTabView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void initializeTable(List<Book> list) {
        table = new BookCellTable(list);
        simplePanel.add(table);
        simplePanel.setHeight(TABLE_HEIGHT);
        searchLabel.setText(SEARCH_LABEL);
    }

    @Override
    public AbstractCatalogCellTable<Book> getTable(){
        return table;
    }

    @Override
    public void setListAndInitialize(List<Book> list) {
        initializeTable(list);
    }

    @Override
    public MultiSelectionModel<Book> getSelectionModel() {
        return ((MultiSelectionModel<Book>) table.getSelectionModel());
    }

    public TextBox getSearchField() {
        return searchField;
    }
}