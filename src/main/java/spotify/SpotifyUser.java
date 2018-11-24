package spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.requests.data.artists.GetArtistRequest;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import java.net.URI;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is an object which represents the current Spotify user which is
 * using the application. Must provide an authorization code to gain access to
 * the viewing and editing of playlists. All functions in this class relate to
 * accessing data that only the authonticated author can use.
 */
public class SpotifyUser {

    private String clientId;
    private String clientSecret;
    private String userID;
    private String accessToken;
    private final SpotifyApi api;

    /*
        Constructor which needs the developer client ID and client secret. Also
        needs the code which is returned from the authentication process's link.
        Represents 3rd step of the Spotify auth system.
    */
    public SpotifyUser(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        String rawURI = "http://localhost:8080/playlistCreator";
        URI redirectUri = SpotifyHttpManager.makeUri(rawURI);
        // Build api instance variable object using developer info.
        api = new SpotifyApi.Builder()
            .setClientSecret(clientSecret)
            .setClientId(clientId)
            .setRedirectUri(redirectUri)
            .build();
        AuthorizationCodeRequest authorizationCodeRequest = api.authorizationCode(code.trim())
            .build();
        try {
            // Execute request to gain access to user info using code. Update api with this access.
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            api.setAccessToken(authorizationCodeCredentials.getAccessToken());
            api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = api.getCurrentUsersProfile()
                .build();
            User user = getCurrentUsersProfileRequest.execute();
            userID = user.getId();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error has occured: " + e);
            e.printStackTrace();
        }

    }

    /*
        Gets all public and private playlists from the current user by making a
        request to Spotify.
    */
    public PlaylistSimplified[] getUserPlaylists() {
        try {
            GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = api
                .getListOfUsersPlaylists(userID)
                .offset(0)
                .build();

            // Execute request
            Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();
            // Turn result into an array
            PlaylistSimplified[] playlists = playlistSimplifiedPaging.getItems();

            return playlists;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;

    }

    /*
        Gets an individual playlist object from an ID of a playlist.
    */
    public Playlist getPlaylistByID(String id) {
        try {
            GetPlaylistRequest getPlaylistRequest = api
                .getPlaylist(id)
                .build();
            Playlist playlist = getPlaylistRequest.execute();
            return playlist;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /*
        Gets all tracks from the input playlist, and returns a list of the
        custom Song object type. This has additional fields such as genres for a
        given song.
    */
    public List<Song> getTracksFromPlaylist(Playlist p) {
        try {
            GetPlaylistsTracksRequest getPlaylistsTracksRequest = api
                .getPlaylistsTracks(p.getId())
                .offset(0)
                .build();

            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
            PlaylistTrack[] tracks = playlistTrackPaging.getItems();

            List<Song> outTracks = new ArrayList<Song>();

            for (int i = 0; i < tracks.length; i++) {
                Track track = tracks[i].getTrack();
                ArtistSimplified[] aSimp = track.getArtists();
                String[] artists = new String[aSimp.length];
                for (int j = 0; j < artists.length; j++) {
                    artists[j] = aSimp[j].getName();
                }
                Song s = new Song(
                    track.getName(),
                    artists,
                    getGenres(aSimp),
                    track.getId(),
                    track.getPopularity(),
                    track.getAlbum().getName(),
                    track.getUri()
                );
                outTracks.add(s);
            }

            return outTracks;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;

    }

    /*
        This method takes the list of artists, and then returns a string list
        which contains all genres that those artists have.
    */
    public String[] getGenres(ArtistSimplified[] artists) throws IOException, SpotifyWebApiException {
        Artist[] a = new Artist[artists.length];
        for (int i = 0; i < a.length; i++) {
            String id = artists[i].getId();
            GetArtistRequest getArtistRequest = api.getArtist(id)
                .build();
            Artist artist = getArtistRequest.execute();
            a[i] = artist;
        }
        List<String> genres = new ArrayList<String>();
        for (Artist artist : a) {
            genres.addAll(Arrays.asList(artist.getGenres()));
        }
        String[] genresOut = new String[genres.size()];
        genres.toArray(genresOut);
        return genresOut;
    }

    /*
        Request to add all the songs in the list to the specified playlist.
        Takes all the URIs from the songs and uses thouse to add them to the
        playlistID using the spotify api.
    */
    public void addSongsToPlaylist(String playlistID, List<Song> songs) {
        String[] uris = new String[songs.size()];
        for (int i = 0; i < uris.length; i++) {
            uris[i] = songs.get(i).getURI();
        }
        AddTracksToPlaylistRequest addTracksToPlaylistRequest = api
            .addTracksToPlaylist(playlistID, uris)
            .position(0)
            .build();

        try {
            SnapshotResult snapshotResult = addTracksToPlaylistRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
