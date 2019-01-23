package catalogApp.client.view.mainPage;

import catalogApp.client.presenter.UserPanelPresenter;
import catalogApp.client.view.components.AbstractCatalogCellTable;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.view.client.ListDataProvider;

public class UserPanelView extends Composite implements UserPanelPresenter.Display {
    interface UserPanelViewUiBinder extends UiBinder<HTMLPanel, UserPanelView> {

    }
    @UiField
    AbstractCatalogCellTable<User> userTable;

    private static UserPanelViewUiBinder ourUiBinder = GWT.create(UserPanelViewUiBinder.class);

    public UserPanelView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    private void initializeTable(){
        TextColumn<User> passColumn = new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getPassword();
            }
        };

        TextColumn<User> roleColumn = new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getRole();
            }
        };

        userTable.getElement().getStyle().setBorderStyle(Style.BorderStyle.SOLID);
        userTable.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);


        userTable.addColumn(passColumn, "Password");
        userTable.addColumn(roleColumn, "Role");
    }

    @Override
    public void setDataProvider(ListDataProvider<User> dataProvider) {
        dataProvider.addDataDisplay(userTable);
        initializeTable();
    }


}