public class Song {

    private final String name;
    private final String[] artists;
    private final String[] genres;
    private final String id;
    private final Integer popularity;
    private final String album;

    public Song(String name, String[] artists, String[] genres, String id, Integer popularity, String album) {
        this.name = name;
        this.artists = artists;
        this.genres = genres;
        this.id = id;
        this.popularity = popularity;
        this.album = album;
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

}
