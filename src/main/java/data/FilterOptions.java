package data;

import java.util.List;

public class FilterOptions {

    private String playlistID;
    private List<String> genre;
    private List<String> artist;
    private Integer popularityMin;
    private Integer popularityMax;
    private List<String> album;

    public String getPlaylistID() {
        return playlistID;
    }

    public List<String> getGenre() {
        return genre;
    }

    public List<String> getArtist() {
        return artist;
    }

    public Integer getPopularityMin() {
        return popularityMin;
    }

    public Integer getPopularityMax() {
        return popularityMax;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void setPlaylistID(String playlistID) {
        this.playlistID = playlistID;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public void setArtist(List<String> artist) {
        this.artist = artist;
    }

    public void setPopularityMin(Integer popularityMin) {
        this.popularityMin = popularityMin;
    }

    public void setPopularityMax(Integer popularityMax) {
        this.popularityMax = popularityMax;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

}
