package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.EditBookDialogPresenter;
import catalogApp.shared.model.BaseObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class EditDialogView extends Composite implements EditBookDialogPresenter.Display {
    interface EditDialogViewUiBinder extends UiBinder<HTMLPanel, EditDialogView> {

    }

    @UiField
    ListBox listBox;
    @UiField
    FlexTable flexTable;
    @UiField
    DialogBox dialogPanel;
    @UiField
    Button cancelButton;
    @UiField
    Button submitButton;

    TextBox idBox = new TextBox();

    TextBox nameBox = new TextBox();

    private static EditDialogViewUiBinder ourUiBinder = GWT.create(EditDialogViewUiBinder.class);

    public EditDialogView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        idBox.setEnabled(false);

        flexTable.setWidget(0, 0, new Label("ID"));
        flexTable.setWidget(0, 1, idBox);
        flexTable.setWidget(1, 0, new Label("Name"));
        flexTable.setWidget(1, 1, nameBox);
    }

    @Override
    public Button getCancelButton() {
        return cancelButton;
    }

    @Override
    public Button getSubmitButton() {
        return submitButton;
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
    public FlexTable getTable() {
        return flexTable;
    }

    @Override
    public void showData(BaseObject object) {
        idBox.setText(String.valueOf(object.getId()));
        nameBox.setText(object.getName());
    }

    @Override
    public void updateListBox(List<? extends BaseObject> list) {
        listBox.clear();
        for (BaseObject x:list) {
            listBox.addItem(x.getName(), String.valueOf(x.getId()));
        }
    }

    @Override
    public String getNewName() {
        return nameBox.getText();
    }


    @Override
    public ListBox getListBox() {
        return listBox;
    }

}