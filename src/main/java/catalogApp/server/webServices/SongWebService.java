package catalogApp.server.webServices;

import catalogApp.server.service.IJdbcService;
import catalogApp.server.service.JdbcService;
import catalogApp.shared.exception.ItemAlreadyExistException;
import catalogApp.shared.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Controller
@Path("/")
public class SongWebService {

    private static IJdbcService jdbcService;

    @GET
    @Path("/song")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getAllSongs() {
        return jdbcService.getAllSong();
    }

    @PUT
    @Path("/addSong")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Song addSong(List params){
        String name = (String) params.get(0);
        String genre = (String) params.get(1);
        String duration = (String) params.get(2);
        Song song;
        try {
            song = jdbcService.addSong(name, genre, duration);
        } catch (ItemAlreadyExistException e) {
            return null;
        }
        return song;
    }

    @GET
    @Path("/getGenre")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getGenreNames(){
        return  jdbcService.getAllGenresNames();
    }

    @POST
    @Path("/addSongsToUserLib")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Song> addSongsToUserLib(List<Integer> ids){
        return jdbcService.addSongsToLibrary(ids);
    }

    @GET
    @Path("/getUserSongs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getUserSongs(){
        return jdbcService.getLibSongs();
    }


    @POST
    @Path("/song")
    @Consumes(MediaType.APPLICATION_JSON)
    public Song updateSong(Song newSong){
        return jdbcService.updateSong(newSong);
    }



    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }
}
