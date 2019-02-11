package catalogApp.server.webServices;


import catalogApp.server.service.IImageService;
import catalogApp.server.service.IJdbcService;
import catalogApp.shared.model.SimpleUser;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static catalogApp.shared.constants.FileServiceConstants.IMAGE_FIELD;
import static catalogApp.shared.constants.UserConstants.*;

@Service
@Controller
@Path("/")
public class UserWebService {

    private static IJdbcService jdbcService;
    private static IImageService imageService;

    @POST
    @Path("/UserProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    public SimpleUser updateUser(SimpleUser simpleUser) {
        jdbcService.updateUser(simpleUser);
        return jdbcService.getSimpleUser();
    }


    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleUser getSimpleUser() {
        return jdbcService.getSimpleUser();
    }

    @GET
    @Path("/allUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SimpleUser> getAllUsers() {
        return jdbcService.getAllUsers();
    }


    @POST
    @Path("/updPass")
    public Boolean changePassword(String password) {
        return jdbcService.changePassword(password);
    }

    @POST
    @Path("/user")
    public SimpleUser createUser(Map<String, String> map) {
        if (map == null || !map.containsKey(USERNAME_FIELD) || !map.containsKey(PASSWORD_FIELD) || !map.containsKey(ROLE_FIELD)) {
            return null;
        } else {
            return jdbcService.addUser(map.get(USERNAME_FIELD), map.get(PASSWORD_FIELD), map.get(ROLE_FIELD));
        }
    }

    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getRoles() {
        return Arrays.asList("ADMIN", "USER");
    }

    @POST
    @Path("/avatar")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public String uploadAvatar(@FormDataParam("image") InputStream fileInputStream,
                               @FormDataParam("image") FormDataContentDisposition fileMetaData) {
        jdbcService.updateAvatar(fileMetaData.getFileName());
        if (imageService.saveImage(fileInputStream, fileMetaData.getFileName()))
            return "200";
        else return "500";
    }

    @GET
    @Path("avatar/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAvatar(@PathParam("id") int id) {
        return jdbcService.getUserAvatarPath(id);
    }


    public void setJdbcService(IJdbcService service) {
        jdbcService = service;
    }

    public void setImageService(IImageService service) {
        imageService = service;
    }
}
