package catalogApp.client.view.mainPage.library.objectViews;

import catalogApp.client.presenter.BookPresenter;
import catalogApp.shared.model.Book;
import com.google.gwt.user.client.ui.Label;

import static catalogApp.client.view.constants.LibraryConstants.AUTHOR_LABEL;
import static catalogApp.client.view.constants.LibraryConstants.BOOK_LABEL;

public class BookView extends ObjectView implements BookPresenter.Display {

    private Label authorLabel = new Label();

    public BookView() {
        super();
        initTable();
    }

    private void initTable(){
        super.initTable(BOOK_LABEL);
        int row = table.insertRow(1);
        table.setWidget(row, 0, new Label(AUTHOR_LABEL));
        table.setWidget(row,1, authorLabel);
    }


    @Override
    public void setData(Book book){
        super.setData(book);
        authorLabel.setText(book.getAuthor().getName());
    }
}