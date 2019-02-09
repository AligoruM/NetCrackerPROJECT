package catalogApp.server.dao;

import catalogApp.server.dao.constants.Attribute;
import catalogApp.server.dao.constants.SQLQuery;
import catalogApp.server.dao.constants.Types;
import catalogApp.server.dao.mapper.BookMapper;
import catalogApp.server.dao.mapper.SongMapper;
import catalogApp.shared.exception.ItemAlreadyExistException;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static catalogApp.server.dao.constants.Tables.*;

@Transactional
public class EavDAO implements IJdbcDAO {

    private final Logger logger = LoggerFactory.getLogger(EavDAO.class);

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
        return jdbcTemplate.query(SQLQuery.ALL_AUTHORS_NAMES(), (resultSet, i) -> resultSet.getString(NAME_OBJ));
    }

    @Override
    public Book addBook(String name, String authorName) throws ItemAlreadyExistException {
        if (name.trim().isEmpty() || authorName.trim().isEmpty()) {
            logger.info("Incoming params is empty. Book cannot be created.");
            return null;
        }
        for (Book item : getAllBooks()) {
            if(item.getAuthor().getName().equalsIgnoreCase(authorName) && item.getName().equalsIgnoreCase(name)){
                String msg = "Book with name = " + name + " and author = " + authorName + " already exist";
                logger.info(msg);
                System.out.println(msg);
                throw new ItemAlreadyExistException(msg);
            }
        }

        Integer authorId = createOrGetAuthor(authorName);
        int bookId;

        bookId = createObjectAndReturnNewId(name, Types.BOOK);
        try {
            jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(String.valueOf(authorId), bookId, Attribute.BOOK_AUTHOR_ID));
            return jdbcTemplate.queryForObject(SQLQuery.BOOK_BY_ID(bookId), new BookMapper());
        } catch (DataAccessException ex) {
            logger.error("Problem with access to DataBase", ex);
            return null;
        }
    }

    @Override
    public List<Book> getUsersBooks(int id) {
        List<Integer> ids = getObjectsIdsByUserIdAndAttribute(id, Attribute.LIKED_BOOK_ID);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(IDS_ALIAS, ids);
        return namedParameterJdbcTemplate.query(SQLQuery.BOOKS_BY_IDS(IDS_ALIAS), params, new BookMapper());
    }

    @Override
    public List<Book> getBooksByIds(List<Integer> ids) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(IDS_ALIAS, ids);
        return namedParameterJdbcTemplate.query(SQLQuery.BOOKS_BY_IDS(IDS_ALIAS), params, new BookMapper());
    }

    @Override
    public Book getBookById(int id) {
        return jdbcTemplate.queryForObject(SQLQuery.BOOK_BY_ID(id), new BookMapper());
    }

    @Override
    public void updateAuthor(int bookId, String authorName) {
        int authorId = createOrGetAuthor(authorName);
        jdbcTemplate.execute(SQLQuery.UPDATE_ATTRIBUTE_VALUE(String.valueOf(authorId), bookId, Attribute.BOOK_AUTHOR_ID));
    }

    @Override
    public List<Song> getAllSongs() {
        return jdbcTemplate.query(SQLQuery.ALL_SONGS(), new SongMapper());
    }

    @Override
    public List<String> getAllGenreNames() {
        return jdbcTemplate.query(SQLQuery.ALL_GENRES_NAMES(), (rs, rowNum) -> rs.getString(NAME_OBJ));
    }

    @Override
    public Song addSong(String name, String genreName, String duration) throws ItemAlreadyExistException {
        if (name.trim().isEmpty() || genreName.trim().isEmpty()) {
            logger.info("Incoming params is empty. Song cannot be created.");
            return null;
        }
        for (Song item : getAllSongs()) {
            if(item.getGenre().getName().equalsIgnoreCase(name) && item.getName().equalsIgnoreCase(genreName)){
                String msg = "Song with name = " + name + " and genre = " + genreName + " already exist";
                logger.info(msg);
                throw new ItemAlreadyExistException(msg);
            }
        }

        Integer genreId = createOrGetGenre(genreName);
        int songId;

        songId = createObjectAndReturnNewId(name, Types.SONG);
        try {
            jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(String.valueOf(genreId), songId, Attribute.SONG_GENRE_ID));
            if (duration.isEmpty() || Integer.valueOf(duration) <= 0) {
                jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE("-1", songId, Attribute.SONG_DURATION));
                logger.info("Incoming duration is empty or isn't positive.");
            }
            else {
                jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(duration, songId, Attribute.SONG_DURATION));
            }
            return jdbcTemplate.queryForObject(SQLQuery.SONG_BY_ID(songId), new SongMapper());
        } catch (DataAccessException ex) {
            logger.error("Problem with access to DataBase", ex);
            return null;
        }
    }

    @Override
    public List<Song> getUsersSongs(int id) {
        List<Integer> ids = getObjectsIdsByUserIdAndAttribute(id, Attribute.LIKED_SONG_ID);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(IDS_ALIAS, ids);
        return namedParameterJdbcTemplate.query(SQLQuery.SONGS_BY_IDS(IDS_ALIAS), params, new SongMapper());
    }

    @Override
    public List<Song> getSongsByIds(List<Integer> ids) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(IDS_ALIAS, ids);
        return namedParameterJdbcTemplate.query(SQLQuery.SONGS_BY_IDS(IDS_ALIAS), params, new SongMapper());
    }

    @Override
    public Song getSongById(int id) {
        return jdbcTemplate.queryForObject(SQLQuery.SONG_BY_ID(id), new SongMapper());
    }

    @Override
    public void updateGenre(int songId, String genreName) {
        int genreId = createOrGetGenre(genreName);
        jdbcTemplate.execute(SQLQuery.UPDATE_ATTRIBUTE_VALUE(String.valueOf(genreId), songId, Attribute.SONG_GENRE_ID));
    }

    @Override
    public void updateObjectName(int id, String name) {
        jdbcTemplate.execute(SQLQuery.UPDATE_OBJECT_NAME(id, name));
    }

    @Override
    public void updateObjectImage(int id, String filepath) {
        jdbcTemplate.execute(SQLQuery.UPDATE_OBJECT_IMAGE(filepath, id));
    }

    @Override
    public void updateObjectComment(int id, String comment) {
        jdbcTemplate.execute(SQLQuery.UPDATE_OBJECT_COMMENT(comment, id));
    }

    @Override
    public void updateAttributeValue(int id, int attributeId, String value) {
        jdbcTemplate.execute(SQLQuery.UPDATE_ATTRIBUTE_VALUE(value, id, attributeId));
    }

    private List<Integer> getObjectsIdsByUserIdAndAttribute(int id, int idAttribute) {
        return jdbcTemplate.query(SQLQuery.ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(id, idAttribute), (rs, rowNum) -> rs.getInt(VALUE_AV));
    }

    private int createObjectAndReturnNewId(String name, int type) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        return jdbcInsert.withTableName(OBJECT_TABLE)
                .usingGeneratedKeyColumns(ID_OBJ)
                .executeAndReturnKey(new HashMap<String, String>() {{
                    put(NAME_OBJ, name);
                    put(ID_TYPE_OBJ, String.valueOf(type));
                    put(ARCHIVED_OBJ, "0");
                }})
                .intValue();
    }

    private Integer createOrGetAuthor(String authorName){
        Integer authorId;
        try {
            authorId = jdbcTemplate.queryForObject(SQLQuery.AUTHOR_ID_BY_NAME(authorName), (rs, rowNum) -> rs.getInt(ID_OBJ));
        } catch (IncorrectResultSizeDataAccessException exception) {
            authorId = createObjectAndReturnNewId(authorName, Types.AUTHOR);
            logger.info("Author(" + authorName +") not found. Created.");
        }
        return authorId;
    }

    private Integer createOrGetGenre(String genreName){
        Integer genreId;
        try {
            genreId = jdbcTemplate.queryForObject(SQLQuery.GENRE_ID_BY_NAME(genreName), (rs, rowNum) -> rs.getInt(ID_OBJ));
        } catch (IncorrectResultSizeDataAccessException exception) {
            genreId = createObjectAndReturnNewId(genreName, Types.SONG_GENRE);
            logger.info("Author(" + genreName +") not found. Created.");
        }
        return genreId;
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
        for (int x : ids) {
            jdbcTemplate.execute(SQLQuery.DELETE_ATTRIBUTE_VALUE_BY_ALL_FIELDS(String.valueOf(x), id, attributeId));
        }
    }

    @Override
    public void changeStateItems(List<Integer> ids, boolean state) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(IDS_ALIAS, ids);
        int iState = state ? 1 : 0;
        namedParameterJdbcTemplate.update(SQLQuery.UPDATE_OBJECTS_STATE(iState, IDS_ALIAS), params);
    }
}


























