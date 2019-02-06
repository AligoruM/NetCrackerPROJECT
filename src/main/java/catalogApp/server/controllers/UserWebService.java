package catalogApp.server.controllers;


import catalogApp.server.service.IImageService;
import catalogApp.server.service.IJdbcService;
import catalogApp.shared.model.SimpleUser;
import org.fusesource.restygwt.client.MethodCallback;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@Service
@Controller
@Path("/")
public class UserWebService {

    private static IJdbcService jdbcService;
    private static IImageService imageService;

    @POST
    @Path("/UserProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    public SimpleUser updateUser(SimpleUser simpleUser){
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
    public List<SimpleUser> getAllUsers(){
        return jdbcService.getAllUsers();
    }


    @POST
    @Path("/avatar")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public void uploadAvatar(@FormDataParam("image") InputStream fileInputStream,
                             @FormDataParam("image") FormDataContentDisposition fileMetaData) {
        imageService.saveImage(fileInputStream, fileMetaData.getFileName());
        jdbcService.updateAvatar(fileMetaData.getFileName());
    }

    @GET
    @Path("avatar/{id}")
    public String getAvatar(@PathParam("id") int id){
        return jdbcService.getUserAvatarPath(id);
    }


    public void setJdbcService(IJdbcService service) {
        jdbcService = service;
    }

    public static void setImageService(IImageService service) {
        imageService = service;
    }
}
