package catalogApp.server.service;

import catalogApp.server.dao.IJdbcDAO;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JdbcService {

    private IJdbcDAO jdbcDAO;

    public JdbcService(IJdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
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

}
