package catalogApp.client.presenter.helper;

import catalogApp.client.view.components.FileUploader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;

import static catalogApp.shared.constants.FileServiceConstants.IMAGE_FIELD;
import static catalogApp.shared.constants.FileServiceConstants.IMAGE_SERVICE_PATH;

public class EditorInitializeHelper {
    public static void initFileUploader(Button uploadButton, FileUploader fileUploader){
        fileUploader.setAction(GWT.getModuleBaseURL() + IMAGE_SERVICE_PATH);
        fileUploader.setFileFieldName(IMAGE_FIELD);
        uploadButton.addClickHandler(event -> {
            String filename = fileUploader.getFileUpload().getFilename();
            if (filename.isEmpty()) {
                Window.alert("No File Specified!");
            } else {
                fileUploader.submit();
            }
        });
    }
}
