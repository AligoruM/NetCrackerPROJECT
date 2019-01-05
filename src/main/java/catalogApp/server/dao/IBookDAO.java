package catalogApp.server.dao;

import catalogApp.shared.model.Book;

import java.util.List;

public interface IBookDAO {
    List<Book> findAllBooks();
}
