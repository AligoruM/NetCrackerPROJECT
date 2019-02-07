package catalogApp.client.presenter;

import catalogApp.shared.model.Book;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class BookPresenter implements Presenter {
    public interface Display{
        void setData(Book book);
        Widget asWidget();
    }

    Display display;

    public BookPresenter(Display display) {
        this.display = display;
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(display.asWidget());
    }

    public void changeBook(Book book){
        display.setData(book);
    }
}
