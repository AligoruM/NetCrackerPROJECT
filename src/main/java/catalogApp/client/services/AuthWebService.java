package catalogApp.client.services;

import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.User;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

public interface AuthWebService extends RestService {
    @POST
    @Path("rest/user")
    void getSimpleUser(MethodCallback<SimpleUser> callback);
    @POST
    @Path("rest/allUsers")
    void getAllUsers(MethodCallback<List<User>> callback);
}
