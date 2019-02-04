package catalogApp.server.controllers;

import catalogApp.server.service.IJdbcService;
import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Service
@Controller
@Path("/")
public class SongWebService {

    private static IJdbcService jdbcService;

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
    public List<Song> addSongsToUserLib(List<Integer> ids){
        return jdbcService.addSongsToLibrary(ids);
    }

    @POST
    @Path("/getUserSongs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getUserSongs(){
        return jdbcService.getLibSongs();
    }

    @DELETE
    @Path("/song")
    public void deleteBooksFromUserLibs(List<Integer> ids){
        jdbcService.deleteSongsFromLibrary(ids);
    }

    @PUT
    @Path("/song/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateSong(@PathParam("id")int id, Map<String, String> params){
        jdbcService.updateSong(id, params);
    }

    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }
}
