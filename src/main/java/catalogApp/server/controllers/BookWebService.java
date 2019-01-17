package catalogApp.server.controllers;


import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.Book;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class BookWebService {

    private JdbcService jdbcService = new JdbcService();

    @POST
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return jdbcService.getAllBooks();
    }

    @POST
    @Path("/addBook")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(List params){
        String name = (String) params.get(0);
        String author = (String) params.get(1);
        jdbcService.addBook(name, author);
    }

    @POST
    @Path("/getAuthor")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAuthorsNames() {
        return jdbcService.getAllAuthorsNames();
    }
/*
    @POST
    @Path("/getSong")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getAllSongs(){
        return songDAO.findAllSongs();
    }*/
}
