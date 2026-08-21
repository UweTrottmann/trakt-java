/*
 * Copyright 2014 Uwe Trottmann
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
 * Build website links to Trakt entities.
 */
public class TraktLink {

    private static final String URL_MOVIES = TraktV2.SITE_URL + "/movies/";
    private static final String URL_SHOWS = TraktV2.SITE_URL + "/shows/";
    private static final String URL_SEASONS = TraktV2.SITE_URL + "/seasons/";
    private static final String URL_EPISODES = TraktV2.SITE_URL + "/episodes/";
    private static final String URL_PEOPLE = TraktV2.SITE_URL + "/people/";
    private static final String URL_COMMENTS = TraktV2.SITE_URL + "/comments/";
    private static final String URL_IMDB = TraktV2.SITE_URL + "/search/imdb/";
    private static final String URL_TMDB = TraktV2.SITE_URL + "/search/tmdb/";
    private static final String URL_TVDB = TraktV2.SITE_URL + "/search/tvdb/";

    private static final String PATH_SEASONS = "/seasons/";
    private static final String PATH_EPISODES = "/episodes/";

    /**
     * Creates a direct link to this movie.
     */
    public static String movie(String idOrSlug) {
        return URL_MOVIES + idOrSlug;
    }

    /**
     * Creates a direct link to this show.
     */
    public static String show(String idOrSlug) {
        return URL_SHOWS + idOrSlug;
    }

    /**
     * Creates a direct link to this season.
     *
     * @deprecated No longer supported. Use {@link #season(int, int)} instead.
     */
    @Deprecated
    public static String season(int id) {
        return URL_SEASONS + id;
    }

    /**
     * Creates a direct link to this season.
     */
    public static String season(int showId, int season) {
        return show(String.valueOf(showId)) + PATH_SEASONS + season;
    }

    /**
     * Creates a direct link to this episode.
     *
     * @deprecated No longer supported. Use {@link #episode(int, int, int)} instead.
     */
    @Deprecated
    public static String episode(int id) {
        return URL_EPISODES + id;
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
        return URL_PEOPLE + idOrSlug;
    }

    /**
     * Creates a direct link to this comment.
     */
    public static String comment(int id) {
        return URL_COMMENTS + id;
    }

    /**
     * Creates a link to a show, movie or person search for this id.
     *
     * @deprecated No longer works with their new web app. Instead, look up the item with
     * {@link com.uwetrottmann.trakt5.services.Search#idLookup} and use the returned Trakt id or slug to build the
     * website link.
     */
    @Deprecated
    public static String imdb(String imdbId) {
        return URL_IMDB + imdbId;
    }

    /**
     * Creates a link to a show or movie search for this id. Keep in mind that TMDb ids are not unique among shows and
     * movies, so a search result page may be displayed.
     *
     * @deprecated No longer works with their new web app. Instead, look up the item with
     * {@link com.uwetrottmann.trakt5.services.Search#idLookup} and use the returned Trakt id or slug to build the
     * website link.
     */
    @Deprecated
    public static String tmdb(int tmdbId) {
        return URL_TMDB + tmdbId;
    }

    /**
     * Creates a link to a show and episode search for this id.
     *
     * @deprecated No longer works with their new web app. Instead, look up the item with
     * {@link com.uwetrottmann.trakt5.services.Search#idLookup} and use the returned Trakt id or slug to build the
     * website link.
     */
    @Deprecated
    public static String tvdb(int tvdbId) {
        return URL_TVDB + tvdbId;
    }

}
