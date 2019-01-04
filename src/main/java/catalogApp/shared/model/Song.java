package catalogApp.shared.model;

import javax.persistence.*;

@Entity
@Table(name = "Song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSong")
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Duration")
    private int duration;
    @Column(name = "Comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "SongGenres_idGenre", referencedColumnName = "idGenre")
    private SongGenre genre;
    @ManyToOne
    @JoinColumn(name = "Album_idAlbum", referencedColumnName = "idAlbum")
    private Album album;
    @ManyToOne
    @JoinColumn(name = "Types_idTypes", referencedColumnName = "idTypes")
    private Type type;

    public Song() {
    }

    public Song(String name, int duration, String comment, SongGenre genre, Album album, Type type) {
        this.name = name;
        this.duration = duration;
        this.comment = comment;
        this.genre = genre;
        this.album = album;
        this.type = type;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SongGenre getGenre() {
        return genre;
    }

    public void setGenre(SongGenre genre) {
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
