package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.EditBookDialogPresenter;
import catalogApp.client.view.components.AdditionalInfo;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;

import static catalogApp.client.view.constants.LibraryConstants.*;

public class EditBookDialogView extends Composite implements EditBookDialogPresenter.Display {
    @UiTemplate("EditDialogView.ui.xml")
    interface EditBookDialogViewUiBinder extends UiBinder<HTMLPanel, EditBookDialogView> {

    }

    @UiField
    FlexTable flexTable;
    @UiField
    DialogBox dialogPanel;
    @UiField
    Button cancelButton;
    @UiField
    Button submitButton;
    @UiField
    AdditionalInfo additionalInfo;

    private TextBox idBox = new TextBox();

    private TextBox nameBox = new TextBox();

    private TextBox authorBox = new TextBox();


    private static EditBookDialogViewUiBinder ourUiBinder = GWT.create(EditBookDialogViewUiBinder.class);

    public EditBookDialogView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        idBox.setEnabled(false);

        flexTable.setWidget(0, 0, new Label(ID_COL_LABEL));
        flexTable.setWidget(0, 1, idBox);
        flexTable.setWidget(1, 0, new Label(NAME_COL_LABEL));
        flexTable.setWidget(1, 1, nameBox);

        authorBox.setEnabled(false);
        flexTable.setWidget(2, 0, new Label(AUTHOR_COL_LABEL));
        flexTable.setWidget(2, 1, authorBox);
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
    public void showData(Book object) {
        idBox.setText(String.valueOf(object.getId()));
        nameBox.setText(object.getName());
        authorBox.setText(object.getAuthor().getName());
        additionalInfo.setComment(object.getComment());
    }


    @Override
    public String getNewName() {
        return nameBox.getText().trim();
    }

    @Override
    public AdditionalInfo getAdditionalInfo(){
        return additionalInfo;
    }
}