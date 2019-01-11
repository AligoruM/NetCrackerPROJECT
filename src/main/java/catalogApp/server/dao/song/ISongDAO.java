package catalogApp.server.dao.song;

import catalogApp.shared.model.Song;

import java.util.List;

public interface ISongDAO {
    List<Song> findAllSongs();

}
