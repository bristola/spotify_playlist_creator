public class Song {

    private String name;
    private String[] artists;
    private String[] genres;
    private String id;

    public Song(String name, String[] artists, String[] genres, String id) {
        this.name = name;
        this.artists = artists;
        this.genres = genres;
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String[] artists) {
        this.artists = artists;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setID(String id) {
        this.id = id;
    }

}
