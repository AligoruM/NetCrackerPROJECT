package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.services.AuthWebService;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class ProfileBarPresenter implements Presenter {
    public interface Display{
        void setDataLabel(String username, String role);
        Widget asWidget();
    }

    private Display display;
    HandlerManager eventBus;

    public ProfileBarPresenter(Display display, HandlerManager eventBus) {
        this.display = display;
        this.eventBus = eventBus;
    }

    @Override
    public void go(HasWidgets container) {
        container.add(display.asWidget());
        bind();
    }

    private void bind(){
    }

    public void setData(SimpleUser simpleUser){
        display.setDataLabel(simpleUser.getUsername(), simpleUser.getRole());

    }
}
