package catalogApp.client.presenter;

import catalogApp.client.services.UserWebService;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static catalogApp.shared.constants.UserConstants.*;

public class AddUserDialogPresenter implements Presenter {
    public interface Display {
        void showDialog();

        void hideDialog();

        void clearFields();

        String getName();

        String getPass();

        String getRole();

        void addRoles(List<String> roles);

        Button getCancelButton();

        Button getSubmitButton();
    }

    private Display display;
    private UserWebService userWebService;

    public AddUserDialogPresenter(Display display, UserWebService userWebService) {
        this.display = display;
        this.userWebService = userWebService;
        userWebService.getRoles(new MethodCallback<List<String>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                Window.alert("getRoles doesnt work");
            }

            @Override
            public void onSuccess(Method method, List<String> response) {
                display.addRoles(response);
            }
        });
        bind();
    }

    @Override
    public void go(Panel container) {
        display.clearFields();
        display.showDialog();
    }

    private void bind() {
        display.getSubmitButton().addClickHandler(event -> {
            Map<String, String> userMap = new HashMap<>();
            String name = display.getName();
            String pass = display.getPass();
            String role = display.getRole();
            if(checkValid(name, pass, role)) {
                userMap.put(USERNAME_FIELD, name);
                userMap.put(PASSWORD_FIELD, pass);
                userMap.put(ROLE_FIELD, role);
                userWebService.createUser(userMap, new MethodCallback<SimpleUser>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        Window.alert("AddUser doesnt work");
                    }

                    @Override
                    public void onSuccess(Method method, SimpleUser response) {
                        if (response != null) {
                            if (UserPanelPresenter.getDataProvider() != null) {
                                UserPanelPresenter.getDataProvider().getList().add(response);
                                display.hideDialog();
                            }
                        } else {
                            Window.alert("You cannot create user like that");
                        }
                    }
                });
            }
        });
        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }

    private boolean checkValid(String name, String pass, String role){
        if(!name.isEmpty() && !pass.isEmpty() && !role.isEmpty()) {
            return true;
        }
        Window.alert("Fields cannot be empty");
        return false;
    }
}
