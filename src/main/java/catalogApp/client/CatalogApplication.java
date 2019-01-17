package catalogApp.client;

import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Defaults;


public class CatalogApplication implements EntryPoint {

    static {
        Defaults.setDateFormat(null);
    }

    public void onModuleLoad() {
        HandlerManager eventBus = new HandlerManager(null);
        SongWebService songWebService = GWT.create(SongWebService.class);
        BookWebService bookWebService = GWT.create(BookWebService.class);
        CatalogController contr = new CatalogController(eventBus, bookWebService, songWebService);
        contr.go(RootPanel.get());
    }
}
