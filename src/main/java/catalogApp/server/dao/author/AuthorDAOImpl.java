package catalogApp.server.dao.author;

import catalogApp.server.dao.BasicDAO;
import catalogApp.shared.model.Author;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("authorDAO")
@Transactional
public class AuthorDAOImpl extends BasicDAO implements IAuthorDAO {
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
}
