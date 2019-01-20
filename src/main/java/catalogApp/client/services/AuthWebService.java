package catalogApp.client.services;

import catalogApp.shared.model.SimpleUser;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

public interface AuthWebService extends RestService {
    @POST
    @Path("rest/user")
    void getSimpleUser(MethodCallback<SimpleUser> callback);
}
