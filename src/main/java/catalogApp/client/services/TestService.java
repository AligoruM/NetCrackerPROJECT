package catalogApp.client.services;

import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

public interface TestService extends RestService {
    @POST
    @Path("rest/test/book")
    void getAllBooks(MethodCallback<List<Book>> callback);

    @POST
    @Path("rest/test/addBook")
    void addBook(List<String> params, MethodCallback<Void> callback);

    @POST
    @Path("rest/test/getAuthor")
    void getAllAuthor(MethodCallback<List<String>> callback);

    @POST
    @Path("rest/test/getSong")
    void getAllSongs(MethodCallback<List<Song>> callback);
}
