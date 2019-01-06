package catalogApp.client.view;

import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Type;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.Arrays;
import java.util.List;

public class AddDialog extends Composite {
    interface AddDialogUiBinder extends UiBinder<HTMLPanel, AddDialog> {
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

    public AddDialog() {
        MultiWordSuggestOracle wordSuggest = new MultiWordSuggestOracle();
        MainScreenView.getTestService().getAllAuthor(new MethodCallback<List<String>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("getAllAuthorsNames doesnt work");
            }

            @Override
            public void onSuccess(Method method, List<String> authors) {
                wordSuggest.addAll(authors);
            }
        });
        authorBox = new SuggestBox(wordSuggest);
        initWidget(ourUiBinder.createAndBindUi(this));
        dialogPanel.setPopupPosition(100, 100);

        authorBox.setLimit(6);
        //authorBox.setAnimationEnabled(true);

        dialogPanel.show();
    }

    @UiHandler("cancelButton")
    void doClickCancel(ClickEvent click) {
        dialogPanel.hide();
    }

    @UiHandler("submitButton")
    void doClickSubmit(ClickEvent click) {
        String name = nameBox.getText().trim();
        String author = authorBox.getText().trim();
        MainScreenView.getTestService().addBook(Arrays.asList(name, author), new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Adding doesnt work");
            }

            @Override
            public void onSuccess(Method method, Void aVoid) {
                Window.alert("Added!");
                dialogPanel.hide();
            }
        });
    }
}