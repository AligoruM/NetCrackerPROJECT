package catalogApp.client.services;

import catalogApp.shared.model.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

public interface BookWebService extends RestService {
    @POST
    @Path("rest/book")
    @Produces(MediaType.APPLICATION_JSON)
    void getAllBooks(MethodCallback<List<Book>> callback);

    @POST
    @Path("rest/addBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void addBook(List<String> params, MethodCallback<Book> callback);

    @POST
    @Path("rest/getAuthor")
    @Produces(MediaType.APPLICATION_JSON)
    void getAllAuthor(MethodCallback<List<String>> callback);

    @POST
    @Path("rest/addBooksToUserLib")
    @Consumes(MediaType.APPLICATION_JSON)
    void addBooksToUserLib(List<Integer> ids, MethodCallback<List<Book>> callback);

    @POST
    @Path("rest/getUserBooks")
    @Produces(MediaType.APPLICATION_JSON)
    void getUserBooks(MethodCallback<List<Book>> callback);

    @DELETE
    @Path("rest/book")
    void deleteBookFromLib(List<Integer> ids, MethodCallback<Void> callback);

    @PUT
    @Path("rest/book/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateBook(@PathParam("id")int id, Map<String, String> params, MethodCallback<Void> callback);
}
