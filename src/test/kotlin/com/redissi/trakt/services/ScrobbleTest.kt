package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.entities.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import java.io.IOException

class ScrobbleTest : BaseTestCase() {
    private fun genEpisodeProgress(progress: Double): ScrobbleProgress {
        // Use a different episode than for other tests to avoid conflicts.
        val episode = SyncEpisode(
            ids = EpisodeIds.tvdb(4647887) /* Agents of Shield S01E02 */
        )
        return ScrobbleProgress.create(episode, progress, null, null)
    }

    private fun genMovieProgress(progress: Double): ScrobbleProgress {
        // Use a different movie than for other tests to avoid conflicts.
        val movie = SyncMovie(
            ids = MovieIds.tmdb(603) /* The Matrix */
        )
        return ScrobbleProgress.create(movie, progress, null, null)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun `scrobble an episode`() = runBlocking<Unit> {
        var playbackResponse = trakt.scrobble().startWatching(genEpisodeProgress(20.0))
        playbackResponse.shouldNotBeNull()
        playbackResponse.action.shouldNotBeNull().shouldBeEqualTo("start")

        // Give the server some time to process the request.
        delay(1500)

        playbackResponse = trakt.scrobble().pauseWatching(genEpisodeProgress(50.0))
        playbackResponse.shouldNotBeNull()
        playbackResponse.action.shouldNotBeNull().shouldBeEqualTo("pause")

        // Give the server some time to process the request.
        delay(1500)
        playbackResponse = trakt.scrobble().stopWatching(genEpisodeProgress(75.0))

        // Above 80% progress action is "scrobble", below "pause".
        // Pause to avoid blocking the next test until the episode runtime has passed.
        playbackResponse.shouldNotBeNull()
        playbackResponse.action.shouldNotBeNull().shouldBeEqualTo("pause")
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun `scrobble a movie`() = runBlocking<Unit> {
        var playbackResponse = trakt.scrobble().startWatching(genMovieProgress(20.0))
        playbackResponse.shouldNotBeNull()
        playbackResponse.action.shouldNotBeNull().shouldBeEqualTo("start")
        // Give the server some time to process the request.
        delay(1500)
        playbackResponse = trakt.scrobble().pauseWatching(genMovieProgress(50.0))

        playbackResponse.shouldNotBeNull()
        playbackResponse.action.shouldNotBeNull().shouldBeEqualTo("pause")
        // Give the server some time to process the request.
        delay(1500)
        playbackResponse = trakt.scrobble().stopWatching(genMovieProgress(75.0))

        // Above 80% progress action is "scrobble", below "pause".
        // Pause to avoid blocking the next test until the movie runtime has passed.
        playbackResponse.shouldNotBeNull()
        playbackResponse.action.shouldNotBeNull().shouldBeEqualTo("pause")
    }
}