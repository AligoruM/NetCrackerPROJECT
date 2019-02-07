package catalogApp.server.controllers;

import catalogApp.server.service.IJdbcService;
import catalogApp.server.service.JdbcService;
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
        return jdbcService.addSong(name, genre, duration);
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

    @DELETE
    @Path("/song")
    public void deleteBooksFromUserLibs(List<Integer> ids){
        jdbcService.deleteSongsFromLibrary(ids);
    }

    @POST
    @Path("/song")
    @Consumes(MediaType.APPLICATION_JSON)
    public Song updateSong(Song newSong){
        return jdbcService.updateSong(newSong);
    }

    @POST
    @Path("/archiveSongs")
    @Consumes(MediaType.APPLICATION_JSON)
    public void archiveSongs(List<Integer> ids){
        jdbcService.archiveItems(ids);
    }

    @POST
    @Path("/restoreSongs")
    @Consumes(MediaType.APPLICATION_JSON)
    public void restoreSongs(List<Integer> ids){
        jdbcService.restoreItems(ids);
    }

    public void setJdbcService(JdbcService service) {
        jdbcService = service;
    }
}
