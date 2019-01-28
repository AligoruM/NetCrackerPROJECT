package catalogApp.server.service;

import catalogApp.server.dao.UserDAO;
import catalogApp.server.dao.IJdbcDAO;
import catalogApp.server.dao.constants.Attribute;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.Song;
import catalogApp.shared.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class JdbcService implements IJdbcService {

    private IJdbcDAO jdbcDAO;
    private UserDAO userDAO;

    public JdbcService(IJdbcDAO jdbcDAO, UserDAO userDAO) {
        this.jdbcDAO = jdbcDAO;
        this.userDAO = userDAO;
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
        int user_id = userDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        return jdbcDAO.getUsersBooks(user_id);
    }

    @Override
    public void addBooksToLibrary(List<Integer> idList) {
        int user_id = userDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        jdbcDAO.addObjectsToUserLibrary(user_id, idList, Attribute.LIKED_BOOK_ID);
    }

    @Override
    public void deleteBooksFromLibrary(List<Integer> ids) {
        int userId= userDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
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
        int userId = userDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        return jdbcDAO.getUsersSongs(userId);
    }

    @Override
    public void addSongsToLibrary(List<Integer> idList) {
        int userId = userDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        jdbcDAO.addObjectsToUserLibrary(userId, idList, Attribute.LIKED_SONG_ID);
    }

    @Override
    public void deleteSongsFromLibrary(List<Integer> ids) {
        int userId= userDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
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
    public SimpleUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleUser user = userDAO.getSimpleUser(authentication.getName());
        if(user!=null)
            return user;
        else
            throw new UsernameNotFoundException("User not found");
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void updateUserProfile(Map<String, String> params) {
        int userId= userDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        userDAO.updateUserProfile(userId, params);
    }

    @Override
    public Map<String, String> getUserProfile() {
        int userId= userDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        return userDAO.getUserProfile(userId);
    }
}
