package catalogApp.server.dao;

import catalogApp.server.dao.constants.Attribure;
import catalogApp.server.dao.constants.SQLQuery;
import catalogApp.server.dao.constants.Types;
import catalogApp.server.dao.mapper.BookMapper;
import catalogApp.server.dao.mapper.SongMapper;
import catalogApp.server.dao.mapper.UserMapper;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import catalogApp.shared.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public class EavDAO implements IJdbcDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public EavDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(SQLQuery.ALL_BOOKS(), new BookMapper());
    }

    @Override
    public List<String> getAllAuthorNames() {
        return jdbcTemplate.query(SQLQuery.ALL_AUTHORS_NAMES(), (resultSet, i) -> resultSet.getString("name"));
    }

    @Override
    public boolean addBook(String name, String authorName) {
        int author_id;
        int book_id;
        try {
            author_id = jdbcTemplate.queryForObject(SQLQuery.AUTHOR_ID_BY_NAME(authorName), (rs, rowNum) -> rs.getInt("idObject"));
        } catch (IncorrectResultSizeDataAccessException exception) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            author_id = jdbcInsert.withTableName("Object")
                    .usingGeneratedKeyColumns("idObject")
                    .executeAndReturnKey(new HashMap<String, String>() {{
                        put("name", authorName);
                        put("idType", String.valueOf(Types.AUTHOR));
                    }})
                    .intValue();
        }
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        book_id = jdbcInsert.withTableName("Object")
                .usingGeneratedKeyColumns("idObject")
                .executeAndReturnKey(new HashMap<String, String>() {{
                    put("name", name);
                    put("idType", String.valueOf(Types.BOOK));
                }})
                .intValue();
        try {
            jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(String.valueOf(author_id), book_id, Attribure.BOOK_AUTHOR_ID));
            return true;
        }catch (DataAccessException ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Song> getAllSongs() {
        return jdbcTemplate.query(SQLQuery.ALL_SONGS(), new SongMapper());
    }

}
