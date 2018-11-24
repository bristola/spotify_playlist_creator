package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Playlist;
import java.io.IOException;
import java.util.List;
import spotify.SpotifyUser;
import spotify.SpotifyUtils;
import spotify.Song;
import data.FilterOptions;

/**
 * Controller which hadles all the requests after the user is already logged in.
 * Holds a session variable of spotifyUser. So that the user only needs to sign
 * in once and this state object is saved until the session is over or the user
 * logs out.
 */
@ComponentScan
@Controller
@Scope("session")
public class SpotifyController {

    // Instance variable for the current spotifyUser who signed in.
    private SpotifyUser spotifyUser = null;

    /*
        If no parameters are specified, redirect to home page.
    */
    @RequestMapping(value = "/playlistCreator")
    public ModelAndView playlistCreator() {
        return new ModelAndView(new RedirectView("/"));
    }

    /*
        Takes the users code returned from auth process and creates a spotify
        user object by signing in to the api using the code.
    */
    @RequestMapping(value = "/playlistCreator", params = "code")
    public ModelAndView playlistCreator(@RequestParam("code") String code, Model model) {

        SpotifyUtils su = new SpotifyUtils();
        SpotifyUser user = null;
        String clientId = "";
        String clientSecret = "";
        try {
            clientId = su.getClientID();
            clientSecret = su.getClientSecret();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        spotifyUser = new SpotifyUser(clientId, clientSecret, code);

        return new ModelAndView(new RedirectView("/options"));
    }

    /*
        Options page to select action.
    */
    @RequestMapping(value = "/options")
    public String pickOptions() {
        if (spotifyUser == null) {
            return "errorPage";
        }
        return "options";
    }

    /*
        Returns an HTML page with all their playlists the user can add from.
    */
    @RequestMapping(value = "/addToPlaylist")
    public String addToPlaylist(Model model) {
        if (spotifyUser == null) {
            return "errorPage";
        }
        PlaylistSimplified[] playlists = spotifyUser.getUserPlaylists();
        model.addAttribute("playlists", playlists);
        return "playlistCreator";
    }

    /*
        Filter page, where options are set for all available artists, genres,
        etc. can be selected by the user to filter songs by. User selects a
        playlist to add to and the types of songs they want from the selected
        playlist.
    */
    @RequestMapping(value = "/addToPlaylist/addSongs", params = "playlistID", method = RequestMethod.GET)
    public String addSongs(@RequestParam("playlistID") String playlistID, Model model) {
        if (spotifyUser == null) {
            return "errorPage";
        }
        SpotifyUtils su = new SpotifyUtils();
        PlaylistSimplified[] playlists = spotifyUser.getUserPlaylists();
        Playlist p = spotifyUser.getPlaylistByID(playlistID);
        List<Song> songs = spotifyUser.getTracksFromPlaylist(p);
        List<String> genres = su.getGenres(songs);
        List<String> albums = su.getAlbums(songs);
        List<String> artists = su.getArtists(songs);

        model.addAttribute("playlists", playlists);
        model.addAttribute("genres", genres);
        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        model.addAttribute("playlistName", p.getName());
        model.addAttribute("filterOptions", new FilterOptions());
        model.addAttribute("playlistID", playlistID);

        return "addSongs";
    }

    /*
        Post request for adding songs. Takes all the filter options, and applys
        them to the selected playlist. Adds valid songs to a playlist.
    */
    @RequestMapping(value = "/addToPlaylist/addSongs", params = "playlistID", method = RequestMethod.POST)
    public ModelAndView addSongsSubmit(@RequestParam("playlistID") String playlistID, @ModelAttribute FilterOptions filterOptions, Model model) {
        SpotifyUtils su = new SpotifyUtils();
        Playlist p = spotifyUser.getPlaylistByID(playlistID);
        List<Song> songs = spotifyUser.getTracksFromPlaylist(p);
        List<Song> filtered = su.filterSongs(songs, filterOptions);
        spotifyUser.addSongsToPlaylist(filterOptions.getPlaylistAdd(), filtered);
        return new ModelAndView(new RedirectView("/addToPlaylist/success"));
    }

    /*
        Success page after adding songs to spotify.
    */
    @RequestMapping(value = "/addToPlaylist/success")
    public String success() {
        if (spotifyUser == null) {
            return "errorPage";
        }
        return "success";
    }

    /*
        Sets session variable to null and redirects to home page.
    */
    @RequestMapping(value = "/signout")
    public ModelAndView signout() {
        spotifyUser = null;
        return new ModelAndView(new RedirectView("/"));
    }

}
