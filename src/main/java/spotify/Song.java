package spotify;

/**
 * Object which holds data for Songs. One central place that has information on
 * the songs as well as additional information such as genres. This object is
 * used in a lot of different methods.
 */
public class Song {

    private final String name;
    private final String[] artists;
    private final String[] genres;
    private final String id;
    private final Integer popularity;
    private final String album;
    private final String uri;

    public Song(
        String name,
        String[] artists,
        String[] genres,
        String id,
        Integer popularity,
        String album,
        String uri
    ) {
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

    /*
        Function to provide a string version which holds all information from
        the class.
    */
    @Override
    public String toString() {
        String out = name;
        out = out + "\n\t" + id;
        out = out + "\n\t" + uri;
        out = out + "\n\t" + album;
        out = out + "\n\t" + popularity;
        out = out + "\n\tArtists:\n";
        for (String artist : artists) {
            out = out + "\t\t" + artist;
        }
        out = out + "\n\tGenres:\n";
        for (String genre : genres) {
            out = out + "\t\t" + genre;
        }
        return out;
    }

}
