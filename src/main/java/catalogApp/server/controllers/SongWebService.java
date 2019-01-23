package catalogApp.server.controllers;

import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Controller
@Path("/")
public class SongWebService {

    private static JdbcService jdbcService;

    @POST
    @Path("/song")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getAllSongs() {
        return jdbcService.getAllSong();
    }

    @POST
    @Path("/addSong")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Song addSong(List params){
        String name = (String) params.get(0);
        String genre = (String) params.get(1);
        String duration = (String) params.get(2);
        return jdbcService.addSong(name, genre, duration);
    }

    @POST
    @Path("/getGenre")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getGenreNames(){
        return  jdbcService.getAllGenresNames();
    }

    @POST
    @Path("/addSongsToUserLib")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addSongsToUserLib(List<Integer> ids){
        jdbcService.addSongsToLibrary(ids);
    }

    @POST
    @Path("/getUserSongs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getUserSongs(){
        return jdbcService.getLibSongs();
    }



    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }
}
