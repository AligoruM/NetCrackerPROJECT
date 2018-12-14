package catalogApp.client;

import catalogApp.client.presenter.MainScreenPresenter;
import catalogApp.client.view.MainScreenView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.GWT;

public class CatalogApp implements EntryPoint {

    public void onModuleLoad() {
        MainScreenPresenter MSP = new MainScreenPresenter(new MainScreenView());
    }
}
