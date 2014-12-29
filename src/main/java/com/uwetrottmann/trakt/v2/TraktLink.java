package com.uwetrottmann.trakt.v2;

/**
 * Build website links to trakt entities.
 */
public class TraktLink {

    // TODO Change to https for final release
    private static final String BASE_URL = "http://v2.trakt.tv";

    private static final String URL_MOVIE = BASE_URL + "/movies/";
    private static final String URL_SHOW = BASE_URL + "/shows/";
    private static final String URL_SEASON = BASE_URL + "/seasons/";
    private static final String URL_EPISODE = BASE_URL + "/episodes/";
    private static final String URL_PERSON = BASE_URL + "/people/";
    private static final String URL_COMMENT = BASE_URL + "/comments/";
    private static final String URL_IMDB = BASE_URL + "/search/imdb/";
    private static final String URL_TMDB = BASE_URL + "/search/tmdb/";
    private static final String URL_TVDB = BASE_URL + "/search/tvdb/";
    private static final String URL_TVRAGE = BASE_URL + "/search/tvrage/";

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
     */
    public static String tvrage(int tvrageId) {
        return URL_TVRAGE + tvrageId;
    }

}
