package catalogApp.server.dao;

import catalogApp.server.dao.constants.Attribute;
import catalogApp.server.dao.constants.SQLQuery;
import catalogApp.server.dao.mapper.UserMapper;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUser(String name) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(SQLQuery.USER_BY_NAME(name), new UserMapper());
    }

    public SimpleUser getSimpleUser(String name) {
        Integer id = jdbcTemplate.queryForObject(SQLQuery.USER_ID_BY_NAME(name), (rs, rowNum) -> rs.getInt("id"));
        if (id != null) {
            String role = jdbcTemplate.queryForObject(SQLQuery.USER_ROLE_BY_ID(id), (rs, rowNum) -> rs.getString("role"));
            return new SimpleUser(id, name, role);
        } else
            return null;
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQLQuery.ALL_USERS(), new UserMapper());
    }

    public void updateUserProfile(int id, Map<String, String> params) {
        if (params.containsKey("name"))
            jdbcTemplate.update(SQLQuery.UPDATE_OBJECT_NAME(id, params.get("name")));
        if (params.containsKey("description")) {
            String description = params.get("description");
            if (description.isEmpty()) {
                jdbcTemplate.execute(SQLQuery.DELETE_ATTRIBUTE_VALUE_BY_KEYS(id, Attribute.USER_DESCRIPTION));
                System.out.println("deleted description");
            } else {
                if (jdbcTemplate.update(SQLQuery.UPDATE_ATTRIBUTE_VALUE(description, id, Attribute.USER_DESCRIPTION)) == 0) {
                    jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(description, id, Attribute.USER_DESCRIPTION));
                    System.out.println("created description");
                } else {
                    System.out.println("updated description");
                }
            }
        }
        if (params.containsKey("avatarUrl")) {
            String avatarUrl = params.get("avatarUrl");
            if (jdbcTemplate.update(SQLQuery.UPDATE_ATTRIBUTE_VALUE(avatarUrl, id, Attribute.USER_AVATAR_URL)) == 0) {
                jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(avatarUrl, id, Attribute.USER_AVATAR_URL));
                System.out.println("created avatar");
            } else {
                System.out.println("updated avatar");
            }
        }
    }

    public Map<String, String> getUserProfile(int id) {
        HashMap<String, String> tmp = new HashMap<>();
        try {
            String result = jdbcTemplate.queryForObject(SQLQuery.ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(id, Attribute.USER_AVATAR_URL)
                    , (rs, rowNum) -> rs.getString("value"));
            tmp.put("avatarUrl", result);
        } catch (IncorrectResultSizeDataAccessException ignored) { }

        try{
            String result = jdbcTemplate.queryForObject(SQLQuery.ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(id, Attribute.USER_DESCRIPTION)
                    , (rs, rowNum) -> rs.getString("value"));
            tmp.put("description", result);
        } catch (IncorrectResultSizeDataAccessException ignored){ }

        return tmp;
    }
}
