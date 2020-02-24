package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestData
import com.redissi.trakt.entities.Movie
import com.redissi.trakt.entities.Person
import com.redissi.trakt.entities.SearchResult
import com.redissi.trakt.entities.Show
import com.redissi.trakt.enums.Extended
import com.redissi.trakt.enums.IdType
import com.redissi.trakt.enums.Type
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBePositive
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

class SearchTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun `query a show`() = runBlocking {
        val results = trakt.search().textQueryShow(
            "House",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            Extended.FULL,
            1,
            DEFAULT_PAGE_SIZE
        )
        results.shouldNotBeNull().shouldNotBeEmpty()
        for (result in results) {
            result.score.shouldNotBeNull()
            result.show.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `query a show with the year`() = runBlocking {
        val results = trakt.search().textQueryShow(
            "Empire",
            "2015",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            Extended.FULL,
            1,
            DEFAULT_PAGE_SIZE
        )

        results.shouldNotBeNull().shouldNotBeEmpty()
        for (result in results) {
            result.score.shouldNotBeNull()
            result.show.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `query a movie`() = runBlocking {
        val results = trakt.search().textQueryMovie(
            "tron",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            Extended.FULL,
            1,
            DEFAULT_PAGE_SIZE
        )

        results.shouldNotBeNull().shouldNotBeEmpty()
        for (result in results) {
            result.score.shouldNotBeNull()
            result.movie.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `query a person`() = runBlocking {
        val results = trakt.search().textQuery(
            Type.PERSON,
            "Bryan Cranston",
            null,
            null,
            null,
            null,
            null,
            null,
            Extended.FULL,
            1,
            DEFAULT_PAGE_SIZE
        )

        results.shouldNotBeNull().shouldNotBeEmpty()
        for (result in results) {
            result.score.shouldNotBeNull()
            result.person.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get ID lookup results`() = runBlocking<Unit> {
        var results: List<SearchResult>? = trakt.search().idLookup(
            IdType.TVDB,
            TestData.SHOW_TVDB_ID.toString(),
            Type.SHOW,
            Extended.FULL,
            1,
            DEFAULT_PAGE_SIZE
        )

        results.shouldNotBeNull().shouldHaveSize(1)

        results = trakt.search().idLookup(
            IdType.TMDB,
            TestData.MOVIE_TMDB_ID.toString(),
            Type.MOVIE,
            null,
            1,
            DEFAULT_PAGE_SIZE
        )

        results.shouldNotBeNull().shouldHaveSize(1)
    }
}