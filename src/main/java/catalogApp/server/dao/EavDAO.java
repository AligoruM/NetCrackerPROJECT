package catalogApp.server.dao;

import catalogApp.server.dao.constants.Attribute;
import catalogApp.server.dao.constants.SQLQuery;
import catalogApp.server.dao.constants.Types;
import catalogApp.server.dao.mapper.BookMapper;
import catalogApp.server.dao.mapper.SongMapper;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    public Book addBook(String name, String authorName) {
        if (name.trim().isEmpty() || authorName.trim().isEmpty()) {
            return null;
        }
        Integer authorId;
        int bookId;
        try {
            authorId = jdbcTemplate.queryForObject(SQLQuery.AUTHOR_ID_BY_NAME(authorName), (rs, rowNum) -> rs.getInt("idObject"));
        } catch (IncorrectResultSizeDataAccessException exception) {
            authorId = createObjectAndReturnNewId(authorName, Types.AUTHOR);
        }
        bookId = createObjectAndReturnNewId(name, Types.BOOK);
        try {
            jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(String.valueOf(authorId), bookId, Attribute.BOOK_AUTHOR_ID));
            return jdbcTemplate.queryForObject(SQLQuery.BOOK_BY_ID(bookId), new BookMapper());
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getUsersBooks(int id) {
        List<Integer> ids = getObjectsIdsByUserIdAndAttribute(id, Attribute.LIKED_BOOK_ID);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", ids);
        return namedParameterJdbcTemplate.query(SQLQuery.BOOKS_BY_IDS("ids"), params, new BookMapper());
    }

    @Override
    public List<Song> getAllSongs() {
        return jdbcTemplate.query(SQLQuery.ALL_SONGS(), new SongMapper());
    }

    @Override
    public List<String> getAllGenreNames() {
        return jdbcTemplate.query(SQLQuery.ALL_GENRES_NAMES(), (rs, rowNum) -> rs.getString("name"));
    }

    @Override
    public Song addSong(String name, String genreName, String duration) {
        if (name.trim().isEmpty() || genreName.trim().isEmpty()) {
            return null;
        }
        Integer genreId;
        int songId;
        try {
            genreId = jdbcTemplate.queryForObject(SQLQuery.GENRE_ID_BY_NAME(genreName), (rs, rowNum) -> rs.getInt("idObject"));
        } catch (IncorrectResultSizeDataAccessException ex) {
            genreId = createObjectAndReturnNewId(genreName, Types.SONG_GENRE);
        }
        songId = createObjectAndReturnNewId(name, Types.SONG);
        try {
            jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(String.valueOf(genreId), songId, Attribute.SONG_GENRE_ID));
            jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(String.valueOf(duration), songId, Attribute.SONG_DURATION));
            return jdbcTemplate.queryForObject(SQLQuery.SONG_BY_ID(songId), new SongMapper());
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Song> getUsersSongs(int id) {
        List<Integer> ids = getObjectsIdsByUserIdAndAttribute(id, Attribute.LIKED_SONG_ID);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", ids);
        return namedParameterJdbcTemplate.query(SQLQuery.SONGS_BY_IDS("ids"), params, new SongMapper());
    }

    @Override
    public void updateObjectName(int id, String name) {
        jdbcTemplate.execute(SQLQuery.UPDATE_OBJECT_NAME(id, name));
    }

    @Override
    public void updateAttributeValue(int id, int attributeId, String value) {
        jdbcTemplate.execute(SQLQuery.UPDATE_ATTRIBUTE_VALUE(value, id, attributeId));
    }

    private List<Integer> getObjectsIdsByUserIdAndAttribute(int id, int idAttribute) {
        return jdbcTemplate.query(SQLQuery.ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(id, idAttribute), (rs, rowNum) -> rs.getInt("value"));
    }

    private int createObjectAndReturnNewId(String name, int type) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        return jdbcInsert.withTableName("Object")
                .usingGeneratedKeyColumns("idObject")
                .executeAndReturnKey(new HashMap<String, String>() {{
                    put("name", name);
                    put("idType", String.valueOf(type));
                }})
                .intValue();
    }

    @Override
    public void addObjectsToUserLibrary(int userId, List<Integer> objectIds, int attributeId) {
        List<Integer> ids = getObjectsIdsByUserIdAndAttribute(userId, attributeId);
        for (int x : objectIds) {
            if (!ids.contains(x)) {
                jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(String.valueOf(x), userId, attributeId));
            }
        }
    }

    @Override
    public void deleteObjectFromUserLibrary(int id, List<Integer> ids, int attributeId) {
        for (int x: ids) {
            jdbcTemplate.execute(SQLQuery.DELETE_ATTRIBUTE_VALUE_BY_ALL_FIELDS(String.valueOf(x), id, attributeId));
        }
    }


}


























