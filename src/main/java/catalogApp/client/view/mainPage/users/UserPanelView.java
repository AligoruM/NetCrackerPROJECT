package catalogApp.client.view.mainPage.users;

import catalogApp.client.presenter.UserPanelPresenter;
import catalogApp.client.view.components.tables.AbstractCatalogCellTable;
import catalogApp.client.view.components.tables.UserCellTable;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

public class UserPanelView extends Composite implements UserPanelPresenter.Display {
    interface UserPanelViewUiBinder extends UiBinder<HTMLPanel, UserPanelView> {

    }
    @UiField
    SimplePanel simplePanel;

    private UserCellTable userTable;

    private static UserPanelViewUiBinder ourUiBinder = GWT.create(UserPanelViewUiBinder.class);

    public UserPanelView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setList(List<SimpleUser> list) {
        userTable = new UserCellTable(list);
        simplePanel.add(userTable);
    }

    @Override
    public AbstractCatalogCellTable<SimpleUser> getTable() {
        return userTable;
    }
}