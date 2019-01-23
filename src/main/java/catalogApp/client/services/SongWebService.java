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

    @POST
    @Path("rest/getGenre")
    void getGenreNames(MethodCallback<List<String>> callback);

    @POST
    @Path("rest/addSong")
    void addSong(List<String> params, MethodCallback<Song> callback);

    @POST
    @Path("rest/addSongsToUserLib")
    void addSongsToUserLib(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/getUserSongs")
    void getUserSongs(MethodCallback<List<Song>> callback);
}