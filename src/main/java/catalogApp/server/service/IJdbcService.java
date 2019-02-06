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

    void updateBook(Book newBook);

    List<Song> getAllSong();

    List<String> getAllGenresNames();

    Song addSong(String name, String genreName, String duration);

    List<Song> getLibSongs();

    List<Song> addSongsToLibrary(List<Integer> idList);

    void deleteSongsFromLibrary(List<Integer> ids);

    void updateSong(Song newSong);

    SimpleUser getSimpleUser();

    List<SimpleUser> getAllUsers();

    void updateUser(SimpleUser simpleUser);

    void updateAvatar(String filename);

    String getUserAvatarPath(int id);

    void archiveItems(List<Integer> ids);

    void restoreItems(List<Integer> ids);
}
