package catalogApp.server.service;

import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;

import java.util.List;
import java.util.Map;

public interface IJdbcService {
    List<Book> getAllBooks();

    List<String> getAllAuthorsNames();

    Book addBook(String name, String authorName);

    List<Book> getLibBooks();

    void addBooksToLibrary(List<Integer> idList);

    void deleteBooksFromLibrary(List<Integer> ids);

    void updateBook(int id, Map<String, String> params);

    List<Song> getAllSong();

    List<String> getAllGenresNames();

    Song addSong(String name, String genreName, String duration);

    List<Song> getLibSongs();

    void addSongsToLibrary(List<Integer> idList);

    void deleteSongsFromLibrary(List<Integer> ids);

    void updateSong(int id, Map<String, String> params);
}
