package catalogApp.server.dao.author;

import catalogApp.server.dao.BasicDAO;
import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("authorDAO")
@Transactional
public class AuthorDAOImpl extends BasicDAO implements IAuthorDAO {
    @Override
    public List<String> getAllAuthorsNames() {
        Criteria criteria = getSession().createCriteria((Author.class));
        criteria.setProjection(Projections.property("name"));
        return criteria.list();
    }

    @Override
    public Author getAuthorById(int id) {
        Criteria criteria = getSession().createCriteria(Author.class);
        criteria.add(Restrictions.eq("id", id));
        return (Author) criteria.uniqueResult();
    }
    @Override
    public Author getAuthorByName(String name) {
        Criteria criteria = getSession().createCriteria(Author.class);
        criteria.add(Restrictions.eq("name", name));
        return (Author) criteria.uniqueResult();
    }
    //TODO
    @Override
    public List<Book> getAuthorsBooksById() {
        return null;
    }
    //TODO
    @Override
    public List<Book> getAuthorsBooksByName() {
        return null;
    }
}
