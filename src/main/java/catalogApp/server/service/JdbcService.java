package catalogApp.server.service;

import catalogApp.server.dao.AuthDAO;
import catalogApp.server.dao.IJdbcDAO;
import catalogApp.server.dao.constants.Attribute;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class JdbcService implements IJdbcService {

    private IJdbcDAO jdbcDAO;
    private AuthDAO authDAO;

    public JdbcService(IJdbcDAO jdbcDAO, AuthDAO authDAO) {
        this.jdbcDAO = jdbcDAO;
        this.authDAO = authDAO;
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcDAO.getAllBooks();
    }

    @Override
    public List<String> getAllAuthorsNames() {
        return jdbcDAO.getAllAuthorNames();
    }

    @Override
    public Book addBook(String name, String authorName) {
        return jdbcDAO.addBook(name, authorName);
    }

    @Override
    public List<Book> getLibBooks() {
        int user_id = getUserId();
        return jdbcDAO.getUsersBooks(user_id);
    }

    @Override
    public void addBooksToLibrary(List<Integer> idList) {
        int user_id = getUserId();
        jdbcDAO.addObjectsToUserLibrary(user_id, idList, Attribute.LIKED_BOOK_ID);
    }

    @Override
    public void deleteBooksFromLibrary(List<Integer> ids) {
        int userId= getUserId();
        jdbcDAO.deleteObjectFromUserLibrary(userId, ids, Attribute.LIKED_BOOK_ID);
    }

    @Override
    public void updateBook(int id, Map<String, String> params) {
        if(params.containsKey("name")){
            jdbcDAO.updateObjectName(id, params.get("name"));
        }
    }

    @Override
    public List<Song> getAllSong() {
        return jdbcDAO.getAllSongs();
    }

    @Override
    public List<String> getAllGenresNames() {
        return jdbcDAO.getAllGenreNames();
    }

    @Override
    public Song addSong(String name, String genreName, String duration) {
        return jdbcDAO.addSong(name, genreName, duration);
    }

    @Override
    public List<Song> getLibSongs() {
        int userId = getUserId();
        return jdbcDAO.getUsersSongs(userId);
    }

    @Override
    public void addSongsToLibrary(List<Integer> idList) {
        int userId = getUserId();
        jdbcDAO.addObjectsToUserLibrary(userId, idList, Attribute.LIKED_SONG_ID);
    }

    @Override
    public void deleteSongsFromLibrary(List<Integer> ids) {
        int userId= getUserId();
        jdbcDAO.deleteObjectFromUserLibrary(userId, ids, Attribute.LIKED_SONG_ID);
    }

    @Override
    public void updateSong(int id, Map<String, String> params) {
        if(params.containsKey("name")) {
            jdbcDAO.updateObjectName(id, params.get("name"));
        }
        if(params.containsKey("duration")){
            jdbcDAO.updateAttributeValue(id, Attribute.SONG_DURATION, params.get("duration"));
        }
    }

    @Override
    public void rateSong(int id) {
        int userId = getUserId();
        //jdbcDAO.rateObject(id, )
    }

    private int getUserId(){
        return authDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }
}
