package catalogApp.server.dao.type;

import catalogApp.shared.model.Type;

import java.util.List;

public interface ITypeDAO {
    List<Type> findAllTypes();
    void addNewType(Type type);
    Type getTypeById(int id);
}
