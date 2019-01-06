package catalogApp.server.dao.author;

import catalogApp.shared.model.Author;

import java.util.List;

public interface IAuthorDAO {
    List<String> getAllAuthorsNames();
    Author getAuthorById(int id);
    Author getAuthorByName(String name);
}
