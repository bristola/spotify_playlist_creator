/**
    This is the drive class for starting up the program
**/

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import com.wrapper.spotify.model_objects.specification.Track;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class App {

    private SpotifyUtils su;
    private SpotifyUser user;

    public static void main(String[] args) {
        App a = new App();
        a.run();
    }

    public void run() {
        setup();
        PlaylistSimplified[] playlists = user.getUserPlaylists();
        for (PlaylistSimplified playlist : playlists) {
            System.out.println("Tracks on "+playlist.getName());
            Track[] tracks = user.getTracksFromPlaylist(playlist);
            for (Track track : tracks) {
                System.out.println("\t"+track.getPreviewUrl());
            }
        }

    }

    public void setup() {
        su = new SpotifyUtils();

        try {
            String clientId = su.getClientID();
            String clientSecret = su.getClientSecret();
            String userID = su.getUserID();
            String accessToken = su.getAccessToken(clientId, clientSecret);
            user = new SpotifyUser(clientId, clientSecret, userID, accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
