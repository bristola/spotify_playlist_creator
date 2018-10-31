import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import com.wrapper.spotify.model_objects.specification.Track;
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

    public PlaylistSimplified[] getUserPlaylists() {
        try {
            GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = api
                .getListOfUsersPlaylists(userID)
                .offset(0)
                .build();

            Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();
            PlaylistSimplified[] playlists = playlistSimplifiedPaging.getItems();

            return playlists;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;

    }

    public Track[] getTracksFromPlaylist(PlaylistSimplified p) {
        try {
            GetPlaylistsTracksRequest getPlaylistsTracksRequest = api
                .getPlaylistsTracks(p.getId())
                .offset(0)
                .build();

            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
            PlaylistTrack[] tracks = playlistTrackPaging.getItems();

            Track[] out_tracks = new Track[tracks.length];

            for (int i = 0; i < tracks.length; i++) {
                out_tracks[i] = tracks[i].getTrack();
            }

            return out_tracks;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;

    }

}
