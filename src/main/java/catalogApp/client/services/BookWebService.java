package catalogApp.client.services;

import catalogApp.shared.model.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

public interface BookWebService extends RestService {
    @POST
    @Path("rest/book")
    void getAllBooks(MethodCallback<List<Book>> callback);

    @POST
    @Path("rest/addBook")
    void addBook(List<String> params, MethodCallback<Book> callback);

    @POST
    @Path("rest/getAuthor")
    void getAllAuthor(MethodCallback<List<String>> callback);

    @POST
    @Path("rest/addBooksToUserLib")
    void addBooksToUserLib(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/getUserBooks")
    void getUserBooks(MethodCallback<List<Book>> callback);

}
