package catalogApp.server.service;

import catalogApp.shared.exception.ItemAlreadyExistException;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.Song;

import java.util.List;
import java.util.Map;

public interface IJdbcService {
    List<Book> getAllBooks();

    List<String> getAllAuthorsNames();

    Book addBook(String name, String authorName) throws ItemAlreadyExistException;

    List<Book> getLibBooks();

    List<Book> addBooksToLibrary(List<Integer> idList);

    Boolean addAuthor(String authorName);

    Book updateBook(Book newBook);

    List<Song> getAllSong();

    List<String> getAllGenresNames();

    Song addSong(String name, String genreName, String duration) throws ItemAlreadyExistException;

    List<Song> getLibSongs();

    List<Song> addSongsToLibrary(List<Integer> idList);

    Song updateSong(Song newSong);

    SimpleUser getSimpleUser();

    List<SimpleUser> getAllUsers();

    SimpleUser addUser(String name, String pass, String role);

    void deleteObjectFromUserLib(List<Integer> ids, int type);

    void updateUser(SimpleUser simpleUser);

    void archiveItems(List<Integer> ids);

    void restoreItems(List<Integer> ids);

    double markItem(int objectId, int newMark);

    boolean changePassword(String password);
}
