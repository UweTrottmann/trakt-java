package com.redissi.trakt.services

import com.redissi.trakt.*
import com.redissi.trakt.entities.Movie
import com.redissi.trakt.enums.Extended
import com.redissi.trakt.enums.Type
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.*
import org.junit.Test
import java.io.IOException

class MoviesTest : BaseTestCase(), TestCrew, TestStats, TestCast, TestRatings {
    @Test
    @Throws(IOException::class)
    fun `get popular movies`() = runBlocking {
        val movies = trakt.movies().popular(1, null, null)
        movies.shouldNotBeNull()
        movies.size.shouldBeLessOrEqualTo(DEFAULT_PAGE_SIZE)
        for (movie in movies) {
            assertMovieNotNull(movie)
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get trending movies`() = runBlocking {
        val trendingMovies = trakt.movies().trending(1, null, null)
        trendingMovies.shouldNotBeNull()
        trendingMovies.size.`should be less or equal to`(DEFAULT_PAGE_SIZE)
        for (trendingMovie in trendingMovies) {
            trendingMovie.movie.shouldNotBeNull()
            trendingMovie.watchers.shouldNotBeNull()
            assertMovieNotNull(trendingMovie.movie!!)
        }
    }

    private fun assertMovieNotNull(movie: Movie) {
        movie.title.shouldNotBeNullOrEmpty()
        movie.ids.shouldNotBeNull()
        movie.ids!!.trakt.shouldNotBeNull()
        movie.year.shouldNotBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `get summary for a movie from its slug`() = runBlocking {
        val movie = trakt.movies().summary(TestData.MOVIE_SLUG, Extended.FULL)
        movie.shouldNotBeNull()
        assertTestMovie(movie)
    }

    @Test
    @Throws(IOException::class)
    fun `get summary for a movie from its trakt's id`() = runBlocking {
        val movie = trakt.movies().summary(TestData.MOVIE_TRAKT_ID.toString(), Extended.FULL)
        movie.shouldNotBeNull()
        assertTestMovie(movie)
    }

    @Test
    @Throws(IOException::class)
    fun `get translations for a movie`() = runBlocking {
        val translations = trakt.movies().translations("batman-begins-2005")
        translations.shouldNotBeNull()
        for (translation in translations) {
            translation.language.shouldNotBeNullOrEmpty()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get specific translation for a movie`() = runBlocking<Unit> {
        val translations = trakt.movies().translation("batman-begins-2005", "de")
        translations.shouldNotBeNull()
        // we know that Batman Begins has a German translation, otherwise this test would fail
        translations.shouldHaveSize(1)
        translations[0].language.shouldBeEqualTo("de")
    }

    @Test
    @Throws(IOException::class)
    fun `get comments for a movie`() = runBlocking<Unit> {
        val comments = trakt.movies().comments(TestData.MOVIE_SLUG, 1, null, null)
        comments.shouldNotBeNull()
        comments.size.shouldBeLessOrEqualTo(DEFAULT_PAGE_SIZE)
    }

    @Test
    @Throws(IOException::class)
    fun `get credits for a movie`() = runBlocking {
        val credits = trakt.movies().people(TestData.MOVIE_SLUG)
        credits.shouldNotBeNull()
        assertCast(credits, Type.PERSON)
        assertCrew(credits, Type.PERSON)
    }

    @Test
    @Throws(IOException::class)
    fun `get ratings for a movie`() = runBlocking {
        val ratings = trakt.movies().ratings(TestData.MOVIE_SLUG)
        ratings.shouldNotBeNull()
        assertRatings(ratings)
    }

    @Test
    @Throws(IOException::class)
    fun `get stats for a movie`() = runBlocking {
        val stats = trakt.movies().stats(TestData.MOVIE_SLUG)
        stats.shouldNotBeNull()
        assertStats(stats)
    }

    companion object {
        fun assertTestMovie(movie: Movie) {
            movie.shouldNotBeNull()
            movie.ids.shouldNotBeNull()
            movie.title.shouldBeEqualTo(TestData.MOVIE_TITLE)
            movie.year.shouldBeEqualTo(TestData.MOVIE_YEAR)
            movie.ids!!.trakt.shouldBeEqualTo(TestData.MOVIE_TRAKT_ID)
            movie.ids!!.slug.shouldBeEqualTo(TestData.MOVIE_SLUG)
            movie.ids!!.imdb.shouldBeEqualTo(TestData.MOVIE_IMDB_ID)
            movie.ids!!.tmdb.shouldBeEqualTo(TestData.MOVIE_TMDB_ID)
        }
    }
}