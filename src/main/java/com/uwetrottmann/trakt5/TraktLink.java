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

/**
 * Build website links to trakt entities.
 */
public class TraktLink {

    private static final String URL_MOVIE = TraktV2.SITE_URL + "/movies/";
    private static final String URL_SHOW = TraktV2.SITE_URL + "/shows/";
    private static final String URL_SEASON = TraktV2.SITE_URL + "/seasons/";
    private static final String URL_EPISODE = TraktV2.SITE_URL + "/episodes/";
    private static final String URL_PERSON = TraktV2.SITE_URL + "/people/";
    private static final String URL_COMMENT = TraktV2.SITE_URL + "/comments/";
    private static final String URL_IMDB = TraktV2.SITE_URL + "/search/imdb/";
    private static final String URL_TMDB = TraktV2.SITE_URL + "/search/tmdb/";
    private static final String URL_TVDB = TraktV2.SITE_URL + "/search/tvdb/";
    /**
     * @deprecated This appears to be no longer supported.
     */
    @Deprecated
    private static final String URL_TVRAGE = TraktV2.SITE_URL + "/search/tvrage/";

    private static final String PATH_SEASONS = "/seasons/";
    private static final String PATH_EPISODES = "/episodes/";

    /**
     * Creates a direct link to this movie.
     */
    public static String movie(String idOrSlug) {
        return URL_MOVIE + idOrSlug;
    }

    /**
     * Creates a direct link to this show.
     */
    public static String show(String idOrSlug) {
        return URL_SHOW + idOrSlug;
    }

    /**
     * Creates a direct link to this season.
     */
    public static String season(int id) {
        return URL_SEASON + id;
    }

    /**
     * Creates a direct link to this season.
     */
    public static String season(int showId, int season) {
        return show(String.valueOf(showId)) + PATH_SEASONS + season;
    }

    /**
     * Creates a direct link to this episode.
     */
    public static String episode(int id) {
        return URL_EPISODE + id;
    }

    /**
     * Creates a direct link to this episode.
     */
    public static String episode(int showId, int season, int episode) {
        return show(String.valueOf(showId)) + PATH_SEASONS + season + PATH_EPISODES + episode;
    }

    /**
     * Creates a direct link to this person.
     */
    public static String person(String idOrSlug) {
        return URL_PERSON + idOrSlug;
    }

    /**
     * Creates a direct link to this comment.
     */
    public static String comment(int id) {
        return URL_COMMENT + id;
    }

    /**
     * Creates a link to a show, movie or person search for this id.
     */
    public static String imdb(String imdbId) {
        return URL_IMDB + imdbId;
    }

    /**
     * Creates a link to a show or movie search for this id. Keep in mind that TMDb ids are not unique among shows and
     * movies, so a search result page may be displayed.
     */
    public static String tmdb(int tmdbId) {
        return URL_TMDB + tmdbId;
    }

    /**
     * Creates a link to a show and episode search for this id.
     */
    public static String tvdb(int tvdbId) {
        return URL_TVDB + tvdbId;
    }

    /**
     * Creates a link to a show search for this id.
     *
     * @deprecated This appears to be no longer supported.
     */
    @Deprecated
    public static String tvrage(int tvrageId) {
        return URL_TVRAGE + tvrageId;
    }

}
