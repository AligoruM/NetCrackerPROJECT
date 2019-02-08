package catalogApp.server.dao.mapper;


import catalogApp.server.dao.constants.Types;
import catalogApp.shared.model.Song;
import catalogApp.shared.model.SongGenre;
import catalogApp.shared.model.Type;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

import static catalogApp.server.dao.constants.Tables.*;

public class SongMapper implements RowMapper<Song> {
    private static Type songType = new Type(Types.SONG, "Song");
    private static Type genreType = new Type(Types.SONG_GENRE, "SongGenre");

    @Override
    public Song mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
        Song song = new Song();
        SongGenre genre = new SongGenre();

        genre.setType(genreType);
        genre.setId(rs.getInt(ID_GENRE_ALIAS));
        genre.setName(rs.getString(NAME_GENRE_ALIAS));

        song.setName(rs.getString(NAME_OBJ_ALIAS));
        song.setId(rs.getInt(ID_OBJ_ALIAS));
        song.setArchived(rs.getBoolean(ARCHIVED_OBJ_ALIAS));
        song.setDuration(rs.getInt(DURATION_ALIAS));
        song.setComment(rs.getString(COMMENT_OBJ_ALIAS));
        song.setImagePath(rs.getString(IMG_OBJ_ALIAS));
        song.setGenre(genre);
        song.setType(songType);

        return song;
    }
}
