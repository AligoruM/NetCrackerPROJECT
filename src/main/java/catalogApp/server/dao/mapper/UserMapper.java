package catalogApp.server.dao.mapper;

import catalogApp.server.security.User;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

import static catalogApp.server.dao.constants.Tables.*;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(@Nonnull ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(ID_OBJ_ALIAS));
        user.setName(rs.getString(NAME_OBJ_ALIAS));
        user.setPassword(rs.getString(PASSWORD_ALIAS));
        user.setActive(!rs.getBoolean(ARCHIVED_OBJ_ALIAS));
        return user;
    }
}
