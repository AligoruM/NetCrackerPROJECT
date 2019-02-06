package catalogApp.server.dao.mapper;


import catalogApp.server.dao.constants.Types;
import catalogApp.shared.model.Song;
import catalogApp.shared.model.SongGenre;
import catalogApp.shared.model.Type;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SongMapper implements RowMapper<Song> {
    private static Type songType = new Type(Types.SONG, "Song");
    private static Type genreType = new Type(Types.SONG_GENRE, "SongGenre");

    @Override
    public Song mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
        Song song = new Song();
        SongGenre genre = new SongGenre();

        genre.setId(rs.getInt("idGenre"));
        genre.setName(rs.getString("genreName"));
        genre.setType(genreType);

        song.setId(rs.getInt("idObject"));
        song.setName(rs.getString("name"));
        song.setArchived(rs.getBoolean("archived"));
        song.setDuration(rs.getInt("duration"));
        song.setComment(rs.getString("comment"));
        song.setImagePath(rs.getString("image"));
        song.setGenre(genre);
        song.setType(songType);

        return song;
    }
}
