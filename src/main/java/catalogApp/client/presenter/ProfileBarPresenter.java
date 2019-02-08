package catalogApp.client.presenter;

import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ProfileBarPresenter implements Presenter {
    public interface Display {
        void setDataLabel(String username, String role);
        Button getLogoutButton();
        Widget asWidget();
    }

    private Display display;
    private HandlerManager eventBus;

    public ProfileBarPresenter(Display display, HandlerManager eventBus) {
        this.display = display;
        this.eventBus = eventBus;
    }

    @Override
    public void go(Panel container) {
        container.add(display.asWidget());
        display.getLogoutButton().addClickHandler(event -> {
            Cookies.removeCookie("JSESSIONID");
            Window.Location.replace(GWT.getHostPageBaseURL() + "login");
        });
    }

    public void setData(SimpleUser simpleUser) {
        display.setDataLabel(simpleUser.getName(), simpleUser.getRoles().toString());
    }
}
