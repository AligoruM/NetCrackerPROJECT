package catalogApp.client.view.components;

import catalogApp.client.view.components.utils.UserCellTableColumns;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Comparator;

import static catalogApp.client.view.constants.UserConstants.AVATAR_LABEL;
import static catalogApp.client.view.constants.UserConstants.ROLES_LABEL;

public class UserCellTable extends AbstractCatalogCellTable <SimpleUser>{
    public UserCellTable(ListDataProvider<SimpleUser> dataProvider) {
        super(dataProvider);

        Column<SimpleUser, String> roleColumn = UserCellTableColumns.getUserRoleColumn(true);
        Column<SimpleUser, String> avatarUrlColumn = UserCellTableColumns.getUserAvatarUrlColumn(false);

        addColumn(roleColumn, ROLES_LABEL);
        addSorter(roleColumn, Comparator.comparing(item->item.getRoles().toString()));
        addColumn(avatarUrlColumn, AVATAR_LABEL);
    }
}
