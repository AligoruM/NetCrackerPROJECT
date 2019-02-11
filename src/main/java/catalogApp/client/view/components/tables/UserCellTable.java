package catalogApp.client.view.components.tables;

import catalogApp.client.view.components.tables.utils.UserCellTableColumns;
import catalogApp.shared.model.SimpleUser;
import com.google.gwt.user.cellview.client.Column;

import java.util.Comparator;
import java.util.List;

import static catalogApp.client.view.constants.UserConstants.AVATAR_LABEL;
import static catalogApp.client.view.constants.UserConstants.ROLES_LABEL;

public class UserCellTable extends AbstractCatalogCellTable <SimpleUser>{
    public UserCellTable(List<SimpleUser> list) {
        super(list);

        Column<SimpleUser, String> roleColumn = UserCellTableColumns.getUserRoleColumn(true);
        Column<SimpleUser, String> avatarUrlColumn = UserCellTableColumns.getUserAvatarUrlColumn(false);

        addColumn(roleColumn, ROLES_LABEL);
        addSorter(roleColumn, Comparator.comparing(item->item.getRoles().toString()));
        addColumn(avatarUrlColumn, AVATAR_LABEL);
    }
}
