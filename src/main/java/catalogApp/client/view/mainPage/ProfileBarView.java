package catalogApp.client.view.mainPage;

import catalogApp.client.presenter.ProfileBarPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class ProfileBarView extends Composite implements ProfileBarPresenter.Display {

    interface ProfileBarViewUiBinder extends UiBinder<HTMLPanel, ProfileBarView> {
    }

    public ProfileBarView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        logoutButton.getElement().getStyle().setMargin(5, Style.Unit.PX);
        logoutButton.getElement().getStyle().setMarginLeft(20, Style.Unit.PX);
        dataLabel.getElement().getStyle().setFontSize(15, Style.Unit.PX);
    }

    private static ProfileBarViewUiBinder ourUiBinder = GWT.create(ProfileBarViewUiBinder.class);

    @UiField
    HorizontalPanel hPanel;
    @UiField
    Label dataLabel;
    @UiField
    Button logoutButton;

    @Override
    public void setDataLabel(String username, String role) {
        dataLabel.setText("Username: " + username + "     Your role: " + role);
    }

    @Override
    public Button getLogoutButton() {
        return logoutButton;
    }

}