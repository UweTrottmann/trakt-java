package com.redissi.trakt.services

import com.redissi.trakt.entities.SearchResult
import com.redissi.trakt.enums.Extended
import com.redissi.trakt.enums.IdType
import com.redissi.trakt.enums.Type
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Search {
    /**
     * Search all text fields that a media object contains (i.e. title, overview, etc). Results are ordered by the most relevant score.
     *
     * @see [Search - Text Query](http://docs.trakt.apiary.io/.reference/search/text-query/get-text-query-results)
     *
     * @see [Filters](http://docs.trakt.apiary.io/.introduction/filters)
     *
     * @see [Extended](http://docs.trakt.apiary.io/.introduction/extended-info)
     *
     * @see [Pagination](http://docs.trakt.apiary.io/.introduction/pagination)
     */
    @GET("search/{type}")
    suspend fun textQuery(
        @Path("type") type: Type?,
        @Query("query") query: String?,
        @Query("years") years: String?,
        @Query("genres") genres: String?,
        @Query("languages") languages: String?,
        @Query("countries") countries: String?,
        @Query("runtimes") runtimes: String?,
        @Query("ratings") ratings: String?,
        @Query("extended") extended: Extended?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): List<SearchResult>?

    /**
     * @see .textQuery textQuery
     */
    @GET("search/movie")
    suspend fun textQueryMovie(
        @Query("query") query: String?,
        @Query("years") years: String?,
        @Query("genres") genres: String?,
        @Query("languages") languages: String?,
        @Query("countries") countries: String?,
        @Query("runtimes") runtimes: String?,
        @Query("ratings") ratings: String?,
        @Query("certifications") certifications: String?,
        @Query("extended") extended: Extended?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): List<SearchResult>?

    /**
     * @see .textQuery textQuery
     */
    @GET("search/show")
    suspend fun textQueryShow(
        @Query("query") query: String?,
        @Query("years") years: String?,
        @Query("genres") genres: String?,
        @Query("languages") languages: String?,
        @Query("countries") countries: String?,
        @Query("runtimes") runtimes: String?,
        @Query("ratings") ratings: String?,
        @Query("certifications") certifications: String?,
        @Query("networks") networks: String?,
        @Query("status") status: String?,
        @Query("extended") extended: Extended?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): List<SearchResult>?

    /**
     * Lookup items by their Trakt, IMDB, TMDB, TVDB, or TVRage ID.
     *
     * @see [Search - ID Lookup](http://docs.trakt.apiary.io/.reference/search/id-lookup/get-id-lookup-results)
     *
     * @see [Extended](http://docs.trakt.apiary.io/.introduction/extended-info)
     *
     * @see [Pagination](http://docs.trakt.apiary.io/.introduction/pagination)
     */
    @GET("search/{id_type}/{id}")
    suspend fun idLookup(
        @Path(value = "id_type", encoded = true) idType: IdType?,
        @Path(value = "id", encoded = true) id: String?,
        @Query("type") type: Type?,
        @Query("extended") extended: Extended?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): List<SearchResult>?
}