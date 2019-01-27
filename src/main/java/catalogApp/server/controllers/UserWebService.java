package catalogApp.server.controllers;



import catalogApp.server.service.IJdbcService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    public void setUserProfile(Map<String, String> params){
        jdbcService.updateUserProfile(params);
    }

    public void setJdbcService(IJdbcService service) {
        jdbcService = service;
    }
}
