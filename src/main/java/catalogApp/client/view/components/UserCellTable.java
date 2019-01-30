package catalogApp.client.view.components;

import catalogApp.shared.model.User;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;

public class UserCellTable extends AbstractCatalogCellTable {
    public UserCellTable(ListDataProvider dataProvider) {
        super(dataProvider);
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

        //getElement().getStyle().setBorderStyle(com.google.gwt.dom.client.Style.BorderStyle.SOLID);
        //getElement().getStyle().setBorderWidth(1, com.google.gwt.dom.client.Style.Unit.PX);


        addColumn(passColumn, "Password");
        addColumn(roleColumn, "Role");
    }
}
