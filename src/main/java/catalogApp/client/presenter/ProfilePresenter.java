package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.services.AuthWebService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;
import java.util.Map;


public class ProfilePresenter implements Presenter {
    public interface Display{
        Button getSubmitButton();
        Widget asWidget();
        String getDescription();
        void setDescription(String text);
        String getUsername();
        void setUsername(String username);
        void setRole(String role);
        String getAvatarUrl();
        void setAvatarUrl(String url);
    }

    private static final String DEFAULT_AVATAR_URL = "http://ctv.swsu.ru/wp-content/uploads/2017/03/avatar.jpeg";

    private String oldDescription;
    private String oldAvatarUrl;

    private Display display;
    private AuthWebService authWebService;
    private HandlerManager eventBus;

    public ProfilePresenter(Display display, AuthWebService authWebService, HandlerManager eventBus) {
        this.display = display;
        this.authWebService = authWebService;
        this.eventBus = eventBus;
        bind();
    }

    @Override
    public void go(Panel container) {
        container.clear();
        container.add(display.asWidget());
    }

    private void bind(){
        display.setUsername(CatalogController.getUser().getUsername());
        display.setRole(CatalogController.getUser().getRole());

        authWebService.getUserDescription(new MethodCallback<Map<String, String>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("UserDescription doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, Map<String, String> response) {
                display.setAvatarUrl(response.getOrDefault("avatarUrl", DEFAULT_AVATAR_URL));
                display.setDescription(response.getOrDefault("description", ""));

                oldDescription = response.getOrDefault("description", "");
                oldAvatarUrl = response.getOrDefault("avatarUrl", DEFAULT_AVATAR_URL);
            }
        });


        display.getSubmitButton().addClickHandler(event -> {
            HashMap<String, String> tmp = new HashMap<>();

            String newDescription = display.getDescription().trim();
            String newAvatarUrl = display.getAvatarUrl().trim();
            String newUsername = display.getUsername().trim();

            if(!(newUsername.equals(CatalogController.getUser().getUsername()) || newUsername.isEmpty())){
                tmp.put("name", newUsername);
            }
            if(!newDescription.equals(oldDescription)){
                tmp.put("description", newDescription);
            }
            if(!(newAvatarUrl.isEmpty() || newAvatarUrl.equals(oldAvatarUrl))){
                tmp.put("avatarUrl", newAvatarUrl);
            }
            if(!tmp.isEmpty()) {
                authWebService.setUserDescription(tmp, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("setUserDescription doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        if(tmp.containsKey("name")){
                            display.setUsername(newUsername);
                            CatalogController.getUser().setUsername(newUsername);
                        }
                        if(tmp.containsKey("avatarUrl")){
                            display.setAvatarUrl(newAvatarUrl);
                            oldAvatarUrl = newAvatarUrl;
                        }
                        if(tmp.containsKey("description")){
                            oldDescription = newDescription;
                        }
                    }
                });
            }
        });

    }
}
