package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.AddUserDialogPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class AddUserDialogView extends Composite implements AddUserDialogPresenter.Display {

    interface AddUserDialogViewUiBinder extends UiBinder<HTMLPanel, AddUserDialogView> {

    }

    private static AddUserDialogViewUiBinder ourUiBinder = GWT.create(AddUserDialogViewUiBinder.class);

    @UiField
    DialogBox dialogPanel;
    @UiField
    ListBox roleBox;
    @UiField
    TextBox passBox;
    @UiField
    TextBox nameBox;
    @UiField
    Button submitButton;
    @UiField
    Button cancelButton;

    public AddUserDialogView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void showDialog() {
        dialogPanel.center();
    }

    @Override
    public void hideDialog() {
        dialogPanel.hide();
    }

    @Override
    public void clearFields() {
        passBox.setText("");
        nameBox.setText("");
    }

    @Override
    public String getName() {
        return nameBox.getText().trim();
    }

    @Override
    public String getPass() {
        return passBox.getText().trim();
    }

    @Override
    public String getRole() {
        return roleBox.getSelectedValue();
    }

    @Override
    public void addRoles(List<String> roles) {
        if(roles!=null){
            roles.forEach(e->roleBox.addItem(e));
        }
    }

    @Override
    public Button getCancelButton() {
        return cancelButton;
    }

    @Override
    public Button getSubmitButton() {
        return submitButton;
    }
}