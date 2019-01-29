import catalogApp.server.security.CustomAuthenticationProvider;
import catalogApp.server.security.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomAuthenticationProviderTest {
    UserDetailsServiceImpl userDetailsService = Mockito.mock(UserDetailsServiceImpl.class);

    @Mock
    Authentication authentication;

    @Mock
    UserDetails userDetails;

    @Test
    public void testPositive(){
        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        when(authentication.getName()).thenReturn("admin");
        when(authentication.getCredentials()).thenReturn("admin");

        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getPassword()).thenReturn("admin");

        when(userDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);
        //authenticationProvider.authenticate(authentication);

        //verify(authenticationProvider).authenticate(authentication);

        assertTrue(authenticationProvider.authenticate(authentication).isAuthenticated());

    }

    @Test(expected = BadCredentialsException.class)
    public void testNegativeBadPass(){
        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        when(authentication.getName()).thenReturn("admin");
        when(authentication.getCredentials()).thenReturn("43234");

        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getPassword()).thenReturn("admin");

        when(userDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);
        authenticationProvider.authenticate(authentication);

    }

    @Test(expected = UsernameNotFoundException.class)
    public void testNegativeUserNotFound(){
        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        when(authentication.getName()).thenReturn("dfgdfgdf");
        when(authentication.getCredentials()).thenReturn("admin");

        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getPassword()).thenReturn("admin");

        when(userDetailsService.loadUserByUsername("dfgdfgdf")).thenThrow(new UsernameNotFoundException("User not found"));

        authenticationProvider.authenticate(authentication);

    }
}
