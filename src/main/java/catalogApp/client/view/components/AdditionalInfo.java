package catalogApp.client.view.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import static catalogApp.client.view.constants.LibraryConstants.DEFAULT_OBJECT_IMAGE;

public class AdditionalInfo extends Composite {
    interface AdditionalInfoUiBinder extends UiBinder<DisclosurePanel, AdditionalInfo> {
    }

    @UiField
    FileUploader fileUploader;
    @UiField
    Button uploadButton;
    @UiField
    TextArea commentArea;
    @UiField
    DisclosurePanel disclosurePanel;
    @UiField
    Image image;

    private static AdditionalInfoUiBinder ourUiBinder = GWT.create(AdditionalInfoUiBinder.class);

    public AdditionalInfo() {
        initWidget(ourUiBinder.createAndBindUi(this));
        image.setSize("100px", "100px");
    }

    public void setComment(String comment){
        if(comment!=null) {
            commentArea.setText(comment);
        }
    }

    public void setImage(String imagePath){
        if(imagePath!=null){
            image.setUrl(imagePath);
        }else {
            image.setUrl(DEFAULT_OBJECT_IMAGE);
        }
    }

    public Button getUploadButton(){
        return uploadButton;
    }

    public FileUploader getFileUploader() {
        return fileUploader;
    }

    public String getComment(){
        return commentArea.getText();
    }

    public Image getImage() {
        return image;
    }
}