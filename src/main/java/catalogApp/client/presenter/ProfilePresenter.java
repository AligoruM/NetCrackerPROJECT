package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.services.UserWebService;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;


public class ProfilePresenter implements Presenter {
    public interface Display {
        Button getSubmitButton();

        Widget asWidget();

        String getDescription();

        String getUsername();

        String getAvatarUrl();

        void updateData(SimpleUser simpleUser);
    }

    private SimpleUser simpleUser;

    private Display display;
    private UserWebService userWebService;

    public ProfilePresenter(Display display, UserWebService userWebService) {
        this.display = display;
        this.userWebService = userWebService;
        this.simpleUser = CatalogController.getUser();
        bind();
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(display.asWidget());
    }

    private void bind() {
        display.updateData(simpleUser);

        display.getSubmitButton().addClickHandler(event -> {

            SimpleUser updatedSimpleUser = new SimpleUser();
            updatedSimpleUser.setId(simpleUser.getId());

            String newDescription = display.getDescription().trim();
            String newAvatarUrl = display.getAvatarUrl().trim();
            String newUsername = display.getUsername().trim();

            if (!newUsername.isEmpty() && !newUsername.equals(simpleUser.getName())) {
                updatedSimpleUser.setName(newUsername);
            }
            if(!newDescription.equals(simpleUser.getDescription())){
                updatedSimpleUser.setDescription(newDescription);
            }

            if(!newAvatarUrl.equals(simpleUser.getAvatarUrl())){
                updatedSimpleUser.setAvatarUrl(newAvatarUrl);
            }


            userWebService.updateUser(updatedSimpleUser, new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("updateUser doesnt work", exception);
                }

                @Override
                public void onSuccess(Method method, Void response) {
                    simpleUser.setName(updatedSimpleUser.getName());
                    simpleUser.setDescription(updatedSimpleUser.getDescription());
                    simpleUser.setAvatarUrl(updatedSimpleUser.getAvatarUrl());
                    display.updateData(simpleUser);
                }
            });

        });

    }
}
