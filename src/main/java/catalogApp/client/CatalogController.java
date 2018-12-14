package catalogApp.client;

import catalogApp.client.presenter.Presenter;
import catalogApp.client.view.MainScreenView;
import com.google.gwt.user.client.ui.*;

public class CatalogController implements Presenter {

    public void go(HasWidgets container) {
        SplitLayoutPanel spl = new SplitLayoutPanel();
        MainScreenView smp = new MainScreenView();
        spl.add(smp);
        container.add(spl);
    }
}
