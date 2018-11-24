package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.io.IOException;
import spotify.SpotifyUtils;

/**
 * Controller which handles all the requests to the server where the user is not
 * yet signed in.
 */
@ComponentScan
@Controller
public class MainController {

    /*
        Home page. Just returns the HTML.
    */
    @GetMapping("/")
    public String index(Model model) {
        return "home";
    }

    /*
        When the user signs in, this function redirects them to a spotify
        authentication page.
    */
    @RequestMapping(value = "/redirect")
    public ModelAndView redirectSpotify() {

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

        return new ModelAndView(new RedirectView(uri));
    }

}
