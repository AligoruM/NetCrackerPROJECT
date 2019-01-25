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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomAuthenticationProviderTest {
    UserDetailsServiceImpl userDetailsService = Mockito.mock(UserDetailsServiceImpl.class);

    @Mock
    Authentication authentication;

    @Mock
    UserDetails userDetails;

    @Test
    public void test1(){
        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        when(authentication.getName()).thenReturn("admin");
        when(authentication.getCredentials()).thenReturn("admin");

        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getPassword()).thenReturn("admin");

        when(userDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);
        //authenticationProvider.authenticate(authentication);

        //verify(authenticationProvider).authenticate(authentication);
        try {
            assertTrue(authenticationProvider.authenticate(authentication).isAuthenticated());
        }catch (BadCredentialsException ex){
            fail(ex.getMessage());
        }
    }

    public void test2(){

    }
}
