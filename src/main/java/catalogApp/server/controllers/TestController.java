package catalogApp.server.controllers;


import catalogApp.server.config.SpringConfig;
import catalogApp.server.dao.author.IAuthorDAO;
import catalogApp.server.dao.book.IBookDAO;
import catalogApp.server.dao.song.ISongDAO;
import catalogApp.server.dao.type.ITypeDAO;
import catalogApp.shared.model.Author;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;
import catalogApp.shared.model.Type;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/test")
public class TestController {
    private static AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    private IBookDAO bookDAO = (IBookDAO) context.getBean("bookDAO");
    private ITypeDAO typeDAO = (ITypeDAO) context.getBean("typeDAO");
    private IAuthorDAO authorDAO = (IAuthorDAO) context.getBean("authorDAO");
    private ISongDAO songDAO = (ISongDAO) context.getBean("songDAO");


    @POST
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        //System.out.println(result);
        /*if (result.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
        return bookDAO.findAllBooks();
    }

    @POST
    @Path("/addBook")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(List params){
        String name = (String) params.get(0);
        String author = (String) params.get(1);
        Author authorResult = authorDAO.getAuthorByName(author);
        if (authorResult == null)
            authorResult = new Author(author);
        Type bookType = typeDAO.getTypeById(1);
        bookDAO.addNewBook(name, bookType, authorResult);
    }

    @POST
    @Path("/getAuthor")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAuthorsNames() {
        return authorDAO.getAllAuthorsNames();
    }

    @POST
    @Path("/getSong")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getAllSongs(){
        return songDAO.findAllSongs();
    }
}
