/**
    JUnit test suite for application
**/
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import spotify.*;
import data.*;

public class AppTest {

    @Test public void test1() {
        assert true;
    }

    @Test public void filterTest1() {
        String[] a1 = null;
        String[] a2 = null;
        String[] a3 = null;
        String[] a4 = null;

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

        SpotifyUser su = new SpotifyUser("","","");
        List<Song> result = su.filterSongs(songs, fo);

        if (result.contains(s1) && result.contains(s2) && !result.contains(s3) && !result.contains(s4))
            assert true;
        else
            assert false;

    }

}
