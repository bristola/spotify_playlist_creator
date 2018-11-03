package web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestParam;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import spotify.*;

@ComponentScan
@RestController
public class MainController {

    @RequestMapping("/")
    public String index() {

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

        return "Welcome to the Spotify Playlist Creator! <a href='"+uri+"'>Login</a>";
    }

    @RequestMapping(value = "/playlistCreator")
    public String getIdByValue(@RequestParam("code") String code){
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
        String output = "<h2>Playlists</h2><ul>";
        PlaylistSimplified[] playlists = spotifyUser.getUserPlaylists();
        for (PlaylistSimplified playlist : playlists) {
            output = output + "<li>" + playlist.getName() + "</li>";
        }
        return output;
    }

}
