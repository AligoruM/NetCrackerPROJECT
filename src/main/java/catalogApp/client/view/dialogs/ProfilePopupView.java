package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.ProfilePopupPresenter;
import com.google.gwt.core.client.GWT;
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
    Label nameLabel;
    @UiField
    FlexTable table;
    @UiField
    Image avatarImg;
    @UiField
    Button closeButton;
    private static ProfilePopupViewUiBinder ourUiBinder = GWT.create(ProfilePopupViewUiBinder.class);

    public ProfilePopupView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        avatarImg.setUrl("http://ctv.swsu.ru/wp-content/uploads/2017/03/avatar.jpeg");
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
}