package spotify;

public class Song {

    private final String name;
    private final String[] artists;
    private final String[] genres;
    private final String id;
    private final Integer popularity;
    private final String album;
    private final String uri;

    public Song(String name, String[] artists, String[] genres, String id, Integer popularity, String album, String uri) {
        this.name = name;
        this.artists = artists;
        this.genres = genres;
        this.id = id;
        this.popularity = popularity;
        this.album = album;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String[] getArtists() {
        return artists;
    }

    public String[] getGenres() {
        return genres;
    }

    public String getID() {
        return id;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public String getAlbum() {
        return album;
    }

    public String getURI() {
        return uri;
    }

}
