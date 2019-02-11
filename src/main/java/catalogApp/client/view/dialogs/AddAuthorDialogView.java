package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.AddAuthorDialogPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class AddAuthorDialogView extends Composite implements AddAuthorDialogPresenter.Display {
    interface AddAuthorDialogViewUiBinder extends UiBinder<HTMLPanel, AddAuthorDialogView> {

    }
    private static AddAuthorDialogViewUiBinder ourUiBinder = GWT.create(AddAuthorDialogViewUiBinder.class);

    @UiField
    DialogBox dialogBox;
    @UiField
    TextBox nameBox;
    @UiField
    Button submitButton;
    @UiField
    Button cancelButton;
    public AddAuthorDialogView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void showDialog() {
        dialogBox.center();
    }

    @Override
    public void hideDialog() {
        dialogBox.hide();
    }

    @Override
    public Button getSubmitButton() {
        return submitButton;
    }

    @Override
    public Button getCancelButton() {
        return cancelButton;
    }

    @Override
    public String getAuthorName() {
        return nameBox.getText().trim();
    }
}