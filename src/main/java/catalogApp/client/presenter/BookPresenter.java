package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.client.view.components.RatingPanel;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class BookPresenter implements Presenter {
    public interface Display {
        void setData(Book book);

        Widget asWidget();

        RatingPanel getRatingPanel();
    }

    private Book book;

    private Display display;

    private ListDataProvider<Book> dataProvider;

    public BookPresenter(Display display, BookWebService bookWebService, ListDataProvider<Book> dataProvider) {
        this.display = display;
        this.dataProvider = dataProvider;
        display.getRatingPanel().getOneButton().addClickHandler(event -> bookWebService.markBook(book.getId(), 1, getMethodCallback()));
        display.getRatingPanel().getTwoButton().addClickHandler(event -> bookWebService.markBook(book.getId(), 2, getMethodCallback()));
        display.getRatingPanel().getThreeButton().addClickHandler(event -> bookWebService.markBook(book.getId(), 3, getMethodCallback()));
        display.getRatingPanel().getFourButton().addClickHandler(event -> bookWebService.markBook(book.getId(), 4, getMethodCallback()));
        display.getRatingPanel().getFiveButton().addClickHandler(event -> bookWebService.markBook(book.getId(), 5, getMethodCallback()));
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(display.asWidget());
    }

    public void changeBook(Book book) {
        this.book = book;
        display.setData(book);
    }

    private MethodCallback<Float> getMethodCallback() {
        return new MethodCallback<Float>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("something in book marking went wrong...");
            }

            @Override
            public void onSuccess(Method method, Float response) {
                book.setRating(response);
                display.setData(book);
                if (dataProvider != null)
                    dataProvider.refresh();
            }
        };
    }
}
