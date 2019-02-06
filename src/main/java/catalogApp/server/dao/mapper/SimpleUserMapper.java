package catalogApp.server.dao.mapper;

import catalogApp.shared.model.SimpleUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleUserMapper implements RowMapper<SimpleUser> {
    @Override
    public SimpleUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        SimpleUser simpleUser = new SimpleUser(rs.getInt("idObject"), rs.getString("objectName"));
        simpleUser.setDescription(rs.getString("description"));
        simpleUser.setAvatarUrl(rs.getString("avatar"));
        return simpleUser;
    }
}
