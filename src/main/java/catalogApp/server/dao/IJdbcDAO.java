package catalogApp.server.dao;

import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;

import java.util.List;

public interface IJdbcDAO {

    List<Book> getAllBooks();

    List<String> getAllAuthorNames();

    Book addBook(String name, String authorName);

    List<Song> getAllSongs();

    List<Integer> getObjectsIdsByUserIdAndAttribute(int id, int idAttribute);

    void addObjectsToUserLibrary(int id, List<Integer> objectIds, int attributeId);
}
