package com.redissi.trakt

import com.redissi.trakt.entities.Movie
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull

interface TestMovie {

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