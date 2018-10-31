import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

public class SpotifyUser {

    private String clientId;
    private String clientSecret;
    private String userID;
    private String accessToken;
    private final SpotifyApi api;

    public SpotifyUser(String clientId, String clientSecret, String userID, String accessToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userID = userID;
        this.accessToken = accessToken;
        this.api = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    }

    public List<String> getUserPlaylists() {
        try {
            GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = api
                .getListOfUsersPlaylists(userID)
                .limit(10)
                .offset(0)
                .build();

            Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();
            PlaylistSimplified[] playlists = playlistSimplifiedPaging.getItems();

            List<String> str_playlists = new ArrayList<String>();

            for (PlaylistSimplified playlist : playlists) {
                str_playlists.add(playlist.getName());
            }

            return str_playlists;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;

    }

}
