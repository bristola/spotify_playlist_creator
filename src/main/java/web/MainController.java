package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestParam;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import spotify.*;

// TODO: Change the index function to just render a template, but then when a
// user clicks sign up, it will run a different mapping which will redirect to
// the correct uri. This removes unnecessary computations when you load the
// homepage.

@ComponentScan
@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {

        SpotifyUtils su = new SpotifyUtils();
        String uri = "";
        try {
            String clientId = su.getClientID();
            String clientSecret = su.getClientSecret();
            uri = su.getURI(clientId, clientSecret);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("uri", uri);

        return "home";
    }

    @RequestMapping(value = "/playlistCreator")
    public String getIdByValue(@RequestParam("code") String code, Model model){

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
        SpotifyUser spotifyUser = new SpotifyUser(clientId, clientSecret, code);
        PlaylistSimplified[] playlists = spotifyUser.getUserPlaylists();

        model.addAttribute("playlists", playlists);

        return "playlistCreator";
    }

}
