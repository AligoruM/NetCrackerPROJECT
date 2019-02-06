package catalogApp.client.services;

import catalogApp.shared.model.SimpleUser;
import com.gargoylesoftware.htmlunit.javascript.host.xml.FormData;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Request;
import java.io.InputStream;
import java.io.OutputStream;
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

    /*@POST
    @Path("rest/avatar/{id}")
    void uploadAvatar(@FormParam("image")OutputStream stream, MethodCallback<String> callback);*/

}
