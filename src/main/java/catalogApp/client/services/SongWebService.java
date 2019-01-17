package catalogApp.client.services;

import catalogApp.shared.model.Song;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

public interface SongWebService extends RestService {
    @POST
    @Path("rest/song")
    void getAllSongs(MethodCallback<List<Song>> callback);
}
