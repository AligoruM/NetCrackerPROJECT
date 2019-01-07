package catalogApp.server.dao.book;

import catalogApp.server.dao.BasicDAO;
import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Type;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("bookDAO")
@Transactional
public class BookDAOImpl extends BasicDAO implements IBookDAO {
    public List<Book> findAllBooks() {
        Criteria criteria = getSession().createCriteria((Book.class));

        return criteria.list();
    }

    @Override
    public void addNewBook(String name, Type type, Author author) {
        Book book = new Book(name, type, author);
        saveOrUpdate(book);

    }
}
