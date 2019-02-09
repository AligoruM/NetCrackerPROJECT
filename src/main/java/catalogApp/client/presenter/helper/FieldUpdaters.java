package catalogApp.client.presenter.helper;

import catalogApp.client.presenter.BookPresenter;
import catalogApp.client.presenter.SongPresenter;
import catalogApp.client.presenter.UserLibPanelPresenter;
import catalogApp.client.view.mainPage.library.objectViews.BookView;
import catalogApp.client.view.mainPage.library.objectViews.SongView;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import com.google.gwt.cell.client.FieldUpdater;

public class FieldUpdaters {
    public static FieldUpdater<Song, String> songFieldUpdater(SongPresenter songPresenter, UserLibPanelPresenter.Display display){
        return (index, object, value) -> {
            songPresenter.changeSong(object);
            if(!(display.getObjectContainer().getWidget() instanceof SongView)) {
                songPresenter.go(display.getObjectContainer());
            }
        };
    }

    public static FieldUpdater<Book, String> bookFieldUpdater(BookPresenter bookPresenter, UserLibPanelPresenter.Display display){
        return (index, object, value) -> {
            bookPresenter.changeBook(object);
            if(!(display.getObjectContainer().getWidget() instanceof BookView)) {
                bookPresenter.go(display.getObjectContainer());
            }
        };
    }





}
