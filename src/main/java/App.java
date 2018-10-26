/**
    This is the drive class for starting up the program
**/

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {

        String clientID = "";
        String clientSecret = "";

        try {
            clientID = getClientID();
            clientSecret = getClientSecret();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpotifyUtils su = new SpotifyUtils();
        String accessToken = su.getAccessToken(clientID, clientSecret);
        System.out.println(accessToken);

    }

    public static String getClientID() throws IOException {
        File f = new File("spotify_settings.txt");
        Scanner scan = new Scanner(f);
        while (scan.hasNext()) {
            String[] lineArray = scan.nextLine().split(":");
            if (lineArray.length != 2) {
                continue;
            } else if (lineArray[0].toLowerCase().trim().equals("client id")) {
                return lineArray[1].trim();
            }
        }
        return "";
    }

    public static String getClientSecret() throws IOException {
        File f = new File("spotify_settings.txt");
        Scanner scan = new Scanner(f);
        while (scan.hasNext()) {
            String[] lineArray = scan.nextLine().split(":");
            if (lineArray.length != 2) {
                continue;
            } else if (lineArray[0].toLowerCase().trim().equals("client secret")) {
                return lineArray[1].trim();
            }
        }
        return "";
    }

}
