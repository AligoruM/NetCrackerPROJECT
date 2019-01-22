package catalogApp.server.service;

import catalogApp.server.dao.AuthDAO;
import catalogApp.server.dao.IJdbcDAO;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import org.springframework.security.core.Authentication;
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

    public List<Book> getAllBooks(){
        return jdbcDAO.getAllBooks();
    }

    public List<String> getAllAuthorsNames(){
        return jdbcDAO.getAllAuthorNames();
    }

    public Book addBook(String name, String authorName){
        return jdbcDAO.addBook(name, authorName);
    }

    public List<Song> getAllSong(){
        return jdbcDAO.getAllSongs();
    }

    public List<Integer> getLikedBooksIds(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int user_id = authDAO.getSimpleUser(authentication.getName()).getId();
        return jdbcDAO.getBooksIdsByUserId(user_id);
    }
}
