package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestData
import com.redissi.trakt.TestRatings
import com.redissi.trakt.TestStats
import com.redissi.trakt.enums.Extended
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.*
import org.junit.Test
import java.io.IOException

class EpisodesTest : BaseTestCase(), TestRatings, TestStats {
    @Test
    @Throws(IOException::class)
    fun `get summary for an episode`() = runBlocking<Unit> {
        val episode = trakt.episodes().summary(
            TestData.SHOW_TRAKT_ID.toString(),
            TestData.EPISODE_SEASON,
            TestData.EPISODE_NUMBER,
            Extended.FULL
        )

        episode.shouldNotBeNull()
        episode.title.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_TITLE)
        episode.season.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_SEASON)
        episode.number.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_NUMBER)
        episode.ids.shouldNotBeNull()
        episode.ids!!.imdb.shouldBeEqualTo(TestData.EPISODE_IMDB_ID)
        episode.ids!!.tmdb.shouldBeEqualTo(TestData.EPISODE_TMDB_ID)
        episode.ids!!.tvdb.shouldBeEqualTo(TestData.EPISODE_TVDB_ID)
        episode.runtime.shouldNotBeNull().shouldBeGreaterThan(0)
        episode.commentCount.shouldNotBeNull().shouldBeGreaterOrEqualTo(0)
    }

    @Test
    @Throws(IOException::class)
    fun `get comments for an episode`() = runBlocking<Unit> {
        val comments = trakt.episodes().comments(
            TestData.SHOW_SLUG,
            TestData.EPISODE_SEASON,
            TestData.EPISODE_NUMBER,
            1,
            DEFAULT_PAGE_SIZE,
            null
        )
        comments.shouldNotBeNull()
        comments.size.shouldBeLessOrEqualTo(DEFAULT_PAGE_SIZE)
    }

    @Test
    @Throws(IOException::class)
    fun `get ratings for an episode`() = runBlocking {
        val ratings = trakt.episodes().ratings(TestData.SHOW_SLUG, TestData.EPISODE_SEASON, TestData.EPISODE_NUMBER)
        ratings.shouldNotBeNull()
        assertRatings(ratings)
    }

    @Test
    @Throws(IOException::class)
    fun `get stats for an episode`() = runBlocking {
        val stats = trakt.episodes().stats(TestData.SHOW_SLUG, TestData.EPISODE_SEASON, TestData.EPISODE_NUMBER)
        stats.shouldNotBeNull()
        assertStats(stats)
    }
}