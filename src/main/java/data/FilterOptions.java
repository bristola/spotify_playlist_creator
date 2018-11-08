package data;

public class FilterOptions {

    private String genre;
    private String artist;
    private Integer popularityMin;
    private Integer popularityMax;
    private String album;

    public String getGenre() {
        return genre;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getPopularityMin() {
        return popularityMin;
    }

    public Integer getPopularityMax() {
        return popularityMax;
    }

    public String getAlbum() {
        return album;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setPopularityMin(Integer popularityMin) {
        this.popularityMin = popularityMin;
    }

    public void setPopularityMax(Integer popularityMax) {
        this.popularityMax = popularityMax;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

}
