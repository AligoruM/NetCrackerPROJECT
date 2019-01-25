package catalogApp.server.dao.mapper;

import catalogApp.server.dao.constants.Types;
import catalogApp.shared.model.Type;
import catalogApp.shared.model.User;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    private static Type userType = new Type(Types.USER, "User");

    @Override
    public User mapRow(@Nonnull ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("idObject"));
        user.setName(rs.getString("objectName"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setActive(rs.getBoolean("active"));
        user.setType(userType);
        return user;
    }
}
