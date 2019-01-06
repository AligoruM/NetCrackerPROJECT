package catalogApp.client.services;

import catalogApp.shared.model.Book;
import catalogApp.shared.model.Type;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface TestService extends RestService {
    @POST
    @Path("rest/test/book")
    @Consumes(MediaType.APPLICATION_JSON)
    void getAllBooks(MethodCallback<List<Book>> callback);

    @POST
    @Path("rest/test/addBook")
    void addBook(List<String> params, MethodCallback<Void> callback);

    /*@POST
    @Path("rest/test/getType/{id}")
    Type getTypeById(int i, MethodCallback<Type> callback);*/
}
