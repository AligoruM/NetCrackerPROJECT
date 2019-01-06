package catalogApp.client.view;

import catalogApp.client.presenter.MainScreenPresenter;
import catalogApp.client.services.TestService;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.Comparator;
import java.util.List;


public class MainScreenView extends Composite implements MainScreenPresenter.Display {
    @UiTemplate("MainScreenView.ui.xml")
    interface MainScreenUiBinder extends UiBinder<Panel, MainScreenView> {
    }

    @UiField
    SimplePanel simplePanel;
    @UiField
    Button addButton;

    private static MainScreenUiBinder mainScreenUiBinder = GWT.create(MainScreenUiBinder.class);
    private static TestService testService = GWT.create(TestService.class);
    private final static ListDataProvider<Book> bookDataProvider = new ListDataProvider<>();


    public MainScreenView() {
        initWidget(mainScreenUiBinder.createAndBindUi(this));
        initContent();
    }

    private void initContent() {
        CellTable<Book> bookTable = new CellTable<>();
        testService.getAllBooks(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("doesnt work!");
            }

            @Override
            public void onSuccess(Method method, List<Book> books) {
                bookDataProvider.getList().addAll(books);
                initializeTable(bookTable);
            }
        });
        bookDataProvider.addDataDisplay(bookTable);
        simplePanel.add(bookTable);
    }


    @Override
    public HasClickHandlers getAddButton() {
        return null;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return null;
    }

    @Override
    public Label getWarningLabel() {
        return null;
    }

    @Override
    public TextArea getTimeTA() {
        return null;
    }

    @Override
    public void hideOwnerField() {

    }

    private void initializeTable(CellTable<Book> table){
        TextColumn<Book> nameColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return book.getName();
            }
        };
        TextColumn<Book> authorColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book book) {
                return book.getAuthor().getName();
            }
        };
        table.addColumn(nameColumn, "Name");
        table.addColumn(authorColumn, "Author");
        table.getColumn(0).setSortable(true);
        table.getColumn(1).setSortable(true);

        ListHandler<Book> nameSorter = new ListHandler<>(bookDataProvider.getList());
        nameSorter.setComparator(nameColumn, Comparator.comparing(Book::getName));

        ListHandler<Book> authorSorter = new ListHandler<>(bookDataProvider.getList());
        authorSorter.setComparator(authorColumn, Comparator.comparing(o -> o.getAuthor().getName()));

        table.addColumnSortHandler(nameSorter);
        table.addColumnSortHandler(authorSorter);
    }

    @UiHandler("addButton")
    void doClickCancel(ClickEvent click) {
        new AddDialog();
    }

    public static TestService getTestService() {
        return testService;
    }
}
