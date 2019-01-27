package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.ProfilePopupPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class ProfilePopupView extends Composite implements ProfilePopupPresenter.Display {
    interface ProfilePopupViewUiBinder extends UiBinder<HTMLPanel, ProfilePopupView> {

    }
    @UiField
    DialogBox dialogBox;
    @UiField
    Label titleLabel;
    @UiField
    TextBox avatarUrl;
    @UiField
    FlexTable table;
    @UiField
    Image avatarImg;
    @UiField
    Button closeButton;
    @UiField
    Button submitButton;

    private TextBox usernameBox = new TextBox();
    private TextBox roleBox = new TextBox();
    private TextArea describeBox = new TextArea();

    private static ProfilePopupViewUiBinder ourUiBinder = GWT.create(ProfilePopupViewUiBinder.class);

    public ProfilePopupView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        usernameBox.setWidth("200px");
        table.setWidget(0,0 , new Label("Username:"));
        table.setWidget(0,1 , usernameBox);
        roleBox.setEnabled(false);
        roleBox.setWidth("200px");
        table.setWidget(1,0, new Label("Role:"));
        table.setWidget(1,1, roleBox);
        describeBox.setSize("200px", "200px");
        describeBox.getElement().setAttribute("maxlength", "300");
        describeBox.getElement().setAttribute("placeholder", "Something about you");
        table.setWidget(2,0, new Label("Description:"));
        table.setWidget(2,1, describeBox);
        avatarUrl.setWidth("130px");
        avatarUrl.addFocusHandler(event -> avatarUrl.selectAll());
        avatarImg.setWidth("150px");
        //avatarImg.setHeight("100%");
        table.getElement().getStyle().setPaddingLeft(20, Style.Unit.PX);
    }

    @Override
    public FlexTable getFlexTable() {
        return table;
    }

    @Override
    public Button getSubmitButton() {
        return submitButton;
    }

    @Override
    public Button getCloseButton() {
        return closeButton;
    }

    @Override
    public void hideDialog() {
        dialogBox.hide();
    }

    @Override
    public void showDialog() {
        dialogBox.center();
    }

    @Override
    public String getDescription() {
        return describeBox.getText();
    }

    @Override
    public void setDescription(String text) {
        describeBox.setText(text);
    }

    @Override
    public String getUsername() {
        return usernameBox.getText();
    }

    @Override
    public void setUsername(String username) {
        usernameBox.setText(username);
    }

    @Override
    public void setRole(String role) {
        roleBox.setText(role);
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl.getText();
    }

    @Override
    public void setAvatarUrl(String url) {
        avatarImg.setUrl(url);
        avatarUrl.setText(url);
    }
}