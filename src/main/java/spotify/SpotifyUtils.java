package spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import java.net.URI;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import data.*;

public class SpotifyUtils {

    private String uri;

    public String getURI(String clientId, String clientSecret) {

        try {

            SpotifyApi api = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

            URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/playlistCreator");
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

    public List<Song> filterSongs(List<Song> songs, FilterOptions fo) {

        List<Song> playlist = new ArrayList<Song>();

        for (Song current : songs) {
            List<String> genres = new ArrayList<String>(Arrays.asList(current.getGenres()));
            List<String> artists = new ArrayList<String>(Arrays.asList(current.getArtists()));
            Integer popularity = current.getPopularity();
            String album = current.getAlbum();

            int genreBefore = genres.size();
            int artistBefore = artists.size();

            if (genres != null && fo.getGenre() != null)
                genres.removeAll(fo.getGenre());
            if (artists != null && fo.getArtist() != null)
                artists.removeAll(fo.getArtist());

            if ((genres != null && fo.getGenre() != null) && fo.getGenre().size() != 0 && genres.size() == genreBefore)
                continue;
            if ((artists != null && fo.getArtist() != null) && fo.getArtist().size() != 0 && artists.size() == artistBefore)
                continue;
            if (fo.getPopularityMin() != null && popularity < fo.getPopularityMin())
                continue;
            if (fo.getPopularityMax() != null && popularity > fo.getPopularityMax())
                continue;
            if (fo.getAlbum() != null && fo.getAlbum().size() != 0 && !fo.getAlbum().contains(album))
                continue;

            playlist.add(current);
        }

        return playlist;
    }

    public List<String> getGenres(List<Song> songs) {
        List<String> genres = new ArrayList<String>();
        for (Song song : songs) {
            String[] curs = song.getGenres();
            for (String g : curs) {
                if (!genres.contains(g))
                    genres.add(g);
            }
        }
        return genres;
    }

    public List<String> getArtists(List<Song> songs) {
        List<String> artists = new ArrayList<String>();
        for (Song song : songs) {
            String[] art = song.getArtists();
            for (String a : art) {
                if (!artists.contains(a))
                    artists.add(a);
            }
        }
        return artists;
    }

    public List<String> getAlbums(List<Song> songs) {
        List<String> albums = new ArrayList<String>();
        for (Song song : songs) {
            String alb = song.getAlbum();
            if (!albums.contains(alb))
                albums.add(alb);
        }
        return albums;
    }

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
