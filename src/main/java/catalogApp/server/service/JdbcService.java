package catalogApp.server.service;

import catalogApp.server.config.SpringConfig;
import catalogApp.server.dao.IJdbcDAO;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.List;


public class JdbcService {
    private static AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    private IJdbcDAO jdbcDAO = (IJdbcDAO) context.getBean("jdbcEavDAO");

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
