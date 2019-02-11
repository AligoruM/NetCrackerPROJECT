package catalogApp.server.webServices;


import catalogApp.server.service.IJdbcService;
import catalogApp.server.service.JdbcService;
import catalogApp.shared.exception.ItemAlreadyExistException;
import catalogApp.shared.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Controller
@Path("/")
public class BookWebService {

    private static IJdbcService jdbcService;

    @GET
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return jdbcService.getAllBooks();
    }

    @PUT
    @Path("/addBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook(List params) {
        String name = (String) params.get(0);
        String author = (String) params.get(1);
        Book book;
        try {
            book = jdbcService.addBook(name, author);
        } catch (ItemAlreadyExistException e) {
            return null;
        }
        return book;
    }

    @GET
    @Path("/getAuthor")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAuthorsNames() {
        return jdbcService.getAllAuthorsNames();
    }
    @POST
    @Path("/author/{name}")
    public Boolean addAuthor(@PathParam("name") String name){
        return jdbcService.addAuthor(name);
    }

    @POST
    @Path("/addBooksToUserLib")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Book> addBooksToUserLib(List<Integer> ids) {
        return jdbcService.addBooksToLibrary(ids);
    }

    @GET
    @Path("/getUserBooks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getUserBookLib() {
        return jdbcService.getLibBooks();
    }


    @POST
    @Path("/book")
    @Consumes(MediaType.APPLICATION_JSON)
    public Book updateBook(Book newBook){
        return jdbcService.updateBook(newBook);
    }

    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }
}
