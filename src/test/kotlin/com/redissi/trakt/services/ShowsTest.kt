package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestData
import com.redissi.trakt.entities.BaseShow
import com.redissi.trakt.entities.Show
import com.redissi.trakt.enums.Extended
import com.redissi.trakt.enums.Type
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.*
import org.junit.Test
import java.io.IOException

class ShowsTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun `get popular shows`() = runBlocking {
        val shows = trakt.shows().popular(2, null, null)
        shows.shouldNotBeNull()
        shows.size.shouldBeLessOrEqualTo(DEFAULT_PAGE_SIZE)
        for (show in shows) {
            assertShowNotNull(show)
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get trending shows`() = runBlocking {
        val trendingShows = trakt.shows().trending(1, null, null)
        trendingShows.shouldNotBeNull()
        trendingShows.size.shouldBeLessOrEqualTo(DEFAULT_PAGE_SIZE)
        for (trendingShow in trendingShows) {
            trendingShow.watchers.shouldNotBeNull()
            trendingShow.show.shouldNotBeNull()
            assertShowNotNull(trendingShow.show!!)
        }
    }

    private fun assertShowNotNull(show: Show) {
        show.shouldNotBeNull()
        show.title.shouldNotBeNullOrEmpty()
        show.ids.shouldNotBeNull()
        show.ids!!.trakt.shouldNotBeNull()
        show.year.shouldNotBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `get summary for a show from its slug`() = runBlocking {
        val show = trakt.shows().summary(TestData.SHOW_SLUG, Extended.FULL)
        show.shouldNotBeNull()
        assertTestShow(show)
    }

    @Test
    @Throws(IOException::class)
    fun `get summary for a show from its trakt's id`() = runBlocking {
        val show = trakt.shows().summary(TestData.SHOW_TRAKT_ID.toString(), Extended.FULL)
        show.shouldNotBeNull()
        assertTestShow(show)
    }

    private fun assertTestShow(show: Show) {
        show.shouldNotBeNull()
        show.ids.shouldNotBeNull()
        show.title.shouldBeEqualTo(TestData.SHOW_TITLE)
        show.year.shouldBeEqualTo(TestData.SHOW_YEAR)
        show.ids!!.trakt.shouldBeEqualTo(TestData.SHOW_TRAKT_ID)
        show.ids!!.slug.shouldBeEqualTo(TestData.SHOW_SLUG)
        show.ids!!.imdb.shouldBeEqualTo(TestData.SHOW_IMDB_ID)
        show.ids!!.tmdb.shouldBeEqualTo(TestData.SHOW_TMDB_ID)
        show.ids!!.tvdb.shouldBeEqualTo(TestData.SHOW_TVDB_ID)
        show.ids!!.tvrage.shouldBeEqualTo(TestData.SHOW_TVRAGE_ID)
    }

    @Test
    @Throws(IOException::class)
    fun `get translations for a show`() = runBlocking {
        val translations = trakt.shows().translations("breaking-bad")
        translations.shouldNotBeNull()
        for (translation in translations) {
            translation.language.shouldNotBeNullOrEmpty()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get specific translation for a show`() = runBlocking<Unit> {
        val translations = trakt.shows().translation("breaking-bad", "de")
        // we know that Breaking Bad has a German translation, otherwise this test would fail
        translations.shouldNotBeNull()
        translations.shouldHaveSize(1)
        translations[0].language.shouldBeEqualTo("de")
    }

    @Test
    @Throws(IOException::class)
    fun `get comments for a show`() = runBlocking<Unit> {
        val comments = trakt.shows().comments(TestData.SHOW_SLUG, 1, null,null)
        comments.shouldNotBeNull()
        comments.size.shouldBeLessOrEqualTo(DEFAULT_PAGE_SIZE)
    }

    @Test
    @Throws(IOException::class)
    fun `get credits for a show`() = runBlocking {
        val credits = trakt.shows().people(TestData.SHOW_SLUG)
        credits.shouldNotBeNull()
        assertCast(credits, Type.PERSON)
        assertCrew(credits, Type.PERSON)
    }

    @Test
    @Throws(IOException::class)
    fun `get ratings for a show`() = runBlocking {
        val ratings = trakt.shows().ratings(TestData.SHOW_SLUG)
        ratings.shouldNotBeNull()
        assertRatings(ratings)
    }

    @Test
    @Throws(IOException::class)
    fun `get stats for a show`() = runBlocking {
        val stats = trakt.shows().stats(TestData.SHOW_SLUG)
        stats.shouldNotBeNull()
        assertShowStats(stats)
    }

    @Test
    @Throws(IOException::class)
    fun `get collected progress for a show`() = runBlocking {
        val show = trakt.shows().collectedProgress(TestData.SHOW_SLUG, null, null, null)
        show.shouldNotBeNull()
        show.lastCollectedAt.shouldNotBeNull()
        assertProgress(show)
    }

    @Test
    @Throws(IOException::class)
    fun `get watched progress for a show`() = runBlocking {
        val show = trakt.shows().watchedProgress(TestData.SHOW_SLUG, null, null, null)
        show.shouldNotBeNull()
        show.lastWatchedAt.shouldNotBeNull()
        assertProgress(show)
    }

    private fun assertProgress(show: BaseShow) {
        show.aired.shouldNotBeNull().shouldBeGreaterThan(30)
        show.completed.shouldNotBeNull().shouldBeGreaterOrEqualTo(1)
        // Killjoys has 5 aired seasons
        show.seasons.shouldNotBeNull().shouldHaveSize(5)
        val season = show.seasons!![0]
        season.number.shouldNotBeNull().shouldBe(1)
        // all aired
        season.aired.shouldNotBeNull().shouldBe(10)
        // always at least 1 watched
        season.completed.shouldNotBeNull().shouldBeGreaterOrEqualTo(1)
        // episode 1 should always be watched
        season.episodes.shouldNotBeNull()
        val episode = season.episodes!![0]
        episode.number.shouldNotBeNull().shouldBe(1)
        episode.completed.shouldNotBeNull().shouldBeTrue()
    }

    @Test
    @Throws(IOException::class)
    fun `get related shows`() = runBlocking {
        val related = trakt.shows().related(TestData.SHOW_SLUG, 1, null, null)
        related.shouldNotBeNull()
        related.size.shouldBeLessOrEqualTo(DEFAULT_PAGE_SIZE)
        for (show in related) {
            assertShowNotNull(show)
        }
    }
}