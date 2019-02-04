package catalogApp.client.view.mainPage.library.tabs;

import catalogApp.client.presenter.BookTabPresenter;
import catalogApp.client.view.components.BookCellTable;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;

public class BookTabView extends Composite implements BookTabPresenter.Display {

    interface BookTabViewUiBinder extends UiBinder<HTMLPanel, BookTabView> {
    }

    private BookCellTable table;

    @UiField
    SimplePanel simplePanel;
    @UiField
    SimplePager pager;

    private static BookTabViewUiBinder ourUiBinder = GWT.create(BookTabViewUiBinder.class);

    public BookTabView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void initializeTable(ListDataProvider<Book> dataProvider) {
        table = new BookCellTable(dataProvider);
        pager.setDisplay(table);
        simplePanel.add(table);
    }



    @Override
    public void setDataProviderAndInitialize(ListDataProvider<Book> dataProvider) {
        initializeTable(dataProvider);
    }

    @Override
    public MultiSelectionModel<Book> getSelectionModel() {
        return ((MultiSelectionModel<Book>) table.getSelectionModel());
    }

}