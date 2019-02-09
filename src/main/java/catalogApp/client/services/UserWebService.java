package catalogApp.client.services;

import catalogApp.shared.model.SimpleUser;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

public interface UserWebService extends RestService {
    @GET
    @Path("rest/user")
    void getSimpleUser(MethodCallback<SimpleUser> callback);

    @GET
    @Path("rest/allUsers")
    void getAllUsers(MethodCallback<List<SimpleUser>> callback);

    @POST
    @Path("rest/UserProfile")
    void updateUser(SimpleUser simpleUser, MethodCallback<SimpleUser> callback);

    @GET
    @Path("rest/avatar/{id}")
    void getAvatarUrl(@PathParam("id") int id, MethodCallback<String> callback);

    @POST
    @Path("rest/updPass/")
    void changePassword(String password, MethodCallback<Boolean> callback);
}
