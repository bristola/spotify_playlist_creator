package spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import java.net.URI;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import data.FilterOptions;

/**
 * This code is all the spotify functions that are needed for the website
 * which do not require an authentication code. This means that it does not need
 * to connect to a spotify user's account to access information.
 */
public class SpotifyUtils {

    private String uri;

    /*
        Takes in the clientID and clientSecret of the developer and generates
        a uri. This uri is created from the spotify api using these credentials.
        This uri is used to link the user to a spotify page where the user can
        sign in to their Spotify account.
    */
    public String getURI(String clientId, String clientSecret) {

        try {

            SpotifyApi api = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

            String rawURI = "http://localhost:8080/playlistCreator";
            URI redirectUri = SpotifyHttpManager.makeUri(rawURI);
            api = new SpotifyApi.Builder()
                .setClientSecret(clientSecret)
                .setClientId(clientId)
                .setRedirectUri(redirectUri)
                .build();
            AuthorizationCodeUriRequest authorizationCodeUriRequest = api.authorizationCodeUri()
                .state("x4xkmn9pu3j6ukrs8n")
                .scope("playlist-read-private,playlist-modify-public,playlist-modify-private,playlist-read-collaborative")
                .show_dialog(true)
                .build();
            URI uri = authorizationCodeUriRequest.execute();

            return uri.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /*
        Takes a list of songs along with a filter options object. Returns a list
        of all songs which satisfy the filter options.
    */
    public List<Song> filterSongs(List<Song> songs, FilterOptions fo) {

        List<Song> playlist = new ArrayList<Song>();

        for (Song current : songs) {
            List<String> genres = new ArrayList<String>(Arrays.asList(current.getGenres()));
            List<String> artists = new ArrayList<String>(Arrays.asList(current.getArtists()));
            Integer popularity = current.getPopularity();
            String album = current.getAlbum();

            int genreBefore = genres.size();
            int artistBefore = artists.size();

            if (genres != null && fo.getGenre() != null) {
                genres.removeAll(fo.getGenre());
            }
            if (artists != null && fo.getArtist() != null) {
                artists.removeAll(fo.getArtist());
            }

            if ((genres != null && fo.getGenre() != null) && fo.getGenre().size() != 0 && genres.size() == genreBefore) {
                continue;
            }
            if ((artists != null && fo.getArtist() != null) && fo.getArtist().size() != 0 && artists.size() == artistBefore) {
                continue;
            }
            if (fo.getPopularityMin() != null && popularity < fo.getPopularityMin()) {
                continue;
            }
            if (fo.getPopularityMax() != null && popularity > fo.getPopularityMax()) {
                continue;
            }
            if (fo.getAlbum() != null && fo.getAlbum().size() != 0 && !fo.getAlbum().contains(album)) {
                continue;
            }

            playlist.add(current);
        }

        return playlist;
    }

    /*
        Takes the genres from a list of song objects and returns the strings.
    */
    public List<String> getGenres(List<Song> songs) {
        List<String> genres = new ArrayList<String>();
        for (Song song : songs) {
            String[] curs = song.getGenres();
            for (String g : curs) {
                if (!genres.contains(g)) {
                    genres.add(g);
                }
            }
        }
        return genres;
    }

    /*
        Takes the artists from a list of song objects and returns the strings.
    */
    public List<String> getArtists(List<Song> songs) {
        List<String> artists = new ArrayList<String>();
        for (Song song : songs) {
            String[] art = song.getArtists();
            for (String a : art) {
                if (!artists.contains(a)) {
                    artists.add(a);
                }
            }
        }
        return artists;
    }

    /*
        Takes the albums from a list of song objects and returns the strings.
    */
    public List<String> getAlbums(List<Song> songs) {
        List<String> albums = new ArrayList<String>();
        for (Song song : songs) {
            String alb = song.getAlbum();
            if (!albums.contains(alb)) {
                albums.add(alb);
            }
        }
        return albums;
    }

    /*
        Gets the developer client ID from a file.
    */
    public String getClientID() throws IOException {
        File f = new File("spotify_settings.txt");
        Scanner scan = new Scanner(f);
        while (scan.hasNext()) {
            String[] lineArray = scan.nextLine().split(":");
            if (lineArray.length != 2) {
                continue;
            } else if (lineArray[0].toLowerCase().trim().equals("client id")) {
                return lineArray[1].trim();
            }
        }
        return "";
    }

    /*
        Gets the developer client secret from a file.
    */
    public String getClientSecret() throws IOException {
        File f = new File("spotify_settings.txt");
        Scanner scan = new Scanner(f);
        while (scan.hasNext()) {
            String[] lineArray = scan.nextLine().split(":");
            if (lineArray.length != 2) {
                continue;
            } else if (lineArray[0].toLowerCase().trim().equals("client secret")) {
                return lineArray[1].trim();
            }
        }
        return "";
    }

    /*
        Gets the developer user ID from a file.
    */
    public String getUserID() throws IOException {
        File f = new File("spotify_settings.txt");
        Scanner scan = new Scanner(f);
        while (scan.hasNext()) {
            String[] lineArray = scan.nextLine().split(":");
            if (lineArray.length != 2) {
                continue;
            } else if (lineArray[0].toLowerCase().trim().equals("spotify id")) {
                return lineArray[1].trim();
            }
        }
        return "";
    }

}
