package catalogApp.server.controllers;


import catalogApp.server.config.SpringConfig;
import catalogApp.server.dao.IBookDAO;
import catalogApp.shared.model.Book;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/test")
public class TestController {
    private static AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    private IBookDAO customerDAO = (IBookDAO) context.getBean("bookDAO");

    @POST
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        List<Book> result = customerDAO.findAllBooks();
        //System.out.println(result);
        /*if (result.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);*/
        return result;
    }
}
