package catalogApp.client.view.tabs;

import catalogApp.client.services.TestService;
import catalogApp.client.view.MainScreenView;
import catalogApp.client.view.dialogs.AddBookDialog;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.Comparator;
import java.util.List;

public class BookTab extends Composite {
    interface BookTabUiBinder extends UiBinder<HTMLPanel, BookTab> {
    }

    private final static ListDataProvider<Book> bookDataProvider = new ListDataProvider<>();
    private static TestService testService = MainScreenView.getTestService();

    @UiField
    SimplePanel simplePanel;
    @UiField
    Button addButton;

    private static BookTabUiBinder ourUiBinder = GWT.create(BookTabUiBinder.class);

    public BookTab() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initContent();
    }

    private void initContent() {
        CellTable<Book> bookTable = new CellTable<>();
        testService.getAllBooks(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Book doesnt work!");
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

        ColumnSortEvent.ListHandler<Book> nameSorter = new ColumnSortEvent.ListHandler<>(bookDataProvider.getList());
        nameSorter.setComparator(nameColumn, Comparator.comparing(Book::getName));

        ColumnSortEvent.ListHandler<Book> authorSorter = new ColumnSortEvent.ListHandler<>(bookDataProvider.getList());
        authorSorter.setComparator(authorColumn, Comparator.comparing(o -> o.getAuthor().getName()));

        table.addColumnSortHandler(nameSorter);
        table.addColumnSortHandler(authorSorter);
    }

    @UiHandler("addButton")
    void doClickCancel(ClickEvent click) {
        new AddBookDialog();
    }
}