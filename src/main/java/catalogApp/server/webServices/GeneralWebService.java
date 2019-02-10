package catalogApp.server.webServices;

import catalogApp.server.dao.constants.Attribute;
import catalogApp.server.service.IImageService;
import catalogApp.server.service.IJdbcService;
import catalogApp.server.service.JdbcService;
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
public class GeneralWebService {
    private static IJdbcService jdbcService;
    private static IImageService imageService;

    @DELETE
    @Path("/object/{type}")
    public void deleteObjectsFromUserLibs(@PathParam("type") String type, List<Integer> ids) {
        switch (type) {
            case "songs":
                jdbcService.deleteObjectFromUserLib(ids, Attribute.LIKED_SONG_ID);
                break;
            case "books":
                jdbcService.deleteObjectFromUserLib(ids, Attribute.LIKED_BOOK_ID);
                break;
            default:
                break;
        }
    }

    @POST
    @Path("/mark/{id}")
    public Double markItem(@PathParam("id")int objectId, Integer newMark){
        System.out.println("id = " + objectId);
        System.out.println("mark = " + newMark);
        return jdbcService.markItem(objectId, newMark);
    }

    @POST
    @Path("/archiveObjects")
    @Consumes(MediaType.APPLICATION_JSON)
    public void archiveSongs(List<Integer> ids) {
        jdbcService.archiveItems(ids);
    }

    @POST
    @Path("/restoreObjects")
    @Consumes(MediaType.APPLICATION_JSON)
    public void restoreSongs(List<Integer> ids) {
        jdbcService.restoreItems(ids);
    }

    @POST
    @Path("/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.TEXT_HTML)
    public String uploadImage(@FormDataParam(IMAGE_FIELD) InputStream fileInputStream,
                              @FormDataParam(IMAGE_FIELD) FormDataContentDisposition fileMetaData) {
        if (imageService.saveImage(fileInputStream, fileMetaData.getFileName())) {
            return "200";
        } else {
            return "500";
        }
    }

    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }

    public void setImageService(IImageService service) {
        imageService = service;
    }

}
