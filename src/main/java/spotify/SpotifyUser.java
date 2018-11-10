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
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import java.net.URI;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import data.*;

public class SpotifyUser {

    private String clientId;
    private String clientSecret;
    private String userID;
    private String accessToken;
    private final SpotifyApi api;

    public SpotifyUser(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/playlistCreator");
        api = new SpotifyApi.Builder()
            .setClientSecret(clientSecret)
            .setClientId(clientId)
            .setRedirectUri(redirectUri)
            .build();
        AuthorizationCodeRequest authorizationCodeRequest = api.authorizationCode(code.trim())
            .build();
        try {
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

    public List<Song> getTracksFromPlaylist(Playlist p) {
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

    public List<Song> filterSongs(List<Song> songs, FilterOptions fo) {

        List<Song> playlist = new ArrayList<Song>();

        int genreLen = fo.getGenre().size();
        int artistLen = fo.getArtist().size();

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

            if ((genres != null || fo.getGenre() != null) && fo.getGenre().size() != 0 && genres.size() == genreBefore)
                continue;
            if ((artists != null || fo.getArtist() != null) && fo.getArtist().size() != 0 && artists.size() == artistBefore)
                continue;
            if (fo.getPopularityMin() != null && popularity < fo.getPopularityMin())
                continue;
            if (fo.getPopularityMax() != null && popularity > fo.getPopularityMax())
                continue;
            if (fo.getAlbum().size() != 0 && !fo.getAlbum().contains(album))
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
