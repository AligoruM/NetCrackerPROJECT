package catalogApp.server.service;

import catalogApp.server.dao.IJdbcDAO;
import catalogApp.shared.model.Book;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JdbcService {
    private IJdbcDAO jdbcDAO;

    @Autowired
    public void setJdbcDAO(IJdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }

    List<Book> getAllBooks(){
        return jdbcDAO.getAllBooks();
    }
}
