package catalogApp.client.services;

import catalogApp.shared.model.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

public interface BookWebService extends RestService {
    @GET
    @Path("rest/book")
    @Produces(MediaType.APPLICATION_JSON)
    void getAllBooks(MethodCallback<List<Book>> callback);

    @PUT
    @Path("rest/addBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void addBook(List<String> params, MethodCallback<Book> callback);

    @GET
    @Path("rest/getAuthor")
    @Produces(MediaType.APPLICATION_JSON)
    void getAllAuthor(MethodCallback<List<String>> callback);

    @POST
    @Path("rest/addBooksToUserLib")
    @Consumes(MediaType.APPLICATION_JSON)
    void addBooksToUserLib(List<Integer> ids, MethodCallback<List<Book>> callback);

    @GET
    @Path("rest/getUserBooks")
    @Produces(MediaType.APPLICATION_JSON)
    void getUserBooks(MethodCallback<List<Book>> callback);

    @DELETE
    @Path("rest/book")
    void deleteBookFromLib(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/book")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateBook(Book newBook, MethodCallback<Void> callback);

    @POST
    @Path("rest/archiveBooks")
    void archiveBooks(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/restoreBooks")
    void restoreBooks(List<Integer> ids, MethodCallback<Void> callback);
}
