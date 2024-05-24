/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.entities.UserSlug;

public interface TestData {

    String MOVIE_TITLE = "TRON: Legacy";
    int MOVIE_TRAKT_ID = 12601;
    String MOVIE_SLUG = "tron-legacy-2010";
    String MOVIE_IMDB_ID = "tt1104001";
    int MOVIE_TMDB_ID = 20526;
    int MOVIE_YEAR = 2010;

    String PERSON_NAME = "Jennifer Aniston";
    int PERSON_TRAKT_ID = 9689;
    String PERSON_SLUG = "jennifer-aniston";
    String PERSON_IMDB_ID = "nm0000098";
    int PERSON_TMDB_ID = 4491;

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
