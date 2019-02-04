package catalogApp.server.dao.mapper;

import catalogApp.server.security.User;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(@Nonnull ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("idObject"));
        user.setName(rs.getString("objectName"));
        user.setPassword(rs.getString("password"));
        user.setActive(rs.getBoolean("active"));
        return user;
    }
}
