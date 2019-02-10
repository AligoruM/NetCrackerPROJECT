package catalogApp.client.services;

import catalogApp.shared.model.SimpleUser;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

public interface UserWebService extends RestService {
    @GET
    @Path("rest/user")
    @Consumes(MediaType.APPLICATION_JSON)
    void getSimpleUser(MethodCallback<SimpleUser> callback);

    @GET
    @Path("rest/allUsers")
    @Consumes(MediaType.APPLICATION_JSON)
    void getAllUsers(MethodCallback<List<SimpleUser>> callback);

    @POST
    @Path("rest/UserProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateUser(SimpleUser simpleUser, MethodCallback<SimpleUser> callback);

    @GET
    @Path("rest/avatar/{id}")
    void getAvatarUrl(@PathParam("id") int id, MethodCallback<String> callback);

    @POST
    @Path("rest/updPass/")
    void changePassword(String password, MethodCallback<Boolean> callback);

    @POST
    @Path("rest/archiveObjects")
    void banUsers(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/restoreObjects")
    void enableUsers(List<Integer> ids, MethodCallback<Void> callback);

    @GET
    @Path("rest/roles")
    void getRoles(MethodCallback<List<String>> callback);

    @POST
    @Path("rest/user")
    @Consumes(MediaType.APPLICATION_JSON)
    void createUser(Map<String, String> map, MethodCallback<SimpleUser> callback);
}
