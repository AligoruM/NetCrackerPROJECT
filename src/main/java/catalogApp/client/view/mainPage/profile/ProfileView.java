package catalogApp.client.view.mainPage.profile;

import catalogApp.client.presenter.ProfilePresenter;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import static catalogApp.client.view.constants.ProfileConstants.*;

public class ProfileView extends Composite implements ProfilePresenter.Display {
    interface ProfileViewUiBinder extends UiBinder<HTMLPanel, ProfileView> {

    }

    @UiField
    Label titleLabel;
    @UiField
    TextBox avatarUrl;
    @UiField
    FlexTable table;
    @UiField
    Image avatarImg;
    @UiField
    Button submitButton;

    private TextBox usernameBox = new TextBox();
    private TextBox roleBox = new TextBox();
    private TextArea describeBox = new TextArea();

    private static ProfileViewUiBinder ourUiBinder = GWT.create(ProfileViewUiBinder.class);

    public ProfileView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        usernameBox.setWidth(PROFILE_FIELDS_WIDTH);
        table.setWidget(0, 0, new Label(USER_LABEL));
        table.setWidget(0, 1, usernameBox);
        roleBox.setEnabled(false);
        roleBox.setWidth(PROFILE_FIELDS_WIDTH);
        table.setWidget(1, 0, new Label(ROLE_LABEL));
        table.setWidget(1, 1, roleBox);
        describeBox.setSize(PROFILE_FIELDS_WIDTH, PROFILE_FIELDS_HEIGHT);

        describeBox.getElement().setAttribute("maxlength", DESCRIPTION_LENGTH);
        describeBox.getElement().setAttribute("placeholder", DESCRIPTION_PLACEHOLDER);

        table.setWidget(2, 0, new Label(DESCRIPTION_LABEL));
        table.setWidget(2, 1, describeBox);
        avatarUrl.setWidth(PROFILE_AVATAR_SIZE);
        avatarUrl.addFocusHandler(event -> avatarUrl.selectAll());
        avatarImg.setWidth(PROFILE_AVATAR_SIZE);
        table.getElement().getStyle().setPaddingLeft(20, Style.Unit.PX);
    }


    @Override
    public Button getSubmitButton() {
        return submitButton;
    }

    @Override
    public String getDescription() {
        return describeBox.getText();
    }

    @Override
    public String getUsername() {
        return usernameBox.getText();
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl.getText();
    }

    @Override
    public void updateData(SimpleUser simpleUser) {
        setUsername(simpleUser.getName());
        setRole(simpleUser.getRoles().toString());
        setDescription(simpleUser.getDescription());
        setAvatarUrl(simpleUser.getAvatarUrl());
    }

    private void setDescription(String text) {
        if(text!=null)
            describeBox.setText(text);
        else
            describeBox.setText("");
    }

    private void setUsername(String username) {
        if(username!=null &&!username.isEmpty())
            usernameBox.setText(username);
        else
            usernameBox.setText(NA);
    }

    private void setRole(String role) {
        if(role!=null &&!role.isEmpty())
            roleBox.setText(role);
        else
            roleBox.setText(NA);
    }

    private void setAvatarUrl(String url) {
        if(url!=null && !url.isEmpty()) {
            avatarImg.setUrl(url);
            avatarUrl.setText(url);
        } else {
            avatarImg.setUrl(DEFAULT_AVATAR_URL);
            avatarUrl.setText(DEFAULT_AVATAR_URL);
        }
    }
}