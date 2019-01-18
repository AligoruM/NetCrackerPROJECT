package catalogApp.server.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Controller
@Path("/")
public class AuthController {
    @POST
    @Path("/user")
    public ResponseEntity<String> user(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
    }
}
