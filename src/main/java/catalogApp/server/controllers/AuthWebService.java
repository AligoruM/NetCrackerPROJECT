package catalogApp.server.controllers;


import catalogApp.server.dao.AuthDAO;
import catalogApp.shared.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
/*
@Service
@Controller
@Path("/")
public class AuthWebService {

    private AuthDAO authDAO;

    public AuthWebService() {
    }

    public AuthWebService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authDAO.getSimpleUser(authentication.getName());
    }


}
*/