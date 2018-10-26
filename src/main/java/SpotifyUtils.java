import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

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

}
