package com.redissi.trakt

/**
 * Build website links to trakt entities.
 */
object TraktLink {
    private const val URL_MOVIE = Trakt.SITE_URL + "/movies/"
    private const val URL_SHOW = Trakt.SITE_URL + "/shows/"
    private const val URL_SEASON = Trakt.SITE_URL + "/seasons/"
    private const val URL_EPISODE = Trakt.SITE_URL + "/episodes/"
    private const val URL_PERSON = Trakt.SITE_URL + "/people/"
    private const val URL_COMMENT = Trakt.SITE_URL + "/comments/"
    private const val URL_IMDB = Trakt.SITE_URL + "/search/imdb/"
    private const val URL_TMDB = Trakt.SITE_URL + "/search/tmdb/"
    private const val URL_TVDB = Trakt.SITE_URL + "/search/tvdb/"
    private const val URL_TVRAGE = Trakt.SITE_URL + "/search/tvrage/"
    private const val PATH_SEASONS = "/seasons/"
    private const val PATH_EPISODES = "/episodes/"
    /**
     * Creates a direct link to this movie.
     */
    fun movie(idOrSlug: String): String {
        return URL_MOVIE + idOrSlug
    }

    /**
     * Creates a direct link to this show.
     */
    fun show(idOrSlug: String): String {
        return URL_SHOW + idOrSlug
    }

    /**
     * Creates a direct link to this season.
     */
    fun season(id: Int): String {
        return URL_SEASON + id
    }

    /**
     * Creates a direct link to this season.
     */
    fun season(showId: Int, season: Int): String {
        return show(showId.toString()) + PATH_SEASONS + season
    }

    /**
     * Creates a direct link to this episode.
     */
    fun episode(id: Int): String {
        return URL_EPISODE + id
    }

    /**
     * Creates a direct link to this episode.
     */
    fun episode(showId: Int, season: Int, episode: Int): String {
        return show(showId.toString()) + PATH_SEASONS + season + PATH_EPISODES + episode
    }

    /**
     * Creates a direct link to this person.
     */
    fun person(idOrSlug: String): String {
        return URL_PERSON + idOrSlug
    }

    /**
     * Creates a direct link to this comment.
     */
    fun comment(id: Int): String {
        return URL_COMMENT + id
    }

    /**
     * Creates a link to a show, movie or person search for this id.
     */
    fun imdb(imdbId: String): String {
        return URL_IMDB + imdbId
    }

    /**
     * Creates a link to a show or movie search for this id. Keep in mind that TMDb ids are not unique among shows and
     * movies, so a search result page may be displayed.
     */
    fun tmdb(tmdbId: Int): String {
        return URL_TMDB + tmdbId
    }

    /**
     * Creates a link to a show and episode search for this id.
     */
    fun tvdb(tvdbId: Int): String {
        return URL_TVDB + tvdbId
    }

    /**
     * Creates a link to a show search for this id.
     */
    fun tvrage(tvrageId: Int): String {
        return URL_TVRAGE + tvrageId
    }
}