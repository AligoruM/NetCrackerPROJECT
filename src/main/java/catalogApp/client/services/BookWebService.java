package catalogApp.client.services;

import catalogApp.shared.model.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

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

    @DELETE
    @Path("rest/book")
    void deleteBookFromLib(List<Integer> ids, MethodCallback<Void> callback);

    @PUT
    @Path("rest/book/{id}")
    void updateBook(@PathParam("id")int id, Map<String, String> params, MethodCallback<Void> callback);
}
