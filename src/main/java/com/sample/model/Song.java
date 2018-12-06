package com.sample.model;

import java.util.Objects;

public class Song {
    private int id;
    private String name;
    private int duration;
    private String comment;
    private int genreId;
    private int albumId;
    private int typeId;


    public Song(int id, String name, int duration, String comment, int genreId, int albumId, int typeId) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.comment = comment;
        this.genreId = genreId;
        this.albumId = albumId;
        this.typeId = typeId;
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

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id &&
                genreId == song.genreId &&
                albumId == song.albumId &&
                typeId == song.typeId &&
                Objects.equals(name, song.name) &&
                Objects.equals(duration, song.duration) &&
                Objects.equals(comment, song.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, comment, genreId, albumId, typeId);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", comment='" + comment + '\'' +
                ", genreId=" + genreId +
                ", albumId=" + albumId +
                ", typeId=" + typeId +
                '}';
    }
}
