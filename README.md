[![Build Status](https://travis-ci.com/bristola/spotify_playlist_creator.svg?branch=master)](https://travis-ci.com/bristola/spotify_playlist_creator)

# Spotify Playlist Creator

## By Austin Bristol

## About

This project is a website which allows the user to sign in to their spotify
account and do various actions. Namely, the user can select a playlist they
would like to add songs from. Then, they select the types of genres, artists,
albums, and popularity you want the songs to be. Then, it will take all the
songs from the playlist that meet the specified criteria and adds them to a user
specified playlist. This allows users to rapidly add many songs to a playlist by
filtering out songs they don't want. When manually done, it would take a very
long time. But, this operation is instantaneous.

## Technology

For this project, a few different technologies were used:

- [Spring Boot](https://github.com/spring-projects/spring-boot):

    Used to create a website in java.

- [Spotify Web API](https://github.com/thelinmichael/spotify-web-api-java):

    Used to connect to Spotify and make requests.

## How to run

1. ```git clone git@github.com:bristola/spotify_playlist_creator.git```

1. ```cd spotify_playlist_creator```

1. Create file called *spotify_settings.txt* in the following format:

    ```
    Client ID: <Your Client ID>
    Client Secret: <Your Client Secret>
    ```

1. ```gradle build```

1. ```java -jar build/libs/gs-spring-boot-0.1.0.jar```

1. Open <http://localhost:8080/> in a web browser
