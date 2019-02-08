package catalogApp.server.service;

import catalogApp.server.dao.IJdbcDAO;
import catalogApp.server.dao.UserDAO;
import catalogApp.server.dao.constants.Attribute;
import catalogApp.shared.model.BaseObject;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.Song;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static catalogApp.shared.constants.FileServiceConstants.IMAGE_SERVICE_DIR;


@Service
public class JdbcService implements IJdbcService {


    private IJdbcDAO jdbcDAO;
    private UserDAO userDAO;

    public JdbcService(IJdbcDAO jdbcDAO, UserDAO userDAO) {
        this.jdbcDAO = jdbcDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = jdbcDAO.getAllBooks();
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            books.removeIf(BaseObject::isArchived);
        }
        return books;
    }

    @Override
    public List<String> getAllAuthorsNames() {
        return jdbcDAO.getAllAuthorNames();
    }

    @Override
    public Book addBook(String name, String authorName) {
        return jdbcDAO.addBook(name, authorName);
    }

    @Override
    public List<Book> getLibBooks() {
        int userId = getUserId();
        return jdbcDAO.getUsersBooks(userId);
    }

    @Override
    public List<Book> addBooksToLibrary(List<Integer> idList) {
        int userId = getUserId();
        jdbcDAO.addObjectsToUserLibrary(userId, idList, Attribute.LIKED_BOOK_ID);
        return jdbcDAO.getBooksByIds(idList);
    }

    @Override
    public void deleteBooksFromLibrary(List<Integer> ids) {
        int userId = getUserId();
        jdbcDAO.deleteObjectFromUserLibrary(userId, ids, Attribute.LIKED_BOOK_ID);
    }

    @Override
    public Book updateBook(Book newBook) {
        updateBaseObjectFields(newBook);
        return jdbcDAO.getBookById(newBook.getId());
    }

    @Override
    public List<Song> getAllSong() {
        List<Song> songs = jdbcDAO.getAllSongs();
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            songs.removeIf(BaseObject::isArchived);
        }
        return songs;
    }

    @Override
    public List<String> getAllGenresNames() {
        return jdbcDAO.getAllGenreNames();
    }

    @Override
    public Song addSong(String name, String genreName, String duration) {
        return jdbcDAO.addSong(name, genreName, duration);
    }

    @Override
    public List<Song> getLibSongs() {
        int userId = getUserId();
        return jdbcDAO.getUsersSongs(userId);
    }

    @Override
    public List<Song> addSongsToLibrary(List<Integer> idList) {
        int userId = getUserId();
        jdbcDAO.addObjectsToUserLibrary(userId, idList, Attribute.LIKED_SONG_ID);
        return jdbcDAO.getSongsByIds(idList);
    }

    @Override
    public void deleteSongsFromLibrary(List<Integer> ids) {
        int userId = getUserId();
        jdbcDAO.deleteObjectFromUserLibrary(userId, ids, Attribute.LIKED_SONG_ID);
    }

    @Override
    public Song updateSong(Song newSong) {
        int id = newSong.getId();
        updateBaseObjectFields(newSong);
        if (newSong.getDuration() < 0) {
            jdbcDAO.updateAttributeValue(id, Attribute.SONG_DURATION, "-1");
        } else if (newSong.getDuration() > 0) {
            jdbcDAO.updateAttributeValue(id, Attribute.SONG_DURATION, String.valueOf(newSong.getDuration()));
        }
        return jdbcDAO.getSongById(id);
    }

    @Override
    public SimpleUser getSimpleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleUser user = userDAO.getSimpleUser(authentication.getName());
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public List<SimpleUser> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void updateUser(SimpleUser simpleUser) {
        userDAO.updateUserAttributes(simpleUser, getSimpleUser());
    }


    @Override
    public void archiveItems(List<Integer> ids) {
        jdbcDAO.changeStateItems(ids, true);
    }

    @Override
    public void restoreItems(List<Integer> ids) {
        jdbcDAO.changeStateItems(ids, false);
    }

    private int getUserId() {
        return userDAO.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private void updateBaseObjectFields(BaseObject object) {
        int id = object.getId();
        if (object.getName() != null) {
            jdbcDAO.updateObjectName(id, object.getName());
        }
        if (object.getComment() != null) {
            jdbcDAO.updateObjectComment(id, object.getComment());
        }
        if (object.getImagePath() != null) {
            String filepath = IMAGE_SERVICE_DIR + "/" + object.getImagePath();
            jdbcDAO.updateObjectImage(id, filepath);
        }
    }

}
