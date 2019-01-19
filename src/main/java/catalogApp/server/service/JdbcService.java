package catalogApp.server.service;

import catalogApp.server.dao.IJdbcDAO;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import catalogApp.shared.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
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

    public void addBook(String name, String authorName){
        jdbcDAO.addBook(name, authorName);
    }

    public List<Song> getAllSong(){
        return jdbcDAO.getAllSongs();
    }

}
