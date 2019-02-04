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
    @UiField(provided = true)
    SuggestBox authorBox;
    @UiField
    DialogBox dialogPanel;

    private static AddDialogViewUiBinder ourUiBinder = GWT.create(AddDialogViewUiBinder.class);
    private MultiWordSuggestOracle wordSuggest = new MultiWordSuggestOracle();

    public AddBookDialogView() {
        authorBox = new SuggestBox(wordSuggest);
        initWidget(ourUiBinder.createAndBindUi(this));
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
        dialogPanel.center();
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