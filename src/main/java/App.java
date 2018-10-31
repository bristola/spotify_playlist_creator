/**
    This is the drive class for starting up the program
**/

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class App {

    private SpotifyUtils su;
    private SpotifyUser user;

    public static void main(String[] args) {
        App a = new App();
        a.run();
    }

    public void run() {
        setup();
        List<String> playlists = user.getUserPlaylists();
        for (String playlist : playlists) {
            System.out.println(playlist);
        }
    }

    public void setup() {
        su = new SpotifyUtils();

        try {
            String clientId = su.getClientID();
            String clientSecret = su.getClientSecret();
            String userID = su.getUserID();
            String accessToken = su.getAccessToken(clientId, clientSecret);
            user = new SpotifyUser(clientId, clientSecret, userID, accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
