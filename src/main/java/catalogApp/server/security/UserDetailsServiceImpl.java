package catalogApp.server.security;

import catalogApp.server.dao.AuthDAO;
import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@Service
@Controller
@Path("/")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static AuthDAO authDAO;

    public UserDetailsServiceImpl() {
    }

    @Autowired
    public UserDetailsServiceImpl(AuthDAO authDAO) {
        UserDetailsServiceImpl.authDAO = authDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = authDAO.getUser(username);
            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles);
        } catch (EmptyResultDataAccessException ex){
            throw new UsernameNotFoundException("User not found");
        }
    }

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authDAO.getSimpleUser(authentication.getName());
    }


}
