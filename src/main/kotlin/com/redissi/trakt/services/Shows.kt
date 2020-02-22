package com.redissi.trakt.services

import com.redissi.trakt.entities.*
import com.redissi.trakt.enums.Extended
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Shows {
    /**
     * Returns the most popular shows. Popularity is calculated using the rating percentage and the number of ratings.
     *
     * @param page Number of page of results to be returned. If `null` defaults to 1.
     * @param limit Number of results to return per page. If `null` defaults to 10.
     */
    @GET("shows/popular")
    suspend fun popular(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Show>?

    /**
     * Returns all shows being watched right now. Shows with the most users are returned first.
     *
     * @param page Number of page of results to be returned. If `null` defaults to 1.
     * @param limit Number of results to return per page. If `null` defaults to 10.
     */
    @GET("shows/trending")
    suspend fun trending(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<TrendingShow>?

    /**
     * Returns a single shows's details.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}")
    suspend fun summary(
        @Path("id") showId: String?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): Show?

    /**
     * Returns all translations for a show, including language and translated values for title and overview.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}/translations")
    suspend fun translations(
        @Path("id") showId: String?
    ): List<Translation>?

    /**
     * Returns a single translation for a show. If the translation does not exist, the returned list will be empty.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param language 2-letter language code (ISO 639-1).
     */
    @GET("shows/{id}/translations/{language}")
    suspend fun translation(
        @Path("id") showId: String?,
        @Path("language") language: String?
    ): List<Translation>?

    /**
     * Returns all top level comments for a show. Most recent comments returned first.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param page Number of page of results to be returned. If `null` defaults to 1.
     * @param limit Number of results to return per page. If `null` defaults to 10.
     */
    @GET("shows/{id}/comments")
    suspend fun comments(
        @Path("id") showId: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Comment>?

    /**
     * **OAuth Required**
     *
     *
     * Returns collection progress for show including details on all seasons and episodes. The `next_episode`
     * will be the next episode the user should collect, if there are no upcoming episodes it will be set to `null`.
     *
     *
     * By default, any hidden seasons will be removed from the response and stats. To include these and adjust the
     * completion stats, set the `hidden` flag to `true`.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param hidden Include any hidden seasons.
     * @param specials Include specials as season 0.
     */
    @GET("shows/{id}/progress/collection")
    suspend fun collectedProgress(
        @Path("id") showId: String?,
        @Query("hidden") hidden: Boolean?,
        @Query("specials") specials: Boolean?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): BaseShow?

    /**
     * **OAuth Required**
     *
     * Returns watched progress for show including details on all seasons and episodes. The `next_episode` will be
     * the next episode the user should watch, if there are no upcoming episodes it will be set to `null`.
     *
     *
     * By default, any hidden seasons will be removed from the response and stats. To include these and adjust the
     * completion stats, set the `hidden` flag to `true`.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     * @param hidden Include any hidden seasons.
     * @param specials Include specials as season 0.
     */
    @GET("shows/{id}/progress/watched")
    suspend fun watchedProgress(
        @Path("id") showId: String?,
        @Query("hidden") hidden: Boolean?,
        @Query("specials") specials: Boolean?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): BaseShow?

    /**
     * Returns all actors, directors, writers, and producers for a show.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}/people")
    suspend fun people(
        @Path("id") showId: String?
    ): Credits?

    /**
     * Returns rating (between 0 and 10) and distribution for a show.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: "game-of-thrones".
     */
    @GET("shows/{id}/ratings")
    suspend fun ratings(
        @Path("id") showId: String?
    ): Ratings?

    /**
     * Returns lots of show stats.
     */
    @GET("shows/{id}/stats")
    suspend fun stats(
        @Path("id") showId: String?
    ): Stats?

    /**
     * Returns related and similar shows.
     */
    @GET("shows/{id}/related")
    suspend fun related(
        @Path("id") showId: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): List<Show>?
}