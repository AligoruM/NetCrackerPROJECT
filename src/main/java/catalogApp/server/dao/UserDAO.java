package catalogApp.server.dao;

import catalogApp.server.dao.constants.Attribute;
import catalogApp.server.dao.constants.SQLQuery;
import catalogApp.server.dao.mapper.SimpleUserMapper;
import catalogApp.server.dao.mapper.UserMapper;
import catalogApp.server.security.User;
import catalogApp.shared.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        SimpleUser simpleUser = new SimpleUser();
        try {
            Integer id = jdbcTemplate.queryForObject(SQLQuery.USER_ID_BY_NAME(name), (rs, rowNum) -> rs.getInt("id"));
            simpleUser.setId(id);
            simpleUser.setName(name);
            simpleUser.setRoles(getUserRoles(id));
            setAdditionDataInSimpleUser(simpleUser);
            return simpleUser;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

    public Integer getUserIdByName(String name){
        return jdbcTemplate.queryForObject(SQLQuery.USER_ID_BY_NAME(name), (rs, rowNum) -> rs.getInt("id"));
    }

    public List<SimpleUser> getAllUsers() {
        List<SimpleUser> users = jdbcTemplate.query(SQLQuery.ALL_USERS(), new SimpleUserMapper());
        users.forEach(user -> user.setRoles(getUserRoles(user.getId())));
        return users;
    }

    public Set<String> getUserRoles(int id){
        return new HashSet<>(jdbcTemplate.query(SQLQuery.USER_ROLES_BY_ID(id), (rs, rowNum) -> rs.getString("role")));
    }

    public void updateUserAttributes(SimpleUser newSimpleUser, SimpleUser oldSimpleUser) {
        if (newSimpleUser.getId() == oldSimpleUser.getId()) {
            int id = oldSimpleUser.getId();
            if (newSimpleUser.getName()!=null && !newSimpleUser.getName().equals(oldSimpleUser.getName())) {
                String username = newSimpleUser.getName();
                jdbcTemplate.update(SQLQuery.UPDATE_OBJECT_NAME(id, username));
                //TODO something with it
                Collection nowAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
                Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, credentials, nowAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            if (newSimpleUser.getDescription()!=null && !newSimpleUser.getDescription().equals(oldSimpleUser.getDescription())) {
                String description = newSimpleUser.getDescription();
                updateUserAttr(description, id, Attribute.USER_DESCRIPTION);
            }
        }
    }

    public void updateAvatar(int id, String filepath){
        updateUserAttr(filepath, id, Attribute.USER_AVATAR_URL);
    }

    public String getUserAvatarPath(int id){
        return jdbcTemplate.queryForObject(SQLQuery.ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(id, Attribute.USER_AVATAR_URL), ResultSet::getString);
    }

    private void updateUserAttr(String value, int userId, int attributeId) {
        if (value.isEmpty()) {
            jdbcTemplate.execute(SQLQuery.DELETE_ATTRIBUTE_VALUE_BY_KEYS(userId, attributeId));
            System.out.println("deleted attribute " + attributeId);
        } else {
            if (jdbcTemplate.update(SQLQuery.UPDATE_ATTRIBUTE_VALUE(value, userId, attributeId)) == 0) {
                jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(value, userId, attributeId));
                System.out.println("created attribute " + attributeId);
            } else {
                System.out.println("updated attribute " + attributeId);
            }
        }
    }

    private void setAdditionDataInSimpleUser(SimpleUser simpleUser) {
        int id = simpleUser.getId();
        try {
            String result = jdbcTemplate.queryForObject(SQLQuery.ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(id, Attribute.USER_AVATAR_URL)
                    , (rs, rowNum) -> rs.getString("value"));
            simpleUser.setAvatarUrl(result);
        } catch (IncorrectResultSizeDataAccessException ignored) {
        }

        try {
            String result = jdbcTemplate.queryForObject(SQLQuery.ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(id, Attribute.USER_DESCRIPTION)
                    , (rs, rowNum) -> rs.getString("value"));
            simpleUser.setDescription(result);
        } catch (IncorrectResultSizeDataAccessException ignored) {
        }
    }
}
