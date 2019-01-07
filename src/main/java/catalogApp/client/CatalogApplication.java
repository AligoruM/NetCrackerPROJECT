package catalogApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Defaults;


public class CatalogApplication implements EntryPoint {

    static {
        Defaults.setDateFormat(null);
    }

    public void onModuleLoad() {
        CatalogController contr = new CatalogController();
        contr.go(RootPanel.get());
    }
}
