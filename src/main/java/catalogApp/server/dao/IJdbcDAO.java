package catalogApp.server.dao;

import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;

import java.util.List;

public interface IJdbcDAO {

    //Books
    List<Book> getAllBooks();

    List<String> getAllAuthorNames();

    Book addBook(String name, String authorName);

    List<Book> getUsersBooks(int id);

    List<Book> getBooksByIds(List<Integer> ids);

    Book getBookById(int id);

    //Songs
    List<Song> getAllSongs();

    List<String> getAllGenreNames();

    Song addSong(String name, String genreName, String duration);

    List<Song> getUsersSongs(int id);

    List<Song> getSongsByIds(List<Integer> ids);

    Song getSongById(int id);

    //General
    void updateObjectName(int id, String name);

    void updateObjectImage(int id, String filepath);

    void updateObjectComment(int id, String comment);

    void updateAttributeValue(int id, int attributeId, String value);

    void addObjectsToUserLibrary(int id, List<Integer> objectIds, int attributeId);

    void deleteObjectFromUserLibrary(int id, List<Integer> ids, int attributeId);

    void changeStateItems(List<Integer> ids, boolean state);
}
