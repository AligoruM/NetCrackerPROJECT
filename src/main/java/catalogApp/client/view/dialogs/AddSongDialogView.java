package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.AddSongDialogPresenter;
import catalogApp.client.view.components.tables.utils.DurationFormatter;
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
    @UiField
    ListBox genreBox;
    @UiField
    IntegerBox durationBox;
    @UiField
    DialogBox dialogPanel;

    private static AddSongDialogViewUiBinder ourUiBinder = GWT.create(AddSongDialogViewUiBinder.class);

    public AddSongDialogView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        durationBox.setMaxLength(4);
        durationBox.addKeyPressHandler(DurationFormatter.durationBoxKeyPressHandler());
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
        return Arrays.asList(nameBox.getText(), genreBox.getSelectedValue(), durationBox.getText());
    }

    @Override
    public void setSuggestions(List<String> suggestions) {
        suggestions.forEach(x-> genreBox.addItem(x));
    }

}