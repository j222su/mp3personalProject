package com.example.info1.mediacreate;

public class MyData {
    private String album;
    private String title;
    private String displayName;
    private String track;
    private String directory;
    private String artist;
    private String duration;
    private String year;
    private String albumId;
    private String albumArt;

    public MyData(String album, String title, String displayName, String track, String directory, String artist, String duration, String year, String albumId, String albumArt) {
        this.album = album;
        this.title = title;
        this.displayName = displayName;
        this.track = track;
        this.directory = directory;
        this.artist = artist;
        this.duration = duration;
        this.year = year;
        this.albumId = albumId;
        this.albumArt = albumArt;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }
}
