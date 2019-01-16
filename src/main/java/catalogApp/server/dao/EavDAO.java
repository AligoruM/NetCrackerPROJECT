package catalogApp.server.dao;

import catalogApp.server.dao.constants.SQLQuery;
import catalogApp.server.dao.constants.Types;
import catalogApp.server.dao.mapper.BookMapper;
import catalogApp.shared.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jdbcEavDAO")
public class EavDAO implements IJdbcDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(SQLQuery.ALL_BOOKS(), new BookMapper());
    }

    @Override
    public List<String> getAllAuthorNames() {
        return jdbcTemplate.query(SQLQuery.ALL_AUTHORS_NAMES(), (resultSet, i) -> resultSet.getString("name"));
    }

    @Override
    public boolean addBook(String name, String authorName) {
        List<String> author_id = jdbcTemplate.query(SQLQuery.AUTHOR_ID_BY_NAME(authorName),(resultSet, i) -> resultSet.getString("idObject"));
        if(author_id.isEmpty()){
            jdbcTemplate.execute(SQLQuery.CREATE_OBJECT(authorName, Types.AUTHOR));
            author_id = jdbcTemplate.query(SQLQuery.AUTHOR_ID_BY_NAME(authorName), (resultSet, i) -> resultSet.getString("idObject"));
        }
        jdbcTemplate.execute(SQLQuery.CREATE_OBJECT(name, Types.BOOK));
        jdbcTemplate.execute(SQLQuery.CREATE_ATTRIBUTE_VALUE(author_id.get(0), ));
    }
}
