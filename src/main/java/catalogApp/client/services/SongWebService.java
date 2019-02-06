package catalogApp.client.services;

import catalogApp.shared.model.Song;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

public interface SongWebService extends RestService {
    @GET
    @Path("rest/song")
    void getAllSongs(MethodCallback<List<Song>> callback);

    @GET
    @Path("rest/getGenre")
    void getGenreNames(MethodCallback<List<String>> callback);

    @PUT
    @Path("rest/addSong")
    void addSong(List<String> params, MethodCallback<Song> callback);

    @POST
    @Path("rest/addSongsToUserLib")
    void addSongsToUserLib(List<Integer> ids, MethodCallback<List<Song>> callback);

    @GET
    @Path("rest/getUserSongs")
    void getUserSongs(MethodCallback<List<Song>> callback);

    @DELETE
    @Path("rest/song")
    void deleteSongFromLib(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/song")
    void updateSong(Song newSong, MethodCallback<Void> callback);

    @POST
    @Path("rest/archiveSongs")
    void archiveSongs(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/restoreSongs")
    void restoreSongs(List<Integer> ids, MethodCallback<Void> callback);
}