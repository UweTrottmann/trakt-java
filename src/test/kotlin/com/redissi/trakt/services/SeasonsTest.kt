package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestData
import com.redissi.trakt.TestRatings
import com.redissi.trakt.TestShowStats
import com.redissi.trakt.entities.Episode
import com.redissi.trakt.enums.Extended
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.*
import org.junit.Test
import java.io.IOException

class SeasonsTest : BaseTestCase(), TestRatings, TestShowStats {
    @Test
    @Throws(IOException::class)
    fun `get all seasons for a show`() = runBlocking {
        val seasons = trakt.seasons().summary(TestData.SHOW_SLUG, Extended.FULLEPISODES)
        seasons.shouldNotBeNull().shouldHaveSize(6)
        for (season in seasons) {
            season.shouldNotBeNull()
            // must have at least trakt and tvdb id
            season.ids.shouldNotBeNull()
            season.ids!!.trakt.shouldNotBeNull().shouldBePositive()
            if (season.ids!!.tvdb != null) {
                season.ids!!.tvdb.shouldNotBeNull().shouldBePositive()
            }
            season.title.shouldNotBeNull()
            season.network.shouldNotBeNull()
            // seasons start at 0 for specials
            season.number.shouldNotBeNull().shouldBeGreaterOrEqualTo(0)
            season.episodeCount.shouldNotBeNull().shouldBePositive()
            season.airedEpisodes.shouldNotBeNull().shouldBeGreaterOrEqualTo(0)
            season.rating.shouldNotBeNull().shouldBeInRange(0.0..10.0)
            season.votes.shouldNotBeNull().shouldBeGreaterOrEqualTo(0)
            // episode details
            if (season.number == TestData.EPISODE_SEASON) {
                season.episodes.shouldNotBeNull().shouldNotBeEmpty()
                var firstEp: Episode? = null
                for (episode in season.episodes!!) {
                    if (episode.number == TestData.EPISODE_NUMBER) {
                        firstEp = episode
                        break
                    }
                }
                firstEp.shouldNotBeNull()
                firstEp.title.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_TITLE)
                firstEp.season.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_SEASON)
                firstEp.number.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_NUMBER)
                firstEp.ids.shouldNotBeNull()
                firstEp.ids!!.imdb.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_IMDB_ID)
                firstEp.ids!!.tmdb.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_TMDB_ID)
                firstEp.ids!!.tvdb.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_TVDB_ID)
                firstEp.overview.shouldNotBeNullOrEmpty()
            }
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get a single season for a show`() = runBlocking {
        val season = trakt.seasons().season(TestData.SHOW_SLUG, TestData.EPISODE_SEASON, null)
        season.shouldNotBeNull().shouldNotBeEmpty()
        for (episode in season) {
            episode.season.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_SEASON)
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get season comments`() = runBlocking<Unit> {
        val comments = trakt.seasons().comments(TestData.SHOW_SLUG, TestData.EPISODE_SEASON)
        comments.shouldNotBeNull()
        comments.size.shouldBeLessOrEqualTo(DEFAULT_PAGE_SIZE)
    }

    @Test
    @Throws(IOException::class)
    fun `get season ratings`() = runBlocking {
        val ratings = trakt.seasons().ratings(TestData.SHOW_SLUG, TestData.EPISODE_SEASON)
        ratings.shouldNotBeNull()
        assertRatings(ratings)
    }

    @Test
    @Throws(IOException::class)
    fun `get season stats`() = runBlocking {
        val stats = trakt.seasons().stats(TestData.SHOW_SLUG, TestData.EPISODE_SEASON)
        stats.shouldNotBeNull()
        assertShowStats(stats)
    }
}