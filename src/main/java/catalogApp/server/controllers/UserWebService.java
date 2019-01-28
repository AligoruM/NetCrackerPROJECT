package catalogApp.server.controllers;



import catalogApp.server.service.IJdbcService;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

@Service
@Controller
@Path("/")
public class UserWebService {

    private static IJdbcService jdbcService;

    @POST
    @Path("/UserProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, String> getUserProfile(){
        return jdbcService.getUserProfile();
    }

    @PUT
    @Path("/UserProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUserProfile(Map<String, String> params){
        jdbcService.updateUserProfile(params);
    }


    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleUser getUser() {
        return jdbcService.getUser();
    }

    @POST
    @Path("/allUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers(){
        return jdbcService.getAllUsers();
    }

    public void setJdbcService(IJdbcService service) {
        jdbcService = service;
    }
}
