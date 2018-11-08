package data;

import java.util.List;

public class FilterOptions {

    private List<String> genre;
    private List<String> artist;
    private List<Integer> popularityMin;
    private List<Integer> popularityMax;
    private List<String> album;

    public List<String> getGenre() {
        return genre;
    }

    public List<String> getArtist() {
        return artist;
    }

    public List<Integer> getPopularityMin() {
        return popularityMin;
    }

    public List<Integer> getPopularityMax() {
        return popularityMax;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public void setArtist(List<String> artist) {
        this.artist = artist;
    }

    public void setPopularityMin(List<Integer> popularityMin) {
        this.popularityMin = popularityMin;
    }

    public void setPopularityMax(List<Integer> popularityMax) {
        this.popularityMax = popularityMax;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

}
