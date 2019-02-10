package catalogApp.client.services;

import catalogApp.shared.model.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    @Path("rest/object/{type}")
    void deleteBookFromLib(@PathParam("type")String type, List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/book")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateBook(Book newBook, MethodCallback<Book> callback);

    @POST
    @Path("rest/archiveObjects")
    void archiveBooks(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/restoreObjects")
    void restoreBooks(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/mark/{id}")
    void markBook(@PathParam("id")Integer bookId,  Integer mark, MethodCallback<Double> callback);

    @POST
    @Path("/rest/author/{name}")
    void addAuthor(@PathParam("name")String name, MethodCallback<Boolean> callback);
}
