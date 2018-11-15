package data;

import java.util.List;


/**
 * This class is a data object to transfer user input data for a post request.
 * When the user inputs data into the filter options, this object is filled and
 * passed to the controller that handles the request.
 */
public class FilterOptions {

    private String playlistAdd;
    private List<String> genre;
    private List<String> artist;
    private Integer popularityMin;
    private Integer popularityMax;
    private List<String> album;

    public String getPlaylistAdd() {
        return playlistAdd;
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

    public void setPlaylistAdd(String playlistAdd) {
        this.playlistAdd = playlistAdd;
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
