package catalogApp.server.dao;

import catalogApp.shared.model.Book;

import java.util.List;

public interface IJdbcDAO {

    List<Book> getAllBooks();

    List<String> getAllAuthorNames();

    boolean addBook(String name, String authorName);
}
