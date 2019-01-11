package catalogApp.client;

import catalogApp.client.services.TestService;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.fusesource.restygwt.client.Defaults;


public class CatalogApplication implements EntryPoint {

    static {
        Defaults.setDateFormat(null);
    }

    public void onModuleLoad() {
        HandlerManager eventBus = new HandlerManager(null);
        TestService testService = GWT.create(TestService.class);
        CatalogController contr = new CatalogController(eventBus, testService);
    }
}
