/**
    JUnit test suite for application
**/
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import spotify.SpotifyUtils;
import spotify.Song;
import data.FilterOptions;

public class AppTest {

    @Test public void filterTest1() {
        String[] a1 = {""};
        String[] a2 = {""};
        String[] a3 = {""};
        String[] a4 = {""};

        String[] g1 = {"rock"};
        String[] g2 = {"soft rock"};
        String[] g3 = {"pop"};
        String[] g4 = {"pop"};

        Song s1 = new Song("Song 1", a1, g1, "", 0, "", "");
        Song s2 = new Song("Song 2", a2, g2, "", 0, "", "");
        Song s3 = new Song("Song 3", a3, g3, "", 0, "", "");
        Song s4 = new Song("Song 4", a4, g4, "", 0, "", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        FilterOptions fo = new FilterOptions();
        fo.setPlaylistAdd("playlist1");

        List<String> genres = new ArrayList<String>();
        genres.add("rock");
        genres.add("soft rock");
        fo.setGenre(genres);

        List<String> artists = null;

        fo.setArtist(artists);

        fo.setPopularityMin(null);

        fo.setPopularityMax(null);

        List<String> albums = null;
        fo.setAlbum(albums);

        SpotifyUtils su = new SpotifyUtils();
        List<Song> result = su.filterSongs(songs, fo);

        if (result.contains(s1) && result.contains(s2) && !result.contains(s3) && !result.contains(s4)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test public void filterTest2() {
        String[] a1 = {"Artist 1"};
        String[] a2 = {"Artist 1"};
        String[] a3 = {"Artist 2"};
        String[] a4 = {"Artist 3"};

        String[] g1 = {""};
        String[] g2 = {""};
        String[] g3 = {""};
        String[] g4 = {""};

        Song s1 = new Song("Song 1", a1, g1, "", 0, "", "");
        Song s2 = new Song("Song 2", a2, g2, "", 0, "", "");
        Song s3 = new Song("Song 3", a3, g3, "", 0, "", "");
        Song s4 = new Song("Song 4", a4, g4, "", 0, "", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        FilterOptions fo = new FilterOptions();
        fo.setPlaylistAdd("playlist1");

        List<String> genres = null;
        fo.setGenre(genres);

        List<String> artists = new ArrayList<String>();
        artists.add("Artist 1");
        artists.add("Artist 2");

        fo.setArtist(artists);

        fo.setPopularityMin(null);

        fo.setPopularityMax(null);

        List<String> albums = null;
        fo.setAlbum(albums);

        SpotifyUtils su = new SpotifyUtils();
        List<Song> result = su.filterSongs(songs, fo);

        if (result.contains(s1) && result.contains(s2) && result.contains(s3) && !result.contains(s4)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test public void filterTest3() {
        String[] a1 = {""};
        String[] a2 = {""};
        String[] a3 = {""};
        String[] a4 = {""};

        String[] g1 = {""};
        String[] g2 = {""};
        String[] g3 = {""};
        String[] g4 = {""};

        Song s1 = new Song("Song 1", a1, g1, "", 10, "", "");
        Song s2 = new Song("Song 2", a2, g2, "", 30, "", "");
        Song s3 = new Song("Song 3", a3, g3, "", 60, "", "");
        Song s4 = new Song("Song 4", a4, g4, "", 90, "", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        FilterOptions fo = new FilterOptions();
        fo.setPlaylistAdd("playlist1");

        List<String> genres = null;
        fo.setGenre(genres);

        List<String> artists = null;

        fo.setArtist(artists);

        fo.setPopularityMin(30);

        fo.setPopularityMax(70);

        List<String> albums = null;
        fo.setAlbum(albums);

        SpotifyUtils su = new SpotifyUtils();
        List<Song> result = su.filterSongs(songs, fo);

        if (!result.contains(s1) && result.contains(s2) && result.contains(s3) && !result.contains(s4)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test public void filterTest4() {
        String[] a1 = {"Artist 1"};
        String[] a2 = {"Artist 1"};
        String[] a3 = {"Artist 2"};
        String[] a4 = {"Artist 3"};

        String[] g1 = {""};
        String[] g2 = {""};
        String[] g3 = {""};
        String[] g4 = {""};

        Song s1 = new Song("Song 1", a1, g1, "", 0, "Album 1", "");
        Song s2 = new Song("Song 2", a2, g2, "", 0, "Album 2", "");
        Song s3 = new Song("Song 3", a3, g3, "", 0, "Album 3", "");
        Song s4 = new Song("Song 4", a4, g4, "", 0, "Album 1", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        FilterOptions fo = new FilterOptions();
        fo.setPlaylistAdd("playlist1");

        List<String> genres = null;
        fo.setGenre(genres);

        List<String> artists = null;

        fo.setArtist(artists);

        fo.setPopularityMin(null);

        fo.setPopularityMax(null);

        List<String> albums = new ArrayList<String>();
        albums.add("Album 1");
        albums.add("Album 3");

        fo.setAlbum(albums);

        SpotifyUtils su = new SpotifyUtils();
        List<Song> result = su.filterSongs(songs, fo);

        if (result.contains(s1) && !result.contains(s2) && result.contains(s3) && result.contains(s4)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test public void filterTest5() {
        String[] a1 = {"Artist 1"};
        String[] a2 = {"Artist 2"};
        String[] a3 = {"Artist 3"};
        String[] a4 = {"Artist 4"};

        String[] g1 = {"rock"};
        String[] g2 = {"rap"};
        String[] g3 = {"pop"};
        String[] g4 = {"blues"};

        Song s1 = new Song("Song 1", a1, g1, "", 0, "", "");
        Song s2 = new Song("Song 2", a2, g2, "", 0, "", "");
        Song s3 = new Song("Song 3", a3, g3, "", 0, "", "");
        Song s4 = new Song("Song 4", a4, g4, "", 0, "", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        FilterOptions fo = new FilterOptions();
        fo.setPlaylistAdd("playlist1");

        List<String> genres = new ArrayList<String>();
        genres.add("blues");
        genres.add("pop");

        fo.setGenre(genres);

        List<String> artists = new ArrayList<String>();
        artists.add("Artist 4");

        fo.setArtist(artists);

        fo.setPopularityMin(null);

        fo.setPopularityMax(null);

        List<String> albums = null;
        fo.setAlbum(albums);

        SpotifyUtils su = new SpotifyUtils();
        List<Song> result = su.filterSongs(songs, fo);

        if (!result.contains(s1) && !result.contains(s2) && !result.contains(s3) && result.contains(s4)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test public void filterTest6() {
        String[] a1 = {"Artist 1"};
        String[] a2 = {"Artist 2"};
        String[] a3 = {"Artist 3"};
        String[] a4 = {"Artist 4"};

        String[] g1 = {"rock"};
        String[] g2 = {"rap"};
        String[] g3 = {"pop"};
        String[] g4 = {"rock"};

        Song s1 = new Song("Song 1", a1, g1, "", 20, "", "");
        Song s2 = new Song("Song 2", a2, g2, "", 40, "", "");
        Song s3 = new Song("Song 3", a3, g3, "", 60, "", "");
        Song s4 = new Song("Song 4", a4, g4, "", 80, "", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        FilterOptions fo = new FilterOptions();
        fo.setPlaylistAdd("playlist1");

        List<String> genres = new ArrayList<String>();
        genres.add("rock");

        fo.setGenre(genres);

        List<String> artists = null;

        fo.setArtist(artists);

        fo.setPopularityMin(25);

        fo.setPopularityMax(null);

        List<String> albums = null;
        fo.setAlbum(albums);

        SpotifyUtils su = new SpotifyUtils();
        List<Song> result = su.filterSongs(songs, fo);

        if (!result.contains(s1) && !result.contains(s2) && !result.contains(s3) && result.contains(s4)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test public void getGenresTest() {
        String[] a1 = {"Artist 1"};
        String[] a2 = {"Artist 2"};
        String[] a3 = {"Artist 3"};
        String[] a4 = {"Artist 4"};

        String[] g1 = {"rock"};
        String[] g2 = {"rap"};
        String[] g3 = {"pop"};
        String[] g4 = {"rock"};

        Song s1 = new Song("Song 1", a1, g1, "1", 20, "Album 1", "");
        Song s2 = new Song("Song 2", a2, g2, "2", 40, "Album 2", "");
        Song s3 = new Song("Song 3", a3, g3, "3", 60, "Album 3", "");
        Song s4 = new Song("Song 4", a4, g4, "4", 80, "Album 4", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        SpotifyUtils su = new SpotifyUtils();
        List<String> result = su.getGenres(songs);

        if (result.contains("rock") && result.contains("rap") && result.contains("pop")) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test public void getArtistsTest() {
        String[] a1 = {"Artist 1"};
        String[] a2 = {"Artist 2"};
        String[] a3 = {"Artist 3"};
        String[] a4 = {"Artist 2"};

        String[] g1 = {"rock"};
        String[] g2 = {"rap"};
        String[] g3 = {"pop"};
        String[] g4 = {"rock"};

        Song s1 = new Song("Song 1", a1, g1, "1", 20, "Album 1", "");
        Song s2 = new Song("Song 2", a2, g2, "2", 40, "Album 2", "");
        Song s3 = new Song("Song 3", a3, g3, "3", 60, "Album 3", "");
        Song s4 = new Song("Song 4", a4, g4, "4", 80, "Album 4", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        SpotifyUtils su = new SpotifyUtils();
        List<String> result = su.getArtists(songs);

        if (result.contains("Artist 1") && result.contains("Artist 2") && result.contains("Artist 3")) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test public void getAlbumsTest() {
        String[] a1 = {"Artist 1"};
        String[] a2 = {"Artist 2"};
        String[] a3 = {"Artist 3"};
        String[] a4 = {"Artist 2"};

        String[] g1 = {"rock"};
        String[] g2 = {"rap"};
        String[] g3 = {"pop"};
        String[] g4 = {"rock"};

        Song s1 = new Song("Song 1", a1, g1, "1", 20, "Album 1", "");
        Song s2 = new Song("Song 2", a2, g2, "2", 40, "Album 2", "");
        Song s3 = new Song("Song 3", a3, g3, "3", 60, "Album 4", "");
        Song s4 = new Song("Song 4", a4, g4, "4", 80, "Album 4", "");

        List<Song> songs = new ArrayList<Song>();
        songs.add(s1);
        songs.add(s2);
        songs.add(s3);
        songs.add(s4);

        SpotifyUtils su = new SpotifyUtils();
        List<String> result = su.getAlbums(songs);

        if (result.contains("Album 1") && result.contains("Album 2") && result.contains("Album 4")) {
            assert true;
        } else {
            assert false;
        }
    }

}
