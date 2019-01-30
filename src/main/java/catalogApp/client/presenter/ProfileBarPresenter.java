package catalogApp.client.presenter;

import catalogApp.client.event.ShowProfileEvent;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ProfileBarPresenter implements Presenter {
    public interface Display {
        void setDataLabel(String username, String role);
        Button getLogoutButton();
        //Button getProfileButton();
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
            Window.Location.replace("/login");

        });

        //display.getProfileButton().addClickHandler(event -> eventBus.fireEvent(new ShowProfileEvent()));
    }

    public void setData(SimpleUser simpleUser) {
        display.setDataLabel(simpleUser.getUsername(), simpleUser.getRole());
    }
}
