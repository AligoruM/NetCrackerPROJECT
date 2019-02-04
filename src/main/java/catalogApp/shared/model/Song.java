package catalogApp.shared.model;

import catalogApp.server.dao.constants.Types;

import java.util.Objects;

public class Song extends BaseObject {

    private int duration;
    private SongGenre genre;

    public Song() {
    }

    public Song(int id, String name) {
        super(id, name, new Type(Types.SONG, "Song"));
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public SongGenre getGenre() {
        return genre;
    }

    public void setGenre(SongGenre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Song{" + "duration=" + duration +
                ", genre=" + genre +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Song song = (Song) o;
        return duration == song.duration &&
                Objects.equals(genre, song.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duration, genre);
    }
}