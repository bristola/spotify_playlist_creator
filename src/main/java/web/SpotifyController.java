package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Playlist;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import spotify.*;
import data.*;

/**
 *
 */
@ComponentScan
@Controller
@Scope("session")
public class SpotifyController {

    private SpotifyUser spotifyUser = null;

    @RequestMapping(value = "/playlistCreator")
    public ModelAndView playlistCreator() {
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/playlistCreator", params="code")
    public ModelAndView playlistCreator(@RequestParam("code") String code, Model model){

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

    @RequestMapping(value = "/options")
    public String pickOptions() {
        if (spotifyUser == null)
            return "errorPage";
        return "options";
    }

    @RequestMapping(value = "/addToPlaylist")
    public String addToPlaylist(Model model) {
        if (spotifyUser == null)
            return "errorPage";
        PlaylistSimplified[] playlists = spotifyUser.getUserPlaylists();
        model.addAttribute("playlists", playlists);
        return "playlistCreator";
    }

    @RequestMapping(value = "/addToPlaylist/addSongs", params="playlistID", method=RequestMethod.GET)
    public String addSongs(@RequestParam("playlistID") String playlistID, Model model) {
        if (spotifyUser == null)
            return "errorPage";
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

    @RequestMapping(value="/addToPlaylist/addSongs", params="playlistID", method=RequestMethod.POST)
    public ModelAndView addSongsSubmit(@RequestParam("playlistID") String playlistID, @ModelAttribute FilterOptions filterOptions, Model model) {
        SpotifyUtils su = new SpotifyUtils();
        Playlist p = spotifyUser.getPlaylistByID(playlistID);
        List<Song> songs = spotifyUser.getTracksFromPlaylist(p);
        List<Song> filtered = su.filterSongs(songs, filterOptions);
        spotifyUser.addSongsToPlaylist(filterOptions.getPlaylistAdd(), filtered);
        return new ModelAndView(new RedirectView("/addToPlaylist/success"));
    }

    @RequestMapping(value="/addToPlaylist/success")
    public String success() {
        if (spotifyUser == null)
            return "errorPage";
        return "success";
    }

    @RequestMapping(value="/signout")
    public ModelAndView signout() {
        spotifyUser = null;
        return new ModelAndView(new RedirectView("/"));
    }

}
