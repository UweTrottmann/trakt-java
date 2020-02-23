package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestData
import com.redissi.trakt.TestResponse
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import java.io.IOException
import java.net.HttpURLConnection

class RecommendationsTest : BaseTestCase(), TestResponse {
    @Test
    @Throws(IOException::class)
    fun `get movie recommendations`() = runBlocking<Unit> {
        val movies = trakt.recommendations().movies(null, null, null)
        movies.shouldNotBeNull().shouldNotBeEmpty()
    }

    @Test
    @Throws(IOException::class)
    fun `get two pages of movie recommendations`() = runBlocking<Unit> {
        val movies1 = trakt.recommendations().movies(1, 5, null)
        movies1.shouldNotBeNull().shouldNotBeEmpty()
        val movies2 = trakt.recommendations().movies(2, 5, null)
        movies2.shouldNotBeNull().shouldNotBeEmpty().shouldNotBeEqualTo(movies1)
    }

    @Test
    @Throws(IOException::class)
    fun `hide a movie recommendation`() = runBlocking<Unit> {
        val response = trakt.recommendations().dismissMovie(TestData.MOVIE_TRAKT_ID.toString())
        assertSuccessfulResponse(response, trakt)
        response.code().shouldBeEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    @Test
    @Throws(IOException::class)
    fun `get show recommendations`() = runBlocking<Unit> {
        val shows =  trakt.recommendations().shows(null, null, null)
        shows.shouldNotBeNull().shouldNotBeEmpty()
    }

    @Test
    @Throws(IOException::class)
    fun `get two pages of show recommendations`() = runBlocking<Unit> {
        val shows1 = trakt.recommendations().shows(1, 5, null)
        shows1.shouldNotBeNull().shouldNotBeEmpty()
        val shows2 = trakt.recommendations().shows(2, 5, null)
        shows2.shouldNotBeNull().shouldNotBeEmpty().shouldNotBeEqualTo(shows1)
    }

    @Test
    @Throws(IOException::class)
    fun `hide a show recommendation`() = runBlocking<Unit> {
        val response = trakt.recommendations().dismissShow(TestData.SHOW_TRAKT_ID.toString())
        assertSuccessfulResponse(response, trakt)
        response.code().shouldBeEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }
}