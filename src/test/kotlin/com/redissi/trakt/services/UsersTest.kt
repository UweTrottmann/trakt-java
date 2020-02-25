package com.redissi.trakt.services

import com.redissi.trakt.*
import com.redissi.trakt.entities.*
import com.redissi.trakt.enums.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.*
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

class UsersTest : BaseTestCase(), TestSync, TestResponse, TestRatedEntities {
    @Test
    @Throws(IOException::class)
    fun `get user's settings`() = runBlocking<Unit> {
        val settings = trakt.users().settings()
        settings.shouldNotBeNull()
        settings.user.shouldNotBeNull()
        settings.account.shouldNotBeNull()
        settings.connections.shouldNotBeNull()
        settings.sharingText.shouldNotBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `get user profile`() = runBlocking<Unit> {
        val user = trakt.users().profile(TestData.USER_SLUG, Extended.FULL)
        user.shouldNotBeNull()
        user.username.shouldNotBeNull().shouldBeEqualTo(TestData.USERNAME_STRING)
        user.isPrivate.shouldNotBeNull().shouldBeFalse()
        user.name.shouldNotBeNull().shouldBeEqualTo(TestData.USER_REAL_NAME)
        user.vip.shouldNotBeNull().shouldBeTrue()
        user.vipEp.shouldNotBeNull().shouldBeTrue()
        user.ids.shouldNotBeNull()
        user.ids!!.slug.shouldNotBeNull().shouldBeEqualTo(TestData.USERNAME_STRING)
        user.images.shouldNotBeNull()
        user.images!!.avatar.shouldNotBeNull().full.shouldNotBeNull().shouldNotBeEmpty()
    }

    @Test
    @Throws(IOException::class)
    fun `get user's collected movies`() = runBlocking {
        val movies = trakt.users().collectionMovies(TestData.USER_SLUG, null)
        movies.shouldNotBeNull()
        assertSyncMovies(movies, "collection")
    }

    @Test
    @Throws(IOException::class)
    fun `get user's collected shows`() = runBlocking {
        val shows = trakt.users().collectionShows(TestData.USER_SLUG, null)
        shows.shouldNotBeNull()
        assertSyncShows(shows, "collection")
    }

    @Test
    @Throws(IOException::class)
    fun `get user's lists`() = runBlocking {
        val lists = trakt.users().lists(UserSlug.ME)
        lists.shouldNotBeNull()
        for (list in lists) {
            // ensure id and a title
            list.ids.shouldNotBeNull()
            list.ids!!.trakt.shouldNotBeNull()
            list.name.shouldNotBeNullOrEmpty()
            list.description.shouldNotBeNullOrEmpty()
            list.privacy.shouldNotBeNull()
            list.displayNumbers.shouldNotBeNull()
            list.allowComments.shouldNotBeNull()
            list.sortBy.shouldNotBeNull()
            list.sortHow.shouldNotBeNull()
            list.createdAt.shouldNotBeNull()
            list.updatedAt.shouldNotBeNull()
            list.itemCount.shouldNotBeNull()
            list.commentCount.shouldNotBeNull().shouldBeGreaterOrEqualTo(0)
            list.likes.shouldNotBeNull().shouldBeGreaterOrEqualTo(0)
            list.user.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `create list`() = runBlocking<Unit> {
        val list = TraktList(
            name = "trakt-kotlin",
            description = "trakt-kotlin test list",
            privacy = ListPrivacy.PUBLIC,
            allowComments = false,
            displayNumbers = false
        )
        // create list...
        val createdList = trakt.users().createList(UserSlug.ME, list)
        createdList.shouldNotBeNull()
        createdList.ids.shouldNotBeNull()
        createdList.ids!!.trakt.shouldNotBeNull()
        createdList.name.shouldNotBeNull().shouldBeEqualTo(list.name)
        createdList.description.shouldNotBeNull().shouldBeEqualTo(list.description)
        // ...and delete it again
        val deleteResponse: Response<*> = trakt.users().deleteList(UserSlug.ME, createdList.ids!!.trakt.toString())
        assertSuccessfulResponse(deleteResponse, trakt)
        deleteResponse.code().shouldBeEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    @Test
    @Throws(IOException::class)
    fun `update a list`() = runBlocking<Unit> {
        // change name (append a new suffix that changes frequently)
        val secondOfDay = LocalTime.now().toSecondOfDay()
        val list = TraktList(
            name = "trakt-kotlin $secondOfDay"
        )
        // create list...
        val updatedList = trakt.users().updateList(UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(), list)
        updatedList.shouldNotBeNull()
        updatedList.ids.shouldNotBeNull()
        updatedList.ids!!.trakt.shouldNotBeNull().shouldBeEqualTo(TEST_LIST_WITH_ITEMS_TRAKT_ID)
        updatedList.name.shouldNotBeNull().shouldBeEqualTo(list.name)
    }

    @Test
    @Throws(IOException::class)
    fun `get a list`() = runBlocking {
        val entries = trakt.users().listItems(UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(), null)
        entries.shouldNotBeNull()
        for (entry in entries) {
            entry.listedAt.shouldNotBeNull()
            entry.id.shouldNotBeNull()
            entry.rank.shouldNotBeNull()
            entry.type.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `add items to a list`() = runBlocking<Unit> {
        val show = SyncShow(ids = ShowIds.tvdb(256227))
        val movie = SyncMovie(ids = MovieIds.tmdb(TestData.MOVIE_TMDB_ID))
        val person = SyncPerson(ids = PersonIds.tmdb(TestData.PERSON_TMDB_ID))
        val items = SyncItems(
            shows = listOf(show),
            movies = listOf(movie),
            people = listOf(person)
        )
        // add items...
        var response = trakt.users().addListItems(UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(), items)
        response.shouldNotBeNull()
        response.added.shouldNotBeNull()
        response.added!!.shows.shouldNotBeNull().shouldBeEqualTo(1)
        response.added!!.movies.shouldNotBeNull().shouldBeEqualTo(1)
        response.added!!.people.shouldNotBeNull().shouldBeEqualTo(1)
        // ...and remove them again
        response = trakt.users().deleteListItems(UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(), items)
        response.shouldNotBeNull()
        response.deleted.shouldNotBeNull()
        response.deleted!!.shows.shouldNotBeNull().shouldBeEqualTo(1)
        response.deleted!!.movies.shouldNotBeNull().shouldBeEqualTo(1)
        response.deleted!!.people.shouldNotBeNull().shouldBeEqualTo(1)
    }

    @Test
    @Throws(IOException::class)
    fun `reorder a list`() = runBlocking<Unit> {
        val entries = trakt.users().listItems(UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(), null)
        entries.shouldNotBeNull()
        // reverse order
        val newRank: MutableList<Long> = ArrayList()
        for (i in entries.indices.reversed()) {
            val id = entries[i].id
            id.shouldNotBeNull()
            newRank.add(id)
        }
        val response = trakt.users().reorderListItems(
            UserSlug.ME,
            TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(),
            ListItemRank(newRank)
        )
        response.shouldNotBeNull()
        response.updated.shouldNotBeNull().shouldBeEqualTo(entries.size)
    }

    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun `unfollow and follow a user`() = runBlocking<Unit> {
        // unfollow first
        val userToFollow = UserSlug(TestData.USER_TO_FOLLOW)
        val response: Response<*> = trakt.users().unfollow(userToFollow)
        assertSuccessfulResponse(response, trakt)
        response.code().shouldBeEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
        // give the server some time to handle the data
        delay(1000)
        // follow again
        val followedResponse = trakt.users().follow(userToFollow)
        followedResponse.shouldNotBeNull()
        followedResponse.user.shouldNotBeNull()
            .username.shouldNotBeNull().shouldBeEqualTo(TestData.USER_TO_FOLLOW)
    }

    @Test
    @Throws(IOException::class)
    fun `get user's followers`() = runBlocking {
        val followers = trakt.users().followers(TestData.USER_SLUG, null)
        followers.shouldNotBeNull()
        for (follower in followers) {
            follower.followedAt.shouldNotBeNull()
            follower.user.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get following users`() = runBlocking {
        val following = trakt.users().following(TestData.USER_SLUG, null)
        following.shouldNotBeNull()
        for (follower in following) {
            follower.followedAt.shouldNotBeNull()
            follower.user.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get user's friends`() = runBlocking {
        val friends = trakt.users().friends(TestData.USER_SLUG, null)
        friends.shouldNotBeNull()
        for (friend in friends) {
            friend.friendsAt.shouldNotBeNull()
            friend.user.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get history of episodes and movies`() = runBlocking {
        val history = trakt.users().history(
            TestData.USER_SLUG,
            1,
            DEFAULT_PAGE_SIZE,
            null,
            null,
            null
        )
        history.shouldNotBeNull()
        for (entry in history) {
            entry.id.shouldNotBeNull().shouldBeGreaterThan(0)
            entry.watchedAt.shouldNotBeNull()
            entry.action.shouldNotBeNullOrEmpty()
            entry.type.shouldNotBeNullOrEmpty()
            if ("episode" == entry.type) {
                entry.episode.shouldNotBeNull()
                entry.show.shouldNotBeNull()
            } else if ("movie" == entry.type) {
                entry.movie.shouldNotBeNull()
            }
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get history of episodes`() = runBlocking {
        val history = trakt.users().history(
            TestData.USER_SLUG,
            HistoryType.EPISODES,
            1,
            DEFAULT_PAGE_SIZE,
            null,
            null,
            null
        )
        history.shouldNotBeNull()
        for (entry in history) {
            entry.id.shouldNotBeNull()
            entry.watchedAt.shouldNotBeNull()
            entry.action.shouldNotBeNullOrEmpty()
            entry.type.shouldNotBeNull().shouldBeEqualTo("episode")
            entry.episode.shouldNotBeNull()
            entry.show.shouldNotBeNull()
            println(
                "Episode watched at date: " + entry.watchedAt + entry.watchedAt!!.toInstant().toEpochMilli()
            )
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get history of movies`() = runBlocking {
        val history = trakt.users().history(
            UserSlug.ME,
            HistoryType.MOVIES,
            1,
            DEFAULT_PAGE_SIZE,
            null,
            null,
            null
        )
        history.shouldNotBeNull()
        assertMovieHistory(history)
    }

    @Test
    @Throws(IOException::class)
    fun `get history of an item`() = runBlocking {
        val history = trakt.users().history(
            UserSlug.ME,
            HistoryType.MOVIES,
            TestData.MOVIE_WATCHED_TRAKT_ID,
            1,
            DEFAULT_PAGE_SIZE,
            null,
            OffsetDateTime.of(2016, 8, 3, 9, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2016, 8, 3, 10, 0, 0, 0, ZoneOffset.UTC)
        )
        history.shouldNotBeNull()
        history.size.shouldBeGreaterThan(0)
        assertMovieHistory(history)
    }

    private fun assertMovieHistory(history: List<HistoryEntry>) {
        for (entry in history) {
            entry.watchedAt.shouldNotBeNull()
            entry.action.shouldNotBeNullOrEmpty()
            entry.type.shouldNotBeNull().shouldBeEqualTo("movie")
            entry.movie.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get rated movies`() = runBlocking {
        val ratedMovies = trakt.users()
            .ratingsMovies(TestData.USER_SLUG, RatingsFilter.ALL, null)
        ratedMovies.shouldNotBeNull()
        assertRatedEntities(ratedMovies)
    }

    @Test
    @Throws(IOException::class)
    fun `get rated movies with a filter`() = runBlocking {
        val ratedMovies = trakt.users().ratingsMovies(
            TestData.USER_SLUG,
            RatingsFilter.TOTALLYNINJA,
            null
        )
        ratedMovies.shouldNotBeNull()
        for (movie in ratedMovies) {
            movie.ratedAt.shouldNotBeNull()
            movie.rating.shouldNotBeNull().shouldBeEqualTo(Rating.TOTALLYNINJA)
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get rated shows`() = runBlocking {
        val ratedShows = trakt.users()
            .ratingsShows(TestData.USER_SLUG, RatingsFilter.ALL, null)
        ratedShows.shouldNotBeNull()
        assertRatedEntities(ratedShows)
    }

    @Test
    @Throws(IOException::class)
    fun `get rated seasons`() = runBlocking {
        val ratedSeasons = trakt.users().ratingsSeasons(TestData.USER_SLUG, RatingsFilter.ALL, null)
        ratedSeasons.shouldNotBeNull()
        assertRatedEntities(ratedSeasons)
    }

    @Test
    @Throws(IOException::class)
    fun `get rated episodes`() = runBlocking {
        val ratedEpisodes = trakt.users().ratingsEpisodes(TestData.USER_SLUG, RatingsFilter.ALL, null)
        ratedEpisodes.shouldNotBeNull()
        assertRatedEntities(ratedEpisodes)
    }

    @Test
    @Throws(IOException::class)
    fun `get movies in watchlist`() = runBlocking {
        val movies = trakt.users().watchlistMovies(UserSlug.ME, null)
        movies.shouldNotBeNull()
        assertSyncMovies(movies, "watchlist")
    }

    @Test
    @Throws(IOException::class)
    fun `get shows in watchlist`() = runBlocking {
        val shows = trakt.users().watchlistShows(UserSlug.ME, null)
        shows.shouldNotBeNull()
        for (show in shows) {
            show.show.shouldNotBeNull()
            show.listedAt.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get seasons in watchlist`() = runBlocking {
        val seasons = trakt.users().watchlistSeasons(UserSlug.ME, null)
        seasons.shouldNotBeNull()
        for (season in seasons) {
            season.season.shouldNotBeNull()
            season.show.shouldNotBeNull()
            season.listedAt.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get episodes in watchlist`() = runBlocking {
        val episodes = trakt.users().watchlistEpisodes(UserSlug.ME, null)
        episodes.shouldNotBeNull()
        for (episode in episodes) {
            episode.episode.shouldNotBeNull()
            episode.show.shouldNotBeNull()
            episode.listedAt.shouldNotBeNull()
        }
    }

    @Test
    @Throws(IOException::class)
    fun `get watched movies`() = runBlocking {
        val watchedMovies = trakt.users().watchedMovies(TestData.USER_SLUG, null)
        assertSyncMovies(watchedMovies!!, "watched")
    }

    @Test
    @Throws(IOException::class)
    fun `get watched shows`() = runBlocking {
        val watchedShows = trakt.users().watchedShows(TestData.USER_SLUG, null)
        assertSyncShows(watchedShows!!, "watched")
    }

    companion object {
        private const val TEST_LIST_WITH_ITEMS_TRAKT_ID = 4834928
    }
}