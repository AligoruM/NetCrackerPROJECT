package catalogApp.server.controllers;


import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.Book;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Service
@RestController
@Path("/")
public class BookWebService {

    private static JdbcService jdbcService;

    @POST
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return jdbcService.getAllBooks();
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public void addBook(List params){
        String name = (String) params.get(0);
        String author = (String) params.get(1);
        jdbcService.addBook(name, author);
    }

    @POST
    @Path("/getAuthor")
    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAuthorsNames() {
        return jdbcService.getAllAuthorsNames();
    }

    public void setJdbcService(JdbcService service) {
        jdbcService=service;
    }
}
