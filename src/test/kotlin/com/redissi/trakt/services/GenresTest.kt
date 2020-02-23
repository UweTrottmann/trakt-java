package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.entities.Genre
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotBeNullOrEmpty
import org.junit.Test
import java.io.IOException

class GenresTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun `get genres for shows`() = runBlocking {
        val genres = trakt.genres().shows()
        genres.shouldNotBeNull()
        assertGenres(genres)
    }

    @Test
    @Throws(IOException::class)
    fun `get genres for movies`() = runBlocking {
        val genres = trakt.genres().movies()
        genres.shouldNotBeNull()
        assertGenres(genres)
    }

    private fun assertGenres(genres: List<Genre>) {
        for (genre in genres) {
            genre.name.shouldNotBeNullOrEmpty()
            genre.slug.shouldNotBeNullOrEmpty()
        }
    }
}