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
