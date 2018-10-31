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

    public Song[] getTracksFromPlaylist(PlaylistSimplified p) {
        try {
            GetPlaylistsTracksRequest getPlaylistsTracksRequest = api
                .getPlaylistsTracks(p.getId())
                .offset(0)
                .build();

            Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
            PlaylistTrack[] tracks = playlistTrackPaging.getItems();

            Song[] out_tracks = new Song[tracks.length];

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
                    track.getAlbum().getName()
                );
                out_tracks[i] = s;
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

    public List<Song> filterSongs() {
        return null;
    }

}
