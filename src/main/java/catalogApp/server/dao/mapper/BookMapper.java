package catalogApp.server.dao.mapper;

import catalogApp.server.dao.constants.Tables;
import catalogApp.server.dao.constants.Types;
import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Type;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

import static catalogApp.server.dao.constants.Tables.*;

public class BookMapper implements RowMapper<Book> {

    private static final Type bookType = new Type(Types.BOOK, "Book");
    private static final Type authorType = new Type(Types.AUTHOR, "Author");

    @Override
    public Book mapRow(@Nonnull ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        Author author = new Author();

        author.setId(resultSet.getInt(ID_AUTHOR_ALIAS));
        author.setName(resultSet.getString(NAME_AUTHOR_ALIAS));
        author.setType(authorType);
        book.setId(resultSet.getInt(ID_OBJ_ALIAS));
        book.setName(resultSet.getString(NAME_OBJ_ALIAS));
        book.setArchived(resultSet.getBoolean(ARCHIVED_OBJ_ALIAS));
        book.setComment(resultSet.getString(COMMENT_OBJ_ALIAS));
        book.setImagePath(resultSet.getString(IMG_OBJ_ALIAS));
        book.setRating(resultSet.getFloat(MARK_ALIAS));
        book.setAuthor(author);
        book.setType(bookType);

        return book;
    }
}
