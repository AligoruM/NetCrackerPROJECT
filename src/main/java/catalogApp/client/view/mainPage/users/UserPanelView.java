package catalogApp.client.view.mainPage.users;

import catalogApp.client.presenter.UserPanelPresenter;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.components.tables.UserCellTable;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class UserPanelView extends Composite implements UserPanelPresenter.Display {
    interface UserPanelViewUiBinder extends UiBinder<HTMLPanel, UserPanelView> {

    }
    @UiField
    SimplePanel simplePanel;
    @UiField
    HorizontalPanel hPanel;
    @UiField
    Button addButton;
    @UiField
    Button banButton;
    @UiField
    Button enableButton;

    private UserCellTable userTable;

    private static UserPanelViewUiBinder ourUiBinder = GWT.create(UserPanelViewUiBinder.class);

    public UserPanelView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        hPanel.getElement().getStyle().setPaddingTop(10, Style.Unit.PX);
        addButton.getElement().getStyle().setPaddingRight(10, Style.Unit.PX);
        banButton.getElement().getStyle().setPaddingRight(10, Style.Unit.PX);
    }

    @Override
    public void setList(List<SimpleUser> list) {
        userTable = new UserCellTable(list);
        simplePanel.add(userTable);
        userTable.enablePopup();
    }

    @Override
    public AbstractCatalogCellTable<SimpleUser> getTable() {
        return userTable;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getBanButton() {
        return banButton;
    }

    public Button getEnableButton() {
        return enableButton;
    }
}