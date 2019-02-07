package catalogApp.client.view.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.TextArea;

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

    private static AdditionalInfoUiBinder ourUiBinder = GWT.create(AdditionalInfoUiBinder.class);

    public AdditionalInfo() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public void setComment(String comment){
        if(comment!=null)
            commentArea.setText(comment);
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
}