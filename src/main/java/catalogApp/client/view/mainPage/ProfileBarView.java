package catalogApp.client.view.mainPage;

import catalogApp.client.presenter.ProfileBarPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ProfileBarView extends Composite implements ProfileBarPresenter.Display {

    interface ProfileBarViewUiBinder extends UiBinder<HTMLPanel, ProfileBarView> {
    }

    public ProfileBarView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private static ProfileBarViewUiBinder ourUiBinder = GWT.create(ProfileBarViewUiBinder.class);

    @UiField
    HorizontalPanel hPanel;
    @UiField
    Label dataLabel;

    @Override
    public void setDataLabel(String username, String role) {
        dataLabel.setText("Username: " + username + " Your role: " + role);
    }
}