import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class SpotifyUtils {

    public String getAccessToken(String clientId, String clientSecret) {

        try {

            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

            ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

            ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            return clientCredentials.getAccessToken();

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
