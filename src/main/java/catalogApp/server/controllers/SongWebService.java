package catalogApp.server.controllers;

import catalogApp.server.service.JdbcService;
import catalogApp.shared.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

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

    public void setJdbcService(JdbcService service) {
        jdbcService=service;
    }
}
