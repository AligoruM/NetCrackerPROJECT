package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.AddBookDialogPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.Arrays;
import java.util.List;

public class AddBookDialog extends Composite implements AddBookDialogPresenter.Display {

    interface AddDialogUiBinder extends UiBinder<HTMLPanel, AddBookDialog> {
    }

    @UiField
    Button submitButton;
    @UiField
    Button cancelButton;
    @UiField
    TextBox nameBox;
    @UiField(provided = true)
    SuggestBox authorBox;
    @UiField
    DialogBox dialogPanel;

    private static AddDialogUiBinder ourUiBinder = GWT.create(AddDialogUiBinder.class);
    private MultiWordSuggestOracle wordSuggest = new MultiWordSuggestOracle();

    public AddBookDialog() {
        authorBox = new SuggestBox(wordSuggest);
        initWidget(ourUiBinder.createAndBindUi(this));
        dialogPanel.setPopupPosition(100, 100);
        authorBox.setLimit(6);
    }


    @Override
    public void setSuggestions(List<String> suggestions) {
        wordSuggest.addAll(suggestions);
        authorBox.refreshSuggestionList();
    }

    @Override
    public HasClickHandlers getSubmitButton() {
        return submitButton;
    }

    @Override
    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    @Override
    public void showDialog() {
        dialogPanel.show();
    }

    @Override
    public void hideDialog() {
        dialogPanel.hide();
    }

    @Override
    public List<String> getAddInfo() {
        return Arrays.asList(nameBox.getText(), authorBox.getText());
    }
}