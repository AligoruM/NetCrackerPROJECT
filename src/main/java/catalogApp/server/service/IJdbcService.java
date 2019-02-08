package catalogApp.server.service;

import catalogApp.shared.model.Book;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.Song;

import java.util.List;

public interface IJdbcService {
    List<Book> getAllBooks();

    List<String> getAllAuthorsNames();

    Book addBook(String name, String authorName);

    List<Book> getLibBooks();

    List<Book> addBooksToLibrary(List<Integer> idList);

    void deleteBooksFromLibrary(List<Integer> ids);

    Book updateBook(Book newBook);

    List<Song> getAllSong();

    List<String> getAllGenresNames();

    Song addSong(String name, String genreName, String duration);

    List<Song> getLibSongs();

    List<Song> addSongsToLibrary(List<Integer> idList);

    Song updateSong(Song newSong);

    SimpleUser getSimpleUser();

    List<SimpleUser> getAllUsers();

    void deleteObjectFromUserLib(List<Integer> ids, int type);

    void updateUser(SimpleUser simpleUser);

    void archiveItems(List<Integer> ids);

    void restoreItems(List<Integer> ids);
}
