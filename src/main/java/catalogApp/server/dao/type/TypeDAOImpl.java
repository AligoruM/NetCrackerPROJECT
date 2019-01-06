package catalogApp.server.dao.type;

import catalogApp.server.dao.BasicDAO;
import catalogApp.shared.model.Type;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository("typeDAO")
@Transactional
public class TypeDAOImpl extends BasicDAO implements ITypeDAO {
    @Override
    public List<Type> findAllTypes() {
        return null;
    }

    @Override
    public void addNewType(Type type) {

    }

    @Override
    public Type getTypeById(int id) {
        Criteria criteria = getSession().createCriteria(Type.class);
        criteria.add(Restrictions.eq("id", id));
        return (Type) criteria.uniqueResult();

    }
}
