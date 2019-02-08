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
import java.util.List;

import static catalogApp.shared.constants.FileServiceConstants.IMAGE_FIELD;

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
    @Path("/avatar")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Integer uploadAvatar(@FormDataParam(IMAGE_FIELD) InputStream fileInputStream,
                                @FormDataParam(IMAGE_FIELD) FormDataContentDisposition fileMetaData) {
        if(imageService.saveImage(fileInputStream, fileMetaData.getFileName()))
            return 200;
        else return 500;
    }

    @POST
    @Path("/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Integer uploadImage(@FormDataParam(IMAGE_FIELD) InputStream fileInputStream,
                               @FormDataParam(IMAGE_FIELD) FormDataContentDisposition fileMetaData) {
        if (imageService.saveImage(fileInputStream, fileMetaData.getFileName()))
            return 200;
        else return 500;
    }


    public static void setJdbcService(IJdbcService service) {
        jdbcService = service;
    }

    public static void setImageService(IImageService service) {
        imageService = service;
    }
}
