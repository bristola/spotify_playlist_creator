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
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Playlist;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import spotify.*;

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

    @RequestMapping(value = "/addToPlaylist/addSongs", params="playlistID")
    public String addSongs(@RequestParam("playlistID") String playlistID, Model model) {
        if (spotifyUser == null)
            return "errorPage";
        Playlist p = spotifyUser.getPlaylistByID(playlistID);
        System.out.println(p);
        List<Song> songs = spotifyUser.getTracksFromPlaylist(p);
        model.addAttribute("playlist", p.getName());
        model.addAttribute("songs", songs);
        return "addSongs";
    }

    @RequestMapping(value="/signout")
    public ModelAndView signout() {
        spotifyUser = null;
        return new ModelAndView(new RedirectView("/"));
    }

}
