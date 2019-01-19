package catalogApp.client.services;

import catalogApp.shared.model.Song;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface SongWebService extends RestService {
    @POST
    @Path("rest/song")
    @Produces(MediaType.APPLICATION_JSON)
    void getAllSongs(MethodCallback<List<Song>> callback);
}