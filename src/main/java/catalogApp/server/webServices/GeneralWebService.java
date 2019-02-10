package catalogApp.server.webServices;

import catalogApp.server.dao.constants.Attribute;
import catalogApp.server.service.IJdbcService;
import catalogApp.server.service.JdbcService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Controller
@Path("/")
public class GeneralWebService {
    private static IJdbcService jdbcService;

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

    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }

}
