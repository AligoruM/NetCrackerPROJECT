package catalogApp.shared.model;

import javax.persistence.*;

public class Song extends BaseObject{

    private int duration;
    private SongGenre genre;

    public Song() {
    }

    public Song(int id, String name, Type type, int duration, SongGenre genre) {
        super(id, name, type);
        this.duration = duration;
        this.genre = genre;
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
}