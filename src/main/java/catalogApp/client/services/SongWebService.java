package catalogApp.client.services;

import catalogApp.shared.model.Song;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

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
    @Path("rest/object/{type}")
    void deleteSongFromLib(@PathParam("type")String type, List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/song")
    void updateSong(Song newSong, MethodCallback<Song> callback);

    @POST
    @Path("rest/archiveObjects")
    void archiveSongs(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/restoreObjects")
    void restoreSongs(List<Integer> ids, MethodCallback<Void> callback);

    @POST
    @Path("rest/mark/{id}")
    void markSong(@PathParam("id")Integer songId,  Integer mark, MethodCallback<Float> callback);
}