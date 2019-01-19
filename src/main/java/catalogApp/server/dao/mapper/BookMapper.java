package catalogApp.server.dao.mapper;

import catalogApp.server.dao.constants.Types;
import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Type;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    private static Type bookType = new Type(Types.BOOK, "Book");

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        Author author = new Author();

        author.setId(resultSet.getInt("authorId"));
        author.setName(resultSet.getString("authorName"));

        book.setId(resultSet.getInt("idObject"));
        book.setName(resultSet.getString("objectName"));
        book.setAuthor(author);
        book.setType(bookType);

        return book;
    }
}
