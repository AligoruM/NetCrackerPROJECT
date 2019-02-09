package catalogApp.server.service;

import catalogApp.server.dao.IJdbcDAO;
import catalogApp.server.dao.UserDAO;
import catalogApp.server.dao.constants.Attribute;
import catalogApp.shared.exception.ItemAlreadyExistException;
import catalogApp.shared.model.BaseObject;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.Song;
import org.apache.commons.codec.digest.Md5Crypt;
import org.eclipse.jetty.util.security.Credential;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.Arrays;
import java.util.List;

import static catalogApp.shared.constants.FileServiceConstants.IMAGE_SERVICE_DIR;


@Service
public class JdbcService implements IJdbcService {


    private IJdbcDAO jdbcDAO;
    private UserDAO userDAO;

    private BCryptPasswordEncoder bCrypt;

    public JdbcService(IJdbcDAO jdbcDAO, UserDAO userDAO, BCryptPasswordEncoder encoder) {
        this.jdbcDAO = jdbcDAO;
        this.userDAO = userDAO;
        this.bCrypt = encoder;
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
    public Book addBook(String name, String authorName) throws ItemAlreadyExistException {
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
        for (Book item : jdbcDAO.getAllBooks()) {
            String newAuthor = newBook.getAuthor() == null ? null : newBook.getAuthor().getName();
            String newName = newBook.getName();
            if (newName != null && newAuthor != null) {
                if (item.getAuthor().getName().equalsIgnoreCase(newAuthor) && item.getName().equalsIgnoreCase(newName))
                    return null;
            }
        }
        updateBaseObjectFields(newBook);
        if (newBook.getAuthor() != null) {
            jdbcDAO.updateAuthor(newBook.getId(), newBook.getAuthor().getName());
        }
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
    public Song addSong(String name, String genreName, String duration) throws ItemAlreadyExistException {
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
    public Song updateSong(Song newSong) {
        int id = newSong.getId();
        for (Song item : jdbcDAO.getAllSongs()) {
            String newGenre = newSong.getGenre()==null ? null : newSong.getGenre().getName();
            String newName = newSong.getName();
            if (newName != null && newGenre != null) {
                if (item.getGenre().getName().equalsIgnoreCase(newGenre) && item.getName().equalsIgnoreCase(newName))
                    return null;
            }
        }
        updateBaseObjectFields(newSong);

        if (newSong.getGenre() != null) {
            jdbcDAO.updateGenre(newSong.getId(), newSong.getGenre().getName());
        }

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
    public void deleteObjectFromUserLib(List<Integer> ids, int type) {
        int userId = getUserId();
        jdbcDAO.deleteObjectFromUserLibrary(userId, ids, type);
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

    @Override
    public boolean changePassword(String password) {
        String bCryptPass = bCrypt.encode(password);
        boolean success = userDAO.updateUserPassword(getUserId(), bCryptPass);
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(!success);
        return success;
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
