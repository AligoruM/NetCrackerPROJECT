package catalogApp.server.security;

import catalogApp.server.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
            Set<String> roles = userDAO.getUserRoles(user.getId());
            Set<GrantedAuthority> authorities = new HashSet<>();
            roles.forEach(x -> authorities.add(new SimpleGrantedAuthority("ROLE_" + x)));
            user.setRoles(authorities);
            return user;
        } catch (EmptyResultDataAccessException ex) {
            throw new UsernameNotFoundException("User not found");
        }
    }

}
