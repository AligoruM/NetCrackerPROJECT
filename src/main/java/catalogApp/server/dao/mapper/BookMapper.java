package catalogApp.server.dao.mapper;

import catalogApp.server.dao.constants.Types;
import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Type;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    private static final Type bookType = new Type(Types.BOOK, "Book");
    private static final Type authorType = new Type(Types.AUTHOR, "Author");

    @Override
    public Book mapRow(@Nonnull ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        Author author = new Author();

        author.setId(resultSet.getInt("authorId"));
        author.setName(resultSet.getString("authorName"));
        author.setType(authorType);
        book.setId(resultSet.getInt("idObject"));
        book.setName(resultSet.getString("objectName"));
        book.setAuthor(author);
        book.setType(bookType);

        return book;
    }
}
