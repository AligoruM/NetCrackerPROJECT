package catalogApp.server.controllers;

import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.Book;
import catalogApp.shared.model.Song;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class SongWebService {
    private JdbcService jdbcService = new JdbcService();

    @POST
    @Path("/song")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getAllSongs() {
        return jdbcService.getAllSong();
    }
}
