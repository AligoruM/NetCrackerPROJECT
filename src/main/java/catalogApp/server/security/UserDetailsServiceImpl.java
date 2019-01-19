package catalogApp.server.security;

import catalogApp.server.dao.AuthDAO;
import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AuthDAO authDAO;

    public UserDetailsServiceImpl() {
    }
    @Autowired
    public UserDetailsServiceImpl(AuthDAO jdbcService) {
        this.authDAO = jdbcService;
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


}
