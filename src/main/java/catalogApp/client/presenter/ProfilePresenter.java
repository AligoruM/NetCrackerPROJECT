package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.services.UserWebService;
import catalogApp.client.view.components.FileUploader;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.TextCallback;

import static catalogApp.shared.constants.FileServiceConstants.AVATAR_SERVICE_PATH;
import static catalogApp.shared.constants.FileServiceConstants.IMAGE_FIELD;


public class ProfilePresenter implements Presenter {
    public interface Display {
        Button getSubmitButton();

        Button getUploadButton();

        Button getRefreshButton();

        FileUploader getFileUploader();

        Widget asWidget();

        String getDescription();

        PasswordTextBox getPasswordBox();

        Button getChangePassButton();

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

        initUploader();

        display.getChangePassButton().addClickHandler(event -> {
            String str = display.getPasswordBox().getText();
            if(str.length()>=4)
            userWebService.changePassword(str, new MethodCallback<Boolean>() {
                @Override
                public void onFailure(Method method, Throwable exception) {

                }

                @Override
                public void onSuccess(Method method, Boolean response) {
                    if (response) {
                        Window.alert("changed");
                        Cookies.removeCookie("JSESSIONID");
                        Window.Location.replace(GWT.getHostPageBaseURL() + "login");
                    } else
                        Window.alert("Something went wrong");
                }
            });
            else
                GWT.log("In password should be at least 4 symbols");
        });


        display.getSubmitButton().addClickHandler(event -> {
            boolean isChanged = false;
            SimpleUser updatedSimpleUser = new SimpleUser();
            updatedSimpleUser.setId(simpleUser.getId());

            String newDescription = display.getDescription().trim();
            if (!newDescription.equals(simpleUser.getDescription() == null ? "" : simpleUser.getDescription())) {
                updatedSimpleUser.setDescription(newDescription);
                isChanged = true;
            }

            if (isChanged) {
                userWebService.updateUser(updatedSimpleUser, new MethodCallback<SimpleUser>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateUser doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, SimpleUser response) {
                        simpleUser.setName(response.getName());
                        simpleUser.setDescription(response.getDescription());
                        simpleUser.setImagePath(response.getImagePath());
                        display.updateData(simpleUser);
                    }
                });
            }

        });

        display.getRefreshButton().addClickHandler(event -> userWebService.getSimpleUser(new MethodCallback<SimpleUser>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("something goes wrong", exception);
            }

            @Override
            public void onSuccess(Method method, SimpleUser response) {
                simpleUser.updateFields(response);
                display.updateData(simpleUser);
            }
        }));
    }

    private void initUploader() {
        FileUploader fileUploader = display.getFileUploader();
        fileUploader.setAction(GWT.getModuleName() + '/' + AVATAR_SERVICE_PATH);
        fileUploader.setFileFieldName(IMAGE_FIELD);

        fileUploader.addSubmitCompleteHandler(event -> userWebService.getAvatarUrl(simpleUser.getId(), new TextCallback() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("get avatar doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, String response) {
                if (response != null) {
                    simpleUser.setImagePath(response);
                    display.updateData(simpleUser);
                }
            }
        }));

        display.getUploadButton().addClickHandler(event -> {
            String filename = fileUploader.getFileUpload().getFilename();
            if (filename.length() == 0) {
                Window.alert("No File Specified!");
            } else {
                fileUploader.submit();
            }
        });
    }
}
