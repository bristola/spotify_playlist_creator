import com.wrapper.spotify.SpotifyApi;
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
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SpotifyUser {

    private String clientId;
    private String clientSecret;
    private String userID;
    private String accessToken;
    private final SpotifyApi api;

    // Change this for new auth method. https://github.com/thelinmichael/spotify-web-api-java/blob/master/examples/authorization/authorization_code/AuthorizationCodeExample.java
    public SpotifyUser(String clientId, String clientSecret, String userID, String accessToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.userID = userID;
        this.accessToken = accessToken;
        this.api = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .setClientId(clientId)
            .setClientSecret(clientSecret)
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

    public List<Song> getTracksFromPlaylist(PlaylistSimplified p) {
        try {
            GetPlaylistsTracksRequest getPlaylistsTracksRequest = api
                .getPlaylistsTracks(p.getId())
                .offset(0)
                .build();

            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
            PlaylistTrack[] tracks = playlistTrackPaging.getItems();

            List<Song> out_tracks = new ArrayList<Song>();

            for (int i = 0; i < tracks.length; i++) {
                Track track = tracks[i].getTrack();
                ArtistSimplified[] a_simp = track.getArtists();
                String[] artists = new String[a_simp.length];
                for (int j = 0; j < artists.length; j++) {
                    artists[j] = a_simp[j].getName();
                }
                Song s = new Song(
                    track.getName(),
                    artists,
                    getGenres(a_simp),
                    track.getId(),
                    track.getPopularity(),
                    track.getAlbum().getName(),
                    track.getUri()
                );
                out_tracks.add(s);
            }

            return out_tracks;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;

    }

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
        String[] genres_out = new String[genres.size()];
        genres.toArray(genres_out);
        return genres_out;
    }

    public List<Song> filterSongs(List<Song> songs, String genre, String artist, Integer popularityMin, Integer popularityMax, String album) {

        List<Song> playlist = new ArrayList<Song>();

        for (Song current : songs) {
            if (genre != null && !Arrays.asList(current.getGenres()).contains(genre))
                continue;
            if (artist != null && !Arrays.asList(current.getArtists()).contains(artist))
                continue;
            if (popularityMin != null && current.getPopularity() < popularityMin)
                continue;
            if (popularityMax != null && current.getPopularity() > popularityMax)
                continue;
            if (album != null && !current.getAlbum().trim().equals(album.trim()))
                continue;
            playlist.add(current);
        }

        return playlist;
    }

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
