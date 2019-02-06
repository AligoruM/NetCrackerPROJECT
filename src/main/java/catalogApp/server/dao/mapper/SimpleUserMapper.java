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
        simpleUser.setArchived(rs.getBoolean("archived"));
        simpleUser.setImagePath(rs.getString("image"));
        simpleUser.setComment(rs.getString("comment"));
        return simpleUser;
    }
}
