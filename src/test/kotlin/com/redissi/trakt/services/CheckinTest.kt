package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestData
import com.redissi.trakt.TestMovie
import com.redissi.trakt.TestResponse
import com.redissi.trakt.entities.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.time.OffsetDateTime

class CheckinTest : BaseTestCase(), TestResponse, TestMovie {

    @Test
    @Throws(IOException::class)
    fun `check into an episode`() = runBlocking {
        val checkin = buildEpisodeCheckin()
        val response = trakt.checkin().checkin(checkin).body()
        response.shouldNotBeNull()
        assertEpisodeCheckin(response)
    }

    @Test
    @Throws(IOException::class)
    fun `check into an episode without ids`() = runBlocking {
        val checkin = buildEpisodeCheckinWithoutIds()
        val response = trakt.checkin().checkin(checkin).body()
        response.shouldNotBeNull()
        assertEpisodeCheckin(response)
    }

    private fun assertEpisodeCheckin(response: EpisodeCheckinResponse) {
        // episode should be over in less than an hour
        response.watchedAt.shouldNotBeNull().isBefore(OffsetDateTime.now().plusHours(1)).shouldBeTrue()
        response.episode.shouldNotBeNull()
        response.episode!!.ids.shouldNotBeNull()
        response.episode!!.ids!!.trakt.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_TRAKT_ID)
        response.episode!!.ids!!.tvdb.shouldNotBeNull().shouldBeEqualTo(TestData.EPISODE_TVDB_ID)
        response.show.shouldNotBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `check into a movie`() = runBlocking {
        val checkin = buildMovieCheckin()
        val response = trakt.checkin().checkin(checkin).body()
        response.shouldNotBeNull()
        // movie should be over in less than 3 hours
        response.watchedAt.shouldNotBeNull().isBefore(OffsetDateTime.now().plusHours(3)).shouldBeTrue()
        response.movie.shouldNotBeNull()
        assertTestMovie(response.movie!!)
    }

    private fun buildMovieCheckin(): MovieCheckin {
        val shareSettings = ShareSettings(facebook = true)
        return MovieCheckin.Builder(
            SyncMovie(ids = MovieIds.trakt(TestData.MOVIE_TRAKT_ID)),
            APP_VERSION,
            APP_DATE
        )
            .message("This is a toasty movie!")
            .sharing(shareSettings)
            .build()
    }

    @Test
    @Throws(IOException::class)
    fun `check into an episode when a checkin is already in progress`() = runBlocking<Unit> {
        val checkin = trakt.checkin()
        val episodeCheckin = buildEpisodeCheckin()
        checkin.checkin(episodeCheckin)
        val movieCheckin = buildMovieCheckin()
        val responseBlocked = checkin.checkin(movieCheckin)
        if (responseBlocked.code() == 401) {
            Assertions.fail<Any>(
                "Authorization required, supply a valid OAuth access token: "
                        + responseBlocked.code() + " " + responseBlocked.message()
            )
        }
        if (responseBlocked.code() != 409) {
            Assertions.fail<Any>("Check-in was not blocked")
        }
        val checkinError = trakt.checkForCheckinError(responseBlocked)
        // episode check in should block until episode duration has passed
        checkinError.shouldNotBeNull()
        checkinError.expiresAt.shouldNotBeNull().isBefore(OffsetDateTime.now().plusHours(1)).shouldBeTrue()
    }

    @Test
    fun `delete active checkins`() = runBlocking<Unit> { // tries to delete a check in even if none active
        val response: Response<*> = trakt.checkin().deleteActiveCheckin()
        assertSuccessfulResponse(response, trakt)
        response.code().shouldBeEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    @After
    fun clearCheckin() = runBlocking<Unit> {
        BaseTestCase.trakt.checkin().deleteActiveCheckin()
    }

    companion object {
        private const val APP_VERSION = "trakt-kotlin-1"
        private const val APP_DATE = "2020-02-23"
        private fun buildEpisodeCheckin(): EpisodeCheckin {
            return EpisodeCheckin.Builder(
                SyncEpisode(ids = EpisodeIds.tvdb(TestData.EPISODE_TVDB_ID)),
                APP_VERSION,
                APP_DATE
            )
                .message("This is a toasty episode!")
                .build()
        }

        private fun buildEpisodeCheckinWithoutIds(): EpisodeCheckin {
            val show = Show(title = TestData.SHOW_TITLE)
            return EpisodeCheckin.Builder(
                SyncEpisode(season = TestData.EPISODE_SEASON, number = TestData.EPISODE_NUMBER),
                APP_VERSION,
                APP_DATE
            )
                .show(show)
                .message("This is a toasty episode!")
                .build()
        }
    }
}