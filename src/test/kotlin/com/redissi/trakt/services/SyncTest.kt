package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestData
import com.redissi.trakt.TestRatedEntities
import com.redissi.trakt.TestSync
import com.redissi.trakt.entities.*
import com.redissi.trakt.enums.Rating
import com.redissi.trakt.enums.RatingsFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import java.io.IOException
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.fail

class SyncTest : BaseTestCase(), TestSync, TestRatedEntities {
    @Test
    @Throws(IOException::class)
    fun `get last activities`() = runBlocking {
        val lastActivities = trakt.sync().lastActivities()
        lastActivities.shouldNotBeNull()
        lastActivities.all.shouldNotBeNull()
        assertLastActivityMore(lastActivities.movies)
        assertLastActivityMore(lastActivities.episodes)
        assertLastActivity(lastActivities.shows)
        assertLastActivity(lastActivities.seasons)
        assertListsLastActivity(lastActivities.lists)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun `get playback progress`() = runBlocking {
        // Make sure there are paused entries.
        val agentsOfShield = 4420028 /* S01E01 */
        val episode = SyncEpisode(
            ids = EpisodeIds.tvdb(agentsOfShield)
        )
        val episodeProgress = ScrobbleProgress.create(episode, 25.0, null, null)
        val episodeResponse = trakt.scrobble().pauseWatching(episodeProgress)

        episodeResponse.shouldNotBeNull()
        episodeResponse.action.shouldNotBeNull().shouldBeEqualTo("pause")

        // Give the server some time to process the request.
        delay(1500)

        val interstellar = 157336
        val movie = SyncMovie(ids = MovieIds.tmdb(157336))
        val movieProgress = ScrobbleProgress.create(movie, 32.0, null, null)
        val movieResponse = trakt.scrobble().pauseWatching(movieProgress)

        movieResponse.shouldNotBeNull()
        movieResponse.action.shouldNotBeNull().shouldBeEqualTo("pause")
        // Give the server some time to process the request.
        delay(1500)

        val playbacks = trakt.sync().getPlayback(10)

        playbacks.shouldNotBeNull()

        var foundEpisode = false
        var foundMovie = false
        for (playback in playbacks) {
            playback.type.shouldNotBeNull()
            if (playback.episode?.ids?.tvdb == agentsOfShield) {
                foundEpisode = true
                playback.pausedAt.shouldNotBeNull()
                playback.progress.shouldNotBeNull().shouldBeEqualTo(25.0)
            }
            if (playback.movie?.ids?.tmdb == interstellar) {
                foundMovie = true
                playback.pausedAt.shouldNotBeNull()
                playback.progress.shouldNotBeNull().shouldBeEqualTo(32.0)
            }
        }
        if (!foundEpisode) fail("Agents of Shield episode not paused.")
        if (!foundMovie) fail("Interstellar movie not paused.")
    }

    private fun assertLastActivityMore(activityMore: LastActivityMore?) {
        assertLastActivity(activityMore)
        activityMore.shouldNotBeNull()
        activityMore.pausedAt.shouldNotBeNull()
        activityMore.collectedAt.shouldNotBeNull()
        activityMore.watchedAt.shouldNotBeNull()
    }

    private fun assertLastActivity(activity: LastActivity?) {
        activity.shouldNotBeNull()
        activity.commentedAt.shouldNotBeNull()
        activity.ratedAt.shouldNotBeNull()
        activity.watchlistedAt.shouldNotBeNull()
    }

    private fun assertListsLastActivity(activity: ListsLastActivity?) {
        activity.shouldNotBeNull()
        activity.commentedAt.shouldNotBeNull()
        activity.likedAt.shouldNotBeNull()
        activity.updatedAt.shouldNotBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `get collected movies`() = runBlocking {
        val movies = trakt.sync().collectionMovies(null)
        movies.shouldNotBeNull()
        assertSyncMovies(movies, "collection")
    }

    @Test
    @Throws(IOException::class)
    fun `get collected shows`() = runBlocking {
        val shows = trakt.sync().collectionShows(null)
        shows.shouldNotBeNull()
        assertSyncShows(shows, "collection")
    }

    @Test
    @Throws(IOException::class)
    fun `add a movie to the collection`() {
        val movie = SyncMovie(ids = buildMovieIds())
        val items = SyncItems(movies = listOf(movie))
        addItemsToCollection(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add a show to the collection`() {
        val show = SyncShow(ids = buildShowIds())
        val items = SyncItems(shows = listOf(show))
        addItemsToCollection(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add a season to the collection`() {
        // season
        val season = SyncSeason(number = 1)
        // show
        val show = SyncShow(
            ids = ShowIds.slug("community"),
            seasons = listOf(season)
        )
        val items = SyncItems(shows = listOf(show))
        addItemsToCollection(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add an episode to the collection`() {
        // Fri Jul 14 2017 02:40:00 UTC
        val collectedAt = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1500000000000L), ZoneOffset.UTC)
        // episodes
        val episode1 = SyncEpisode(
            number = 1,
            collectedAt = collectedAt
        )
        val episode2 = SyncEpisode(
            number = 2
        )
        // season
        val season = SyncSeason(
            number = TestData.EPISODE_SEASON,
            episodes = listOf(episode1, episode2)
        )
        // show
        val show = SyncShow(
            ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID),
            seasons = listOf(season)
        )
        val items = SyncItems(shows = listOf(show))
        addItemsToCollection(items)
    }

    @Throws(IOException::class)
    private fun addItemsToCollection(items: SyncItems) = runBlocking {
        val response = trakt.sync().addItemsToCollection(items)
        response.shouldNotBeNull()
        assertSyncResponse(response)
    }

    private fun assertSyncResponse(response: SyncResponse) {
        response.added.shouldNotBeNull()
        response.added!!.movies.shouldNotBeNull()
        response.added!!.episodes.shouldNotBeNull()
        response.existing.shouldNotBeNull()
        response.existing!!.movies.shouldNotBeNull()
        response.existing!!.episodes.shouldNotBeNull()
        response.notFound.shouldNotBeNull()
        response.deleted.shouldBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `remove items from the collection`() = runBlocking {
        val response = trakt.sync().deleteItemsFromCollection(buildItemsForDeletion())
        response.shouldNotBeNull()
        assertSyncResponseDelete(response)
    }

    private fun assertSyncResponseDelete(response: SyncResponse) {
        response.deleted.shouldNotBeNull()
        response.deleted!!.movies.shouldNotBeNull()
        response.deleted!!.episodes.shouldNotBeNull()
        response.existing.shouldBeNull()
        response.notFound.shouldNotBeNull()
        response.added.shouldBeNull()
    }

    private fun buildItemsForDeletion(): SyncItems {
        // movie
        val movie = SyncMovie(ids = buildMovieIds())
        // episode
        val episode2 = SyncEpisode(number = 2)
        val season = SyncSeason(number = TestData.EPISODE_SEASON, episodes = listOf(episode2))
        val show = SyncShow(ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID), seasons = listOf(season))
        return SyncItems(movies = listOf(movie), shows = listOf(show))
    }

    @Test
    @Throws(IOException::class)
    fun `get watched movies`() = runBlocking {
        val watchedMovies = trakt.sync().watchedMovies(null)
        watchedMovies.shouldNotBeNull()
        assertSyncMovies(watchedMovies, "watched")
    }

    @Test
    @Throws(IOException::class)
    fun `get watched shows`() = runBlocking {
        val watchedShows = trakt.sync().watchedShows(null)
        watchedShows.shouldNotBeNull()
        assertSyncShows(watchedShows, "watched")
    }

    @Test
    @Throws(IOException::class)
    fun `add items to watched history`() = runBlocking {
        // movie
        val movie = SyncMovie(
            ids = buildMovieIds(),
            watchedAt = OffsetDateTime.now().minusHours(1)
        )
        // episode
        val episode = SyncEpisode(
            number = TestData.EPISODE_NUMBER,
            watchedAt = OffsetDateTime.now().minusHours(1)
        )
        val episode2 = SyncEpisode(
            number = 2,
            watchedAt = OffsetDateTime.now().minusMinutes(30)
        )
        // season
        val season = SyncSeason(
            number = TestData.EPISODE_SEASON,
            episodes = listOf(episode, episode2)
        )
        // show
        val show = SyncShow(
            ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID),
            seasons = listOf(season)
        )
        val items = SyncItems(movies = listOf(movie), shows = listOf(show))
        val response = trakt.sync().addItemsToWatchedHistory(items)
        response.shouldNotBeNull()
        response.added.shouldNotBeNull()
        response.added!!.movies.shouldNotBeNull()
        response.added!!.episodes.shouldNotBeNull()
        response.existing.shouldBeNull()
        response.notFound.shouldNotBeNull()
        response.deleted.shouldBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `remove items from history`() = runBlocking<Unit> {
        val items = buildItemsForDeletion()
        val response = trakt.sync().deleteItemsFromWatchedHistory(items)
        response.shouldNotBeNull()
        response.added.shouldBeNull()
        response.existing.shouldBeNull()
        response.notFound.shouldNotBeNull()
        response.deleted.shouldNotBeNull()
        response.deleted!!.movies.shouldNotBeNull()
        response.deleted!!.episodes.shouldNotBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `get all rated movies`() = runBlocking {
        val ratedMovies = trakt.sync().ratingsMovies(RatingsFilter.ALL, null)
        ratedMovies.shouldNotBeNull()
        assertRatedEntities(ratedMovies)
    }

    @Test
    @Throws(IOException::class)
    fun `get filtered rated movies`() = runBlocking {
        val ratedMovies = trakt.sync().ratingsMovies(RatingsFilter.TOTALLYNINJA, null)
        ratedMovies.shouldNotBeNull()
        for (movie in ratedMovies) {
            movie.ratedAt.shouldNotBeNull()
            movie.rating.shouldNotBeNull().shouldBeEqualTo(Rating.TOTALLYNINJA)
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get all rated shows`() = runBlocking {
        val ratedShows = trakt.sync().ratingsShows(RatingsFilter.ALL, null)
        ratedShows.shouldNotBeNull()
        assertRatedEntities(ratedShows)
    }

    @Test
    @Throws(IOException::class)
    fun `get all rated seasons`() = runBlocking {
        val ratedSeasons = trakt.sync().ratingsSeasons(RatingsFilter.ALL, null)
        ratedSeasons.shouldNotBeNull()
        assertRatedEntities(ratedSeasons)
    }

    @Test
    @Throws(IOException::class)
    fun `get rated episodes`() = runBlocking {
        val ratedEpisodes = trakt.sync().ratingsEpisodes(RatingsFilter.ALL, null)
        ratedEpisodes.shouldNotBeNull()
        assertRatedEntities(ratedEpisodes)
    }

    @Test
    @Throws(IOException::class)
    fun `add rating for a movie`() = runBlocking<Unit> {
        val movie = SyncMovie(ids = MovieIds.slug(TestData.MOVIE_SLUG), rating = Rating.MEH)
        val items = SyncItems(movies = listOf(movie))
        trakt.sync().addRatings(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add rating for a show`() = runBlocking<Unit> {
        val show = SyncShow(ids = ShowIds.slug(TestData.SHOW_SLUG), rating = Rating.TERRIBLE)
        val items = SyncItems(shows = listOf(show))
        trakt.sync().addRatings(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add rating for a season`() = runBlocking<Unit> {
        val season = SyncSeason(number = TestData.EPISODE_SEASON, rating = Rating.FAIR)
        val show = SyncShow(ids = ShowIds.slug("community"), seasons = listOf(season))
        val items = SyncItems(shows = listOf(show))
        trakt.sync().addRatings(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add rating for episodes`() = runBlocking<Unit> {
        val episode1 = SyncEpisode(number = 1, rating = Rating.TOTALLYNINJA)
        val episode2 = SyncEpisode(number = 1, rating = Rating.GREAT)
        val season = SyncSeason(number = TestData.EPISODE_SEASON, episodes = listOf(episode1, episode2))
        val show = SyncShow(ids = ShowIds.slug(TestData.SHOW_SLUG), seasons = listOf(season))
        val items = SyncItems(shows = listOf(show))
        trakt.sync().addRatings(items)
    }

    @Test
    @Throws(IOException::class)
    fun `remove ratings`() = runBlocking<Unit> {
        val items = buildItemsForDeletion()
        val response = trakt.sync().deleteRatings(items)
        response.shouldNotBeNull()
        response.added.shouldBeNull()
        response.existing.shouldBeNull()
        response.deleted.shouldNotBeNull()
        response.deleted!!.movies.shouldNotBeNull()
        response.deleted!!.seasons.shouldNotBeNull()
        response.deleted!!.episodes.shouldNotBeNull()
        response.notFound.shouldNotBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `get watchlisted movies`() = runBlocking {
        val movies = trakt.sync().watchlistMovies(null)
        movies.shouldNotBeNull()
        assertSyncMovies(movies, "watchlist")
    }

    @Test
    @Throws(IOException::class)
    fun `get watchlisted shows`() = runBlocking {
        val baseShows = trakt.sync().watchlistShows(null)
        baseShows.shouldNotBeNull()
        for (baseShow in baseShows) {
            baseShow.show.shouldNotBeNull()
            baseShow.listedAt.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get watchlisted seasons`() = runBlocking {
        val watchlistedSeasons =  trakt.sync().watchlistSeasons(null)
        watchlistedSeasons.shouldNotBeNull()
        for (watchlistedSeason in watchlistedSeasons) {
            watchlistedSeason.season.shouldNotBeNull()
            watchlistedSeason.show.shouldNotBeNull()
            watchlistedSeason.listedAt.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get watchlisted episodes`() = runBlocking {
        val watchlistedEpisodes = trakt.sync().watchlistEpisodes(null)
        watchlistedEpisodes.shouldNotBeNull()
        for (watchlistedEpisode in watchlistedEpisodes) {
            watchlistedEpisode.episode.shouldNotBeNull()
            watchlistedEpisode.show.shouldNotBeNull()
            watchlistedEpisode.listedAt.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `add a movie to watchlist`() {
        val movie = SyncMovie(ids = buildMovieIds())
        val items = SyncItems(movies = listOf(movie))
        addItemsToWatchlist(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add a show to watchlist`() {
        val show = SyncShow(ids = buildShowIds())
        val items = SyncItems(shows = listOf(show))
        addItemsToWatchlist(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add a season to watchlist`() = runBlocking {
        // season
        val season = SyncSeason(number = 1)
        // show
        val show = SyncShow(ids = ShowIds.slug("community"), seasons = listOf(season))
        val items = SyncItems(shows = listOf(show))
        addItemsToWatchlist(items)
    }

    @Test
    @Throws(IOException::class)
    fun `add episodes to watchlist`() = runBlocking {
        // episode
        val episode1 = SyncEpisode(number = 1)
        val episode2 = SyncEpisode(number = 2)
        // season
        val season = SyncSeason(number = TestData.EPISODE_SEASON, episodes = listOf(episode1, episode2))
        // show
        val show = SyncShow(ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID), seasons = listOf(season))
        val items = SyncItems(shows = listOf(show))
        addItemsToWatchlist(items)
    }

    private fun addItemsToWatchlist(items: SyncItems) = runBlocking {
        val requestResponse = trakt.sync().addItemsToWatchlist(items)
        requestResponse.shouldNotBeNull()
        assertSyncResponse(requestResponse)
    }

    @Test
    @Throws(IOException::class)
    fun `remove items from watchlist`() = runBlocking {
        val requestResponse = trakt.sync().deleteItemsFromWatchlist(buildItemsForDeletion())
        requestResponse.shouldNotBeNull()
        assertSyncResponseDelete(requestResponse)
    }

    private fun buildMovieIds(): MovieIds {
        return MovieIds.tmdb(TestData.MOVIE_TMDB_ID)
    }

    private fun buildShowIds(): ShowIds {
        return ShowIds.slug("the-walking-dead")
    }
}