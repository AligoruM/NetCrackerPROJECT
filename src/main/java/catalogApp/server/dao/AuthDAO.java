package catalogApp.server.dao;

import catalogApp.server.dao.constants.SQLQuery;
import catalogApp.server.dao.mapper.UserMapper;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class AuthDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUser(String name) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(SQLQuery.USER_BY_NAME(name), new UserMapper());
    }

    public SimpleUser getSimpleUser(String name){
        int id = jdbcTemplate.queryForObject(SQLQuery.USER_ID_BY_NAME(name), (rs, rowNum) -> rs.getInt("id"));
        String role = jdbcTemplate.queryForObject(SQLQuery.USER_ROLE_BY_ID(id), (rs, rowNum) -> rs.getString("role"));
        return new SimpleUser(name, role);
    }
}
