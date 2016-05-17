package com.uwetrottmann.trakt.v2;

import com.uwetrottmann.trakt.v2.entities.Username;

public interface TestData {

    String MOVIE_TITLE = "TRON: Legacy";
    int MOVIE_TRAKT_ID = 12601;
    String MOVIE_SLUG = "tron-legacy-2010";
    String MOVIE_IMDB_ID = "tt1104001";
    int MOVIE_TMDB_ID = 20526;
    int MOVIE_YEAR = 2010;

    String SHOW_TITLE = "Breaking Bad";
    int SHOW_TRAKT_ID = 1388;
    String SHOW_SLUG = "breaking-bad";
    String SHOW_IMDB_ID = "tt0903747";
    int SHOW_TMDB_ID = 1396;
    int SHOW_TVDB_ID = 81189;
    int SHOW_TVRAGE_ID = 18164;
    int SHOW_YEAR = 2008;

    String EPISODE_TITLE = "Pilot";
    int EPISODE_SEASON = 1;
    int EPISODE_NUMBER = 1;
    int EPISODE_TRAKT_ID = 73482;
    int EPISODE_TVDB_ID = 349232;
    String EPISODE_IMDB_ID = "tt0959621";
    int EPISODE_TMDB_ID = 62085;

    String USERNAME_STRING = "sean";
    Username USERNAME = new Username("sean");
    String USER_REAL_NAME = "Sean Rudford";

    String USER_TO_FOLLOW = "aeonmckay";
}
