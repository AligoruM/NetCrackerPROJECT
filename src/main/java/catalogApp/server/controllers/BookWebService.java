package catalogApp.server.controllers;


import catalogApp.server.service.IJdbcService;
import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Service
@Controller
@Path("/")
public class BookWebService {

    private static IJdbcService jdbcService;

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
    public List<Book> addBooksToUserLib(List<Integer> ids) {
        return jdbcService.addBooksToLibrary(ids);
    }

    @POST
    @Path("/getUserBooks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getUserBookLib() {
        return jdbcService.getLibBooks();
    }


    @DELETE
    @Path("/book")
    public void deleteBooksFromUserLibs(List<Integer> ids){
        jdbcService.deleteBooksFromLibrary(ids);
    }

    @PUT
    @Path("/book")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateBook(Book newBook){
        jdbcService.updateBook(newBook);
    }

    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }
}
