package catalogApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


public class CatalogApplication implements EntryPoint {

    public void onModuleLoad() {
        CatalogController contr = new CatalogController();
        contr.go(RootPanel.get());
    }
}
