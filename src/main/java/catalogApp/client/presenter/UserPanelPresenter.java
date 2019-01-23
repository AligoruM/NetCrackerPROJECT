package catalogApp.client.presenter;

import catalogApp.client.services.AuthWebService;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class UserPanelPresenter implements Presenter {
    public interface Display{
        void setDataProvider(ListDataProvider<User> dataProvider);
        Widget asWidget();
    }

    ListDataProvider<User> dataProvider = new ListDataProvider<>();

    private HandlerManager eventBus;
    private AuthWebService authWebService;
    private Display display;

    public UserPanelPresenter(Display display, AuthWebService authWebService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.authWebService = authWebService;
        this.display = display;
    }

    @Override
    public void go(DockPanel container) {
        container.add(display.asWidget(), DockPanel.EAST);
        authWebService.getAllUsers(new MethodCallback<List<User>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getAllUsers doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, List<User> response) {
                dataProvider.getList().addAll(response);
                GWT.log(response.toString());
                display.setDataProvider(dataProvider);
            }
        });
    }
}
