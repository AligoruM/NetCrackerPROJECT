package catalogApp.client.view.dialogs;

import catalogApp.client.presenter.EditSongDialogPresenter;
import catalogApp.client.view.components.AdditionalInfo;
import catalogApp.client.view.components.tables.utils.DurationFormatter;
import catalogApp.shared.model.Song;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;

import java.util.List;

import static catalogApp.client.view.constants.LibraryConstants.*;

public class EditSongDialogView extends Composite implements EditSongDialogPresenter.Display {
    @UiTemplate("EditDialogView.ui.xml")
    interface EditSongDialogViewUiBinder extends UiBinder<HTMLPanel, EditSongDialogView>{


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

    private ListBox genreBox = new ListBox();

    private IntegerBox durationBox = new IntegerBox();

    private TextBox idBox = new TextBox();

    private TextBox nameBox = new TextBox();

    private static EditSongDialogViewUiBinder ourUiBinder = GWT.create(EditSongDialogViewUiBinder.class);

    public EditSongDialogView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        idBox.setEnabled(false);

        flexTable.setWidget(0, 0, new Label(ID_COL_LABEL));
        flexTable.setWidget(0, 1, idBox);
        flexTable.setWidget(1, 0, new Label(NAME_COL_LABEL));
        flexTable.setWidget(1, 1, nameBox);

        flexTable.setWidget(2, 0, new Label(GENRE_COL_LABEL));
        flexTable.setWidget(2, 1, genreBox);
        flexTable.setWidget(3, 0, new Label(DURATION_COL_LABEL));
        flexTable.setWidget(3, 1, durationBox);
    }

    @Override
    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
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
    public void showData(Song object, List<String> genres) {
        idBox.setText(String.valueOf(object.getId()));
        nameBox.setText(object.getName());
        genres.forEach(x-> genreBox.addItem(x));
        durationBox.setText(String.valueOf(object.getDuration()));
        additionalInfo.setComment(object.getComment());
        additionalInfo.setImage(object.getImagePath());
        durationBox.setMaxLength(4);
        durationBox.addKeyPressHandler(DurationFormatter.durationBoxKeyPressHandler());
    }

    @Override
    public String getNewName() {
        return nameBox.getText().trim();
    }

    @Override
    public String getNewGenre() {
        return genreBox.getSelectedItemText();
    }

    @Override
    public int getNewDuration() {
        return Integer.parseInt(durationBox.getText());
    }
}
