package catalogApp.shared.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlbum")
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Artist")
    private String artist;
    @Column(name = "ReleaseDate")
    private Date date;

    public Album() {
    }

    public Album(String name, String artist, Date date) {
        this.name = name;
        this.artist = artist;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Album{" + "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", date=" + date +
                '}';
    }
}
