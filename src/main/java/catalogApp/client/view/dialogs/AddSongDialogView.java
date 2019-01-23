package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.AddSongDialogPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.Arrays;
import java.util.List;

public class AddSongDialogView extends Composite implements AddSongDialogPresenter.Display {
    interface AddSongDialogViewUiBinder extends UiBinder<HTMLPanel, AddSongDialogView> {
    }

    @UiField
    Button submitButton;
    @UiField
    Button cancelButton;
    @UiField
    TextBox nameBox;
    @UiField(provided = true)
    SuggestBox genreBox;
    @UiField
    TextBox durationBox;
    @UiField
    DialogBox dialogPanel;

    private static AddSongDialogViewUiBinder ourUiBinder = GWT.create(AddSongDialogViewUiBinder.class);
    private MultiWordSuggestOracle wordSuggest = new MultiWordSuggestOracle();

    public AddSongDialogView() {
        genreBox = new SuggestBox(wordSuggest);
        initWidget(ourUiBinder.createAndBindUi(this));
        dialogPanel.setPopupPosition(100, 100);
        genreBox.setLimit(6);
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
        return Arrays.asList(nameBox.getText(), genreBox.getText(), durationBox.getText());
    }

    @Override
    public void setSuggestions(List<String> suggestions) {
        wordSuggest.addAll(suggestions);
        genreBox.refreshSuggestionList();
    }
}