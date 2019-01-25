package catalogApp.client.presenter;

import catalogApp.client.services.BookWebService;
import catalogApp.shared.model.BaseObject;
import catalogApp.shared.model.Book;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;
import java.util.List;

public class EditBookDialogPresenter implements Presenter {

    public interface Display {
        Button getCancelButton();

        Button getSubmitButton();

        void showDialog();

        void hideDialog();

        FlexTable getTable();

        Widget asWidget();

        void showData(BaseObject object);

        void updateListBox(List<? extends BaseObject> list);

        String getNewName();

        ListBox getListBox();
    }

    private ListDataProvider<Book> dataProvider;

    private Display display;

    private BookWebService bookWebService;

    private TextBox authorBox = new TextBox();

    private String oldName;

    public EditBookDialogPresenter(Display display, BookWebService bookWebService, ListDataProvider<Book> dataProvider) {
        this.dataProvider = dataProvider;
        this.display = display;
        this.bookWebService = bookWebService;
    }

    @Override
    public void go(DockPanel container) {
        RootPanel.get().add(display.asWidget());
        authorBox.setEnabled(false);
        display.getTable().setWidget(2, 0, new Label("Author"));
        display.getTable().setWidget(2, 1, authorBox);

        bind();

        display.showDialog();
    }

    private void bind() {
        display.updateListBox(dataProvider.getList());

        if (!dataProvider.getList().isEmpty()) {
            display.showData(dataProvider.getList().get(0));
            authorBox.setText(dataProvider.getList().get(0).getAuthor().getName());
            oldName = dataProvider.getList().get(0).getName();
        }

        display.getListBox().addChangeHandler(event -> {
            int selected_id = Integer.parseInt(display.getListBox().getSelectedValue());
            for (Book x : dataProvider.getList()) {
                if (x.getId() == selected_id) {
                    oldName = x.getName();
                    display.showData(x);
                    authorBox.setText(x.getAuthor().getName());
                    break;
                }
            }
        });

        display.getSubmitButton().addClickHandler(event -> {
            String newName = display.getNewName().trim();
            if (!(oldName.equals(newName) || newName.isEmpty())) {
                HashMap<String, String> tmp = new HashMap<>();

                int selected_id = Integer.valueOf(display.getListBox().getSelectedValue());
                tmp.put("name", newName);

                bookWebService.updateBook(selected_id, tmp, new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        GWT.log("updateBook doesnt work", exception);
                    }

                    @Override
                    public void onSuccess(Method method, Void response) {
                        for (Book x : dataProvider.getList()) {
                            if (x.getId() == selected_id) {
                                x.setName(newName);
                                break;
                            }
                        }
                        display.updateListBox(dataProvider.getList());
                    }
                });
            }
        });

        display.getCancelButton().addClickHandler(event -> display.hideDialog());
    }
}
