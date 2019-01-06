package catalogApp.server.dao.author;

import catalogApp.shared.model.Author;

public interface IAuthorDAO {
    Author getAuthorById(int id);
    Author getAuthorByName(String name);
}
