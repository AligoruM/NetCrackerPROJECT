package catalogApp.server.dao;

import catalogApp.shared.model.Book;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository("bookDAO")
@Transactional
public class BookDAOImpl extends BasicDAO implements IBookDAO {
    public List<Book> findAllBooks() {
        Criteria criteria = getSession().createCriteria((Book.class));
        return criteria.list();
    }
}
