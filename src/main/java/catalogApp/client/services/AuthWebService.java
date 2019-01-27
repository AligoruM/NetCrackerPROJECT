package catalogApp.client.services;

import catalogApp.shared.model.SimpleUser;
import catalogApp.shared.model.User;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

public interface AuthWebService extends RestService {
    @POST
    @Path("rest/user")
    void getSimpleUser(MethodCallback<SimpleUser> callback);

    @POST
    @Path("rest/allUsers")
    void getAllUsers(MethodCallback<List<User>> callback);

    @POST
    @Path("rest/UserProfile")
    void getUserDescription(MethodCallback<Map<String, String>> callback);

    @PUT
    @Path("rest/UserProfile")
    void setUserDescription(Map<String,String> params, MethodCallback<Void> callback);
}
