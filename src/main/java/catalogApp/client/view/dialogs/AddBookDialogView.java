package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.AddBookDialogPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.Arrays;
import java.util.List;

public class AddBookDialogView extends Composite implements AddBookDialogPresenter.Display {

    interface AddDialogViewUiBinder extends UiBinder<HTMLPanel, AddBookDialogView> {
    }

    @UiField
    Button submitButton;
    @UiField
    Button cancelButton;
    @UiField
    TextBox nameBox;
    @UiField
    ListBox authorBox;
    @UiField
    DialogBox dialogPanel;
    @UiField
    Button plusButton;

    private static AddDialogViewUiBinder ourUiBinder = GWT.create(AddDialogViewUiBinder.class);

    public AddBookDialogView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }


    @Override
    public void setListBoxData(List<String> suggestions) {
        if(suggestions!=null) {
            authorBox.clear();
            suggestions.forEach(x -> authorBox.addItem(x));
        }
    }

    @Override
    public HasClickHandlers getSubmitButton() {
        return submitButton;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    public HasClickHandlers getPlusButton() {
        return plusButton;
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
    public List<String> getAddInfo() {
        return Arrays.asList(nameBox.getText(), authorBox.getSelectedValue());
    }

    @Override
    public ListBox getListBox() {
        return authorBox;
    }
}