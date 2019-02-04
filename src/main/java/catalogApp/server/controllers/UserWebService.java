package catalogApp.server.controllers;


import catalogApp.server.service.IJdbcService;
import catalogApp.shared.model.SimpleUser;
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
public class UserWebService {

    private static IJdbcService jdbcService;

    @POST
    @Path("/UserProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(SimpleUser simpleUser){
        jdbcService.updateUser(simpleUser);
    }


    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleUser getSimpleUser() {
        return jdbcService.getSimpleUser();
    }

    @POST
    @Path("/allUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SimpleUser> getAllUsers(){
        return jdbcService.getAllUsers();
    }

    public void setJdbcService(IJdbcService service) {
        jdbcService = service;
    }
}
