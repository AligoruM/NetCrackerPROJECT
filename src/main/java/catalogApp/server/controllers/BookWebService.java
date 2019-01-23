package catalogApp.server.controllers;


import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Controller
@Path("/")
public class BookWebService {

    private static JdbcService jdbcService;

    @POST
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return jdbcService.getAllBooks();
    }

    @POST
    @Path("/addBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook(List params) {
        String name = (String) params.get(0);
        String author = (String) params.get(1);
        return jdbcService.addBook(name, author);
    }

    @POST
    @Path("/getAuthor")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAuthorsNames() {
        return jdbcService.getAllAuthorsNames();
    }

    @POST
    @Path("/addBooksToUserLib")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBooksToUserLib(List<Integer> ids) {
        jdbcService.addBooksToLibrary(ids);
    }

    @POST
    @Path("/getUserBooks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getUserBookLib() {
        return jdbcService.getLibBooks();
    }

    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }
}
