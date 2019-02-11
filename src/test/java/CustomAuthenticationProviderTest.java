//import catalogApp.server.security.CustomAuthenticationProvider;
/*
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
        when(userDetails.isEnabled()).thenReturn(true);

        when(userDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);

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
        when(userDetails.isEnabled()).thenReturn(true);

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
        when(userDetails.isEnabled()).thenReturn(true);

        when(userDetailsService.loadUserByUsername("dfgdfgdf")).thenThrow(new UsernameNotFoundException("User not found"));

        authenticationProvider.authenticate(authentication);

    }

    @Test(expected = DisabledException.class)
    public void testNegativeUserBanned(){
        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        when(authentication.getName()).thenReturn("admin");
        when(authentication.getCredentials()).thenReturn("admin");

        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getPassword()).thenReturn("admin");
        when(userDetails.isEnabled()).thenReturn(false);

        when(userDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);

        authenticationProvider.authenticate(authentication);

    }
}
*/