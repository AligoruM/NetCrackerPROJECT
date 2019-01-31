package catalogApp.client.view.mainPage;

import catalogApp.client.presenter.UserPanelPresenter;
import catalogApp.client.view.components.UserCellTable;
import catalogApp.shared.model.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;

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

    private void initializeTable(){

    }

    @Override
    public void setDataProvider(ListDataProvider<User> dataProvider) {
        userTable=new UserCellTable(dataProvider);
        initializeTable();
        simplePanel.add(userTable);
    }


}