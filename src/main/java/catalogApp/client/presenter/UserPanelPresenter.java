package catalogApp.client.presenter;

import catalogApp.client.services.AuthWebService;
import catalogApp.shared.model.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Panel;
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

    private ListDataProvider<User> dataProvider = new ListDataProvider<>();

    private AuthWebService authWebService;
    private Display display;

    public UserPanelPresenter(Display display, AuthWebService authWebService) {
        this.authWebService = authWebService;
        this.display = display;
        authWebService.getAllUsers(new MethodCallback<List<User>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getAllUsers doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, List<User> response) {
                dataProvider.getList().addAll(response);
                display.setDataProvider(dataProvider);
            }
        });
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(display.asWidget());

    }
}
