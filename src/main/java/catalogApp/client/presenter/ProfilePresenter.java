package catalogApp.client.presenter;

import catalogApp.client.CatalogController;
import catalogApp.client.services.UserWebService;
import catalogApp.client.view.components.FileUploader;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import static catalogApp.shared.constants.FileServiceConstants.IMAGE_FIELD;
import static catalogApp.shared.constants.FileServiceConstants.IMAGE_SERVICE_PATH;


public class ProfilePresenter implements Presenter {
    public interface Display {
        Button getSubmitButton();

        Button getUploadButton();

        Button getRefreshButton();

        FileUploader getFileUploader();

        Widget asWidget();

        String getDescription();

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
            if (!newDescription.equals(simpleUser.getDescription())) {
                updatedSimpleUser.setDescription(newDescription);
            }

            if (updatedSimpleUser.getImagePath() != null) {
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

        display.getRefreshButton().addClickHandler(event -> {
            userWebService.getSimpleUser(new MethodCallback<SimpleUser>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    GWT.log("something goes wrong", exception);
                }

                @Override
                public void onSuccess(Method method, SimpleUser response) {
                    simpleUser.updateFields(response);
                    display.updateData(simpleUser);
                }
            });
        });
    }

    private void initUploader() {

        FileUploader fileUploader = display.getFileUploader();
        fileUploader.setAction(GWT.getModuleBaseURL() + IMAGE_SERVICE_PATH);
        fileUploader.setFileFieldName(IMAGE_FIELD);

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
