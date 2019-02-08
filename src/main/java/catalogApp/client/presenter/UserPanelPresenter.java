package catalogApp.client.presenter;

import catalogApp.client.services.UserWebService;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class UserPanelPresenter implements Presenter {
    public interface Display{
        void setDataProvider(ListDataProvider<SimpleUser> dataProvider);
        Widget asWidget();
    }

    private ListDataProvider<SimpleUser> dataProvider = new ListDataProvider<>();

    private Display display;

    public UserPanelPresenter(Display display, UserWebService userWebService) {
        this.display = display;
        userWebService.getAllUsers(new MethodCallback<List<SimpleUser>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getAllUsers doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, List<SimpleUser> response) {
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
