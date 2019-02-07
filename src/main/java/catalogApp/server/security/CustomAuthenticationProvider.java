package catalogApp.server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);


    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails.isEnabled()) {
            if (username.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword())) {
                logger.info("Successful login. User:" + userDetails.getUsername());
                return new UsernamePasswordAuthenticationToken(username, password,
                        new HashSet<>(userDetails.getAuthorities()));
            } else {
                logger.info("Failed login. User:" + userDetails.getUsername() + ". Incorrect password.");
                throw new BadCredentialsException("Bad Credentials");
            }
        }else {
            logger.info("Failed login. User:" + userDetails.getUsername() + ". User is banned");
            throw new DisabledException("You are banned");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
