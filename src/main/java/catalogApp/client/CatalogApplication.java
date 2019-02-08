package catalogApp.client;

import catalogApp.client.services.BookWebService;
import catalogApp.client.services.SongWebService;
import catalogApp.client.services.UserWebService;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;


public class CatalogApplication implements EntryPoint {


    public void onModuleLoad() {
        HandlerManager eventBus = new HandlerManager(null);
        SongWebService songWebService = GWT.create(SongWebService.class);
        BookWebService bookWebService = GWT.create(BookWebService.class);
        UserWebService userWebService = GWT.create(UserWebService.class);
        new CatalogController(eventBus, bookWebService, songWebService, userWebService);
    }
}
