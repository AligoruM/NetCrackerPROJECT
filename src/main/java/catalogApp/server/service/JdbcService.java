package catalogApp.server.service;

import catalogApp.server.dao.IJdbcDAO;
import catalogApp.server.dao.UserDAO;
import catalogApp.server.dao.constants.Attribute;
import catalogApp.shared.model.BaseObject;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        if(!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
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
    public void updateBook(Book newBook) {
        if (newBook.getName()!=null){
            jdbcDAO.updateObjectName(newBook.getId(), newBook.getName());
        }
    }

    @Override
    public List<Song> getAllSong() {
        List<Song> songs = jdbcDAO.getAllSongs();
        if(!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
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
    public void updateSong(Song newSong) {
        if (newSong.getName()!=null) {
            jdbcDAO.updateObjectName(newSong.getId(), newSong.getName());
        }

        if (newSong.getDuration()<=0) {
            jdbcDAO.updateAttributeValue(newSong.getId(), Attribute.SONG_DURATION, "-1");
        }else{
            jdbcDAO.updateAttributeValue(newSong.getId(), Attribute.SONG_DURATION, String.valueOf(newSong.getDuration()));
        }
    }

    @Override
    public SimpleUser getSimpleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleUser user = userDAO.getSimpleUser(authentication.getName());
        if (user != null)
            return user;
        else
            throw new UsernameNotFoundException("User not found");
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
    public void updateAvatar(String filename) {
        String filepath = IMAGE_SERVICE_DIR + "/" + filename;
        userDAO.updateAvatar(getUserId(), filepath);
    }

    @Override
    public String getUserAvatarPath(int id) {
        return userDAO.getUserAvatarPath(id);
    }

    @Override
    public void archiveItems(List<Integer> ids) {
        jdbcDAO.changeStateItems(ids, true);
    }

    @Override
    public void restoreItems(List<Integer> ids) {
        jdbcDAO.changeStateItems(ids, false);
    }

    private int getUserId(){
        return userDAO.getUserIdByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
