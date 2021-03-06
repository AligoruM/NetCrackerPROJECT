package catalogApp.server.dao;

import catalogApp.server.dao.constants.Attribute;
import catalogApp.server.dao.constants.SQLQuery;
import catalogApp.server.dao.mapper.SimpleUserMapper;
import catalogApp.server.dao.mapper.UserMapper;
import catalogApp.server.security.User;
import catalogApp.shared.model.SimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static catalogApp.server.dao.constants.Tables.IMG_OBJ;
import static catalogApp.server.dao.constants.Tables.ROLE_ALIAS;
import static catalogApp.shared.constants.FileServiceConstants.IMAGE_SERVICE_DIR;

public class UserDAO {

    private final Logger logger = LoggerFactory.getLogger(UserDAO.class);


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUser(String name) {
        try {
            return jdbcTemplate.queryForObject(SQLQuery.USER_BY_NAME(name), new UserMapper());
        } catch (EmptyResultDataAccessException ex) {
            logger.warn("Incorrect authorization. User: " + name + ". User not found");
            return null;
        }
    }

    public SimpleUser getSimpleUser(String name) {
        try {
            Integer id = jdbcTemplate.queryForObject(SQLQuery.USER_ID_BY_NAME(name), (rs, rowNum) -> rs.getInt("id"));
            if (id != null) {
                SimpleUser simpleUser = jdbcTemplate.queryForObject(SQLQuery.GET_SIMPLE_USER(id), new SimpleUserMapper());
                if (simpleUser != null) {
                    simpleUser.setRoles(getUserRoles(id));
                    setAdditionDataInSimpleUser(simpleUser);
                } else {
                    return null;
                }
                return simpleUser;
            } else {
                throw new IncorrectResultSizeDataAccessException(1);
            }
        } catch (IncorrectResultSizeDataAccessException ex) {
            logger.error("Founded more, than one or not found user with name " + name);
            return null;
        }
    }

    public Integer getUserIdByName(String name) {
        return jdbcTemplate.queryForObject(SQLQuery.USER_ID_BY_NAME(name), (rs, rowNum) -> rs.getInt("id"));
    }

    public List<SimpleUser> getAllUsers() {
        List<SimpleUser> users = jdbcTemplate.query(SQLQuery.ALL_SIMPLE_USERS(), new SimpleUserMapper());
        users.forEach(user -> user.setRoles(getUserRoles(user.getId())));
        return users;
    }

    public Set<String> getUserRoles(int id) {
        return new HashSet<>(jdbcTemplate.query(SQLQuery.USER_ROLES_BY_ID(id), (rs, rowNum) -> rs.getString(ROLE_ALIAS)));
    }

    public void updateUserAttributes(SimpleUser newSimpleUser, SimpleUser oldSimpleUser) {
        if (newSimpleUser.getId() == oldSimpleUser.getId()) {
            int id = oldSimpleUser.getId();
            if (newSimpleUser.getDescription() != null && !newSimpleUser.getDescription().equals(oldSimpleUser.getDescription())) {
                String description = newSimpleUser.getDescription();
                updateUserAttr(description, id, Attribute.USER_DESCRIPTION);
            }
            if (newSimpleUser.getImagePath() != null && !oldSimpleUser.getImagePath().contains(newSimpleUser.getImagePath())) {
                String filepath = IMAGE_SERVICE_DIR + "/" + newSimpleUser.getImagePath();
                updateAvatar(id, filepath);
            }
        }
    }

    public void updateAvatar(int id, String filepath) {
        jdbcTemplate.execute(SQLQuery.UPDATE_OBJECT_IMAGE(filepath, id));
    }

    private int updateUserAttr(String value, int userId, int attributeId) {
        if (value.isEmpty()) {
            jdbcTemplate.execute(SQLQuery.DELETE_ATTRIBUTE_VALUE_BY_KEYS(userId, attributeId));
            logger.info("Deleted attribute " + attributeId + ", value = " + value + ", object id = " + userId);
            return 0;
        } else {
            int count = jdbcTemplate.update(SQLQuery.UPDATE_ATTRIBUTE_VALUE(value, userId, attributeId));
            if (count == 0) {
                jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(value, userId, attributeId));
                logger.info("Created attribute " + attributeId + ", value = " + value + ", object id = " + userId);
                return 0;
            } else {
                logger.info("Updated attribute " + attributeId + ", value = " + value + ", object id = " + userId);
                return count;
            }
        }
    }

    public boolean updateUserPassword(int id, String password) {
        return updateUserAttr(password, id, Attribute.USER_PASSWORD) == 1;
    }

    public String getObjectImagePath(int id) {
        return jdbcTemplate.queryForObject(SQLQuery.OBJECT_IMAGE_PATH_BY_ID(id), (rs, rowNum) ->  rs.getString(IMG_OBJ));
    }

    private void setAdditionDataInSimpleUser(SimpleUser simpleUser) {
        int id = simpleUser.getId();
        try {
            String result = jdbcTemplate.queryForObject(SQLQuery.ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(id, Attribute.USER_DESCRIPTION)
                    , (rs, rowNum) -> rs.getString("value"));
            simpleUser.setDescription(result);
        } catch (IncorrectResultSizeDataAccessException ex) {
            logger.warn("Founded " + ex.getActualSize() + " instance/s of " + Attribute.USER_DESCRIPTION + "th attribute");
        }
    }
}
