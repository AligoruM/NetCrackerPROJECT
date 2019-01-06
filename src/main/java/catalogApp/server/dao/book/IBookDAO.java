package catalogApp.server.dao.book;

import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Type;

import java.util.List;

public interface IBookDAO {
    List<Book> findAllBooks();
    void addNewBook(String name, Type type, Author author);
}
