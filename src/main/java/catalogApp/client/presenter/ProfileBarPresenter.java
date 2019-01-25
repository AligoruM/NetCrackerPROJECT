package catalogApp.client.presenter;

import catalogApp.shared.model.SimpleUser;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProfileBarPresenter implements Presenter {
    public interface Display {
        void setDataLabel(String username, String role);
        Button getLogoutButton();
        Widget asWidget();
    }

    private Display display;

    public ProfileBarPresenter(Display display) {
        this.display = display;
    }

    @Override
    public void go(DockPanel container) {
        container.add(display.asWidget(), DockPanel.NORTH);
        display.getLogoutButton().addClickHandler(event -> {
            Cookies.removeCookie("JSESSIONID");
            Window.Location.replace("/login");

        });
    }

    public void setData(SimpleUser simpleUser) {
        display.setDataLabel(simpleUser.getUsername(), simpleUser.getRole());
    }
}
