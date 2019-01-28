package catalogApp.server.security;

import catalogApp.server.dao.UserDAO;
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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static UserDAO userDAO;

    public UserDetailsServiceImpl() {
    }

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO) {
        UserDetailsServiceImpl.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDAO.getUser(username);
            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
            if(user.getRole().equals("ADMIN"))
                roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles);
        } catch (EmptyResultDataAccessException ex) {
            throw new UsernameNotFoundException("User not found");
        }
    }

}
