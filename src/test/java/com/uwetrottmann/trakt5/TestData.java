package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.entities.UserSlug;

public interface TestData {

    String MOVIE_TITLE = "TRON: Legacy";
    int MOVIE_TRAKT_ID = 12601;
    String MOVIE_SLUG = "tron-legacy-2010";
    String MOVIE_IMDB_ID = "tt1104001";
    int MOVIE_TMDB_ID = 20526;
    int MOVIE_YEAR = 2010;

    // Interstellar
    int MOVIE_WATCHED_TRAKT_ID = 102156;

    String SHOW_TITLE = "Killjoys";
    int SHOW_TRAKT_ID = 77712;
    String SHOW_SLUG = "killjoys";
    String SHOW_IMDB_ID = "tt3952222";
    int SHOW_TMDB_ID = 62413;
    int SHOW_TVDB_ID = 281534;
    int SHOW_TVRAGE_ID = 38299;
    int SHOW_YEAR = 2015;

    String EPISODE_TITLE = "Bangarang";
    int EPISODE_SEASON = 1;
    int EPISODE_NUMBER = 1;
    int EPISODE_TRAKT_ID = 1498291;
    int EPISODE_TVDB_ID = 4913049;
    String EPISODE_IMDB_ID = "tt3977698";
    int EPISODE_TMDB_ID = 1054068;

    String USERNAME_STRING = "sean";
    UserSlug USER_SLUG = new UserSlug("sean");
    String USER_REAL_NAME = "Sean Rudford";

    String USER_TO_FOLLOW = "aeonmckay";
}
