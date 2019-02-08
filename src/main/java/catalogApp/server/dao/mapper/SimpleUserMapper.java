package catalogApp.server.dao.mapper;

import catalogApp.shared.model.SimpleUser;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

import static catalogApp.server.dao.constants.Tables.*;

public class SimpleUserMapper implements RowMapper<SimpleUser> {
    @Override
    public SimpleUser mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
        SimpleUser simpleUser = new SimpleUser(rs.getInt(ID_OBJ_ALIAS), rs.getString(NAME_OBJ_ALIAS));
        simpleUser.setDescription(rs.getString(DESCRIPTION_ALIAS));
        simpleUser.setArchived(rs.getBoolean(ARCHIVED_OBJ_ALIAS));
        simpleUser.setImagePath(rs.getString(IMG_OBJ_ALIAS));
        simpleUser.setComment(rs.getString(COMMENT_OBJ_ALIAS));
        return simpleUser;
    }
}
