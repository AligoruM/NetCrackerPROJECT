package catalogApp.client.services;

import catalogApp.shared.model.SimpleUser;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

public interface UserWebService extends RestService {
    @POST
    @Path("rest/user")
    void getSimpleUser(MethodCallback<SimpleUser> callback);

    @POST
    @Path("rest/allUsers")
    void getAllUsers(MethodCallback<List<SimpleUser>> callback);

    @POST
    @Path("rest/UserProfile")
    void updateUser(SimpleUser simpleUser, MethodCallback<Void> callback);
}
