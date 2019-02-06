package catalogApp.server.security;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.login.AccountLockedException;
import java.util.HashSet;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails.isEnabled()) {
            if (username.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password,
                        new HashSet<>(userDetails.getAuthorities()));
            } else {
                throw new BadCredentialsException("Bad Credentials");
            }
        }else throw new DisabledException("You are banned");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
