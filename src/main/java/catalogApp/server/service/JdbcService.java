package catalogApp.server.service;

import catalogApp.server.dao.AuthDAO;
import catalogApp.server.dao.IJdbcDAO;
import catalogApp.server.dao.constants.Attribute;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JdbcService {

    private IJdbcDAO jdbcDAO;
    private AuthDAO authDAO;

    public JdbcService(IJdbcDAO jdbcDAO, AuthDAO authDAO) {
        this.jdbcDAO = jdbcDAO;
        this.authDAO = authDAO;
    }

    public List<Book> getAllBooks() {
        return jdbcDAO.getAllBooks();
    }

    public List<String> getAllAuthorsNames() {
        return jdbcDAO.getAllAuthorNames();
    }

    public Book addBook(String name, String authorName) {
        return jdbcDAO.addBook(name, authorName);
    }

    public List<Book> getLibBooks() {
        int user_id = authDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        return jdbcDAO.getUsersBooks(user_id);
    }

    public void addBooksToLibrary(List<Integer> idList) {
        int user_id = authDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        jdbcDAO.addObjectsToUserLibrary(user_id, idList, Attribute.LIKED_BOOK_ID);
    }

    public List<Song> getAllSong() {
        return jdbcDAO.getAllSongs();
    }

    public List<String> getAllGenresNames(){
        return jdbcDAO.getAllGenreNames();
    }

    public Song addSong(String name, String genreName, String duration){
        return jdbcDAO.addSong(name, genreName,duration);
    }

    public List<Song> getLibSongs(){
        int userId = authDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        return jdbcDAO.getUsersSongs(userId);
    }

    public void addSongsToLibrary(List<Integer> idList){
        int userId = authDAO.getSimpleUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        jdbcDAO.addObjectsToUserLibrary(userId, idList, Attribute.LIKED_SONG_ID);
    }
}
