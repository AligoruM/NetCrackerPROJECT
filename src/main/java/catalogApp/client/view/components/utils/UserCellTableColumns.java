package catalogApp.client.view.components.utils;

import catalogApp.shared.model.SimpleUser;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

public class UserCellTableColumns {
    public static Column<SimpleUser, String> getUserRoleColumn(boolean sortable) {
        return new TextColumn<SimpleUser>() {
            @Override
            public String getValue(SimpleUser object) {
                return object.getRoles().toString();
            }

            @Override
            public boolean isSortable() {
                return sortable;
            }
        };
    }

    public static Column<SimpleUser, String> getUserAvatarUrlColumn(boolean sortable) {
        return new TextColumn<SimpleUser>() {
            @Override
            public String getValue(SimpleUser object) {
                return object.getImagePath();
            }

            @Override
            public boolean isSortable() {
                return sortable;
            }
        };

    }
}
