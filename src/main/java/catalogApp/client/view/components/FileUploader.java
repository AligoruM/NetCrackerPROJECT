package catalogApp.client.view.components;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;

public class FileUploader extends FormPanel{

    private FileUpload fileUpload = new FileUpload();

    public FileUploader() {
        add(fileUpload);
    }

    public void setFileFieldName(String fieldName){
        fileUpload.setName(fieldName);
    }

    public void setAction(String actionUrl){
        super.setAction(actionUrl);
        setMethod(FormPanel.METHOD_POST);
        setEncoding(FormPanel.ENCODING_MULTIPART);
    }

    public FileUpload getFileUpload() {
        return fileUpload;
    }
}
