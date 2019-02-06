package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.services.UserWebService;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;


public class ProfilePresenter implements Presenter {
    public interface Display {
        Button getSubmitButton();

        Button getUploadButton();

        FormPanel getFormPanel();

        Widget asWidget();

        String getDescription();

        String getUsername();

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

        display.getSubmitButton().addClickHandler(event -> {

            SimpleUser updatedSimpleUser = new SimpleUser();
            updatedSimpleUser.setId(simpleUser.getId());

            String newDescription = display.getDescription().trim();
            String newUsername = display.getUsername().trim();

            if (!newUsername.isEmpty() && !newUsername.equals(simpleUser.getName())) {
                updatedSimpleUser.setName(newUsername);
            }
            if(!newDescription.equals(simpleUser.getDescription())){
                updatedSimpleUser.setDescription(newDescription);
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
                    display.updateData(simpleUser);
                }
            });

        });

    }

    private void initUploader(){
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName("image");

        FormPanel formPanel = display.getFormPanel();
        formPanel.add(fileUpload);
        formPanel.setMethod(FormPanel.METHOD_POST);
        formPanel.setAction(GWT.getModuleBaseURL() + "rest/avatar");
        formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);

        display.getUploadButton().addClickHandler(event -> {
            String filename = fileUpload.getFilename();
            if (filename.length() == 0) {
                Window.alert("No File Specified!");
            } else {
                formPanel.submit();
            }
        });

        formPanel.addSubmitCompleteHandler(event -> userWebService.getAvatarUrl(simpleUser.getId(), new MethodCallback<String>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("getAvatarUrl doesnt work", exception);
            }

            @Override
            public void onSuccess(Method method, String response) {
                GWT.log(GWT.getModuleBaseURL() + response);
                simpleUser.setAvatarUrl(GWT.getModuleBaseURL() + response);
                display.updateData(simpleUser);
            }
        }));
    }
}
