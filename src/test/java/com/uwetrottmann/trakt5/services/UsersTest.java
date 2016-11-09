package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.BaseMovie;
import com.uwetrottmann.trakt5.entities.BaseShow;
import com.uwetrottmann.trakt5.entities.Followed;
import com.uwetrottmann.trakt5.entities.Follower;
import com.uwetrottmann.trakt5.entities.Friend;
import com.uwetrottmann.trakt5.entities.HistoryEntry;
import com.uwetrottmann.trakt5.entities.ListEntry;
import com.uwetrottmann.trakt5.entities.MovieIds;
import com.uwetrottmann.trakt5.entities.RatedEpisode;
import com.uwetrottmann.trakt5.entities.RatedMovie;
import com.uwetrottmann.trakt5.entities.RatedSeason;
import com.uwetrottmann.trakt5.entities.RatedShow;
import com.uwetrottmann.trakt5.entities.Settings;
import com.uwetrottmann.trakt5.entities.ShowIds;
import com.uwetrottmann.trakt5.entities.SyncItems;
import com.uwetrottmann.trakt5.entities.SyncMovie;
import com.uwetrottmann.trakt5.entities.SyncResponse;
import com.uwetrottmann.trakt5.entities.SyncShow;
import com.uwetrottmann.trakt5.entities.TraktList;
import com.uwetrottmann.trakt5.entities.User;
import com.uwetrottmann.trakt5.entities.Username;
import com.uwetrottmann.trakt5.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt5.entities.WatchlistedSeason;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.HistoryType;
import com.uwetrottmann.trakt5.enums.ListPrivacy;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.RatingsFilter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersTest extends BaseTestCase {

    private static final int TEST_LIST_WITH_ITEMS_TRAKT_ID = 1012786;

    @Test
    public void test_getSettings() throws IOException {
        Settings settings = executeCall(getTrakt().users().settings());
        assertThat(settings.user).isNotNull();
        assertThat(settings.account).isNotNull();
        assertThat(settings.connections).isNotNull();
        assertThat(settings.sharing_text).isNotNull();
    }

    @Test
    public void test_profile() throws IOException {
        User user = executeCall(getTrakt().users().profile(TestData.USERNAME, Extended.FULL));
        assertThat(user.username).isEqualTo(TestData.USERNAME_STRING);
        assertThat(user.isPrivate).isEqualTo(false);
        assertThat(user.name).isEqualTo(TestData.USER_REAL_NAME);
        assertThat(user.vip).isEqualTo(true);
        assertThat(user.images.avatar.full).isNotEmpty();
    }

    @Test
    public void test_collectionMovies() throws IOException {
        List<BaseMovie> movies = executeCall(
                getTrakt().users().collectionMovies(TestData.USERNAME, Extended.DEFAULT_MIN));
        assertSyncMovies(movies, "collection");
    }

    @Test
    public void test_collectionShows() throws IOException {
        List<BaseShow> shows = executeCall(getTrakt().users().collectionShows(TestData.USERNAME, Extended.DEFAULT_MIN));
        assertSyncShows(shows, "collection");
    }

    @Test
    public void test_lists() throws IOException {
        List<TraktList> lists = executeCall(getTrakt().users().lists(Username.ME));
        for (TraktList list : lists) {
            // ensure id and a title
            assertThat(list.ids).isNotNull();
            assertThat(list.ids.trakt).isNotNull();
            assertThat(list.name).isNotEmpty();
            assertThat(list.updated_at).isNotNull();
            assertThat(list.item_count).isPositive();
            assertThat(list.comment_count).isGreaterThanOrEqualTo(0);
            assertThat(list.likes).isGreaterThanOrEqualTo(0);
        }
    }

    @Test
    public void test_createList() throws IOException {
        TraktList list = new TraktList();
        list.name("trakt-java");
        list.description("trakt-java test list");
        list.privacy(ListPrivacy.PUBLIC);
        list.allowComments(false);
        list.displayNumbers(false);

        // create list...
        TraktList createdList = executeCall(getTrakt().users().createList(Username.ME, list));
        assertThat(createdList.ids.trakt).isNotNull();
        assertThat(createdList.name).isEqualTo(list.name);
        assertThat(createdList.description).isEqualTo(list.description);

        // ...and delete it again
        Response deleteResponse = getTrakt().users().deleteList(Username.ME,
                String.valueOf(createdList.ids.trakt)).execute();
        assertSuccessfulResponse(deleteResponse);
        assertThat(deleteResponse.code()).isEqualTo(204);
    }


    @Test
    public void test_updateList() throws IOException {
        // change name (append a new suffix that changes frequently)
        int secondOfDay = new DateTime().getSecondOfDay();
        TraktList list = new TraktList();
        list.name("trakt-java " + secondOfDay);

        // create list...
        TraktList updatedList = executeCall(getTrakt().users().updateList(Username.ME, String.valueOf(
                TEST_LIST_WITH_ITEMS_TRAKT_ID), list));
        assertThat(updatedList.ids.trakt).isEqualTo(TEST_LIST_WITH_ITEMS_TRAKT_ID);
        assertThat(updatedList.name).isEqualTo(list.name);
    }

    @Test
    public void test_listItems() throws IOException {
        List<ListEntry> entries = executeCall(getTrakt().users().listItems(Username.ME,
                String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                Extended.DEFAULT_MIN));
        for (ListEntry entry : entries) {
            assertThat(entry.listed_at).isNotNull();
        }
    }

    @Test
    public void test_addListItems() throws IOException {
        SyncShow show = new SyncShow().id(ShowIds.tvdb(256227));
        SyncMovie movie = new SyncMovie().id(MovieIds.tmdb(TestData.MOVIE_TMDB_ID));

        SyncItems items = new SyncItems();
        items.shows(show);
        items.movies(movie);

        // add items...
        SyncResponse response = executeCall(getTrakt().users().addListItems(Username.ME,
                String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                items));

        assertThat(response.added.shows).isEqualTo(1);
        assertThat(response.added.movies).isEqualTo(1);

        // ...and remove them again
        response = executeCall(
                getTrakt().users().deleteListItems(Username.ME, String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                        items));

        assertThat(response.deleted.shows).isEqualTo(1);
        assertThat(response.deleted.movies).isEqualTo(1);
    }

    @Test
    public void test_unfollowAndFollow() throws InterruptedException, IOException {
        // unfollow first
        Username userToFollow = new Username(TestData.USER_TO_FOLLOW);
        Response response = getTrakt().users().unfollow(userToFollow).execute();
        assertSuccessfulResponse(response);
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);

        // give the server some time to handle the data
        Thread.sleep(1000);

        // follow again
        Followed followedResponse = executeCall(getTrakt().users().follow(userToFollow));
        assertThat(followedResponse.user.username).isEqualTo(TestData.USER_TO_FOLLOW);
    }

    @Test
    public void test_followers() throws IOException {
        List<Follower> followers = executeCall(getTrakt().users().followers(TestData.USERNAME, Extended.DEFAULT_MIN));
        for (Follower follower : followers) {
            assertThat(follower.followed_at).isNotNull();
            assertThat(follower.user).isNotNull();
        }
    }

    @Test
    public void test_following() throws IOException {
        List<Follower> following = executeCall(getTrakt().users().following(TestData.USERNAME, Extended.DEFAULT_MIN));
        for (Follower follower : following) {
            assertThat(follower.followed_at).isNotNull();
            assertThat(follower.user).isNotNull();
        }
    }

    @Test
    public void test_friends() throws IOException {
        List<Friend> friends = executeCall(getTrakt().users().friends(TestData.USERNAME, Extended.DEFAULT_MIN));
        for (Friend friend : friends) {
            assertThat(friend.friends_at).isNotNull();
            assertThat(friend.user).isNotNull();
        }
    }

    @Test
    public void test_historyEpisodesAndMovies() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().users().history(TestData.USERNAME, 1,
                        DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN,
                        null, null));
        for (HistoryEntry entry : history) {
            assertThat(entry.id).isGreaterThan(0);
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isNotEmpty();
            if ("episode".equals(entry.type)) {
                assertThat(entry.episode).isNotNull();
                assertThat(entry.show).isNotNull();
            } else if ("movie".equals(entry.type)) {
                assertThat(entry.movie).isNotNull();
            }
        }
    }

    @Test
    public void test_historyEpisodes() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().users().history(TestData.USERNAME, HistoryType.EPISODES, 1,
                        DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN,
                        null, null));
        for (HistoryEntry entry : history) {
            assertThat(entry.id).isGreaterThan(0);
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isEqualTo("episode");
            assertThat(entry.episode).isNotNull();
            assertThat(entry.show).isNotNull();
        }
    }

    @Test
    public void test_historyMovies() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().users().history(Username.ME, HistoryType.MOVIES, 1,
                        DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN,
                        null, null));
        assertMovieHistory(history);
    }

    @Test
    public void test_historyItem() throws IOException {
        List<HistoryEntry> history = executeCall(getTrakt().users().history(Username.ME, HistoryType.MOVIES,
                TestData.MOVIE_WATCHED_TRAKT_ID, 1,
                DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN,
                new DateTime(2016, 8, 3, 9, 0, 0, 0, DateTimeZone.UTC),
                new DateTime(2016, 8, 3, 10, 0, 0, 0, DateTimeZone.UTC)));
        assertThat(history.size()).isGreaterThan(0);
        assertMovieHistory(history);
    }

    private void assertMovieHistory(List<HistoryEntry> history) {
        for (HistoryEntry entry : history) {
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isEqualTo("movie");
            assertThat(entry.movie).isNotNull();
        }
    }

    @Test
    public void test_ratingsMovies() throws IOException {
        List<RatedMovie> ratedMovies = executeCall(
                getTrakt().users().ratingsMovies(TestData.USERNAME, RatingsFilter.ALL,
                        Extended.DEFAULT_MIN));
        assertRatedEntities(ratedMovies);
    }

    @Test
    public void test_ratingsMovies_filtered() throws IOException {
        List<RatedMovie> ratedMovies = executeCall(getTrakt().users().ratingsMovies(TestData.USERNAME,
                RatingsFilter.TOTALLYNINJA,
                Extended.DEFAULT_MIN));
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(Rating.TOTALLYNINJA);
        }
    }

    @Test
    public void test_ratingsShows() throws IOException {
        List<RatedShow> ratedShows = executeCall(
                getTrakt().users().ratingsShows(TestData.USERNAME, RatingsFilter.ALL,
                        Extended.DEFAULT_MIN));
        assertRatedEntities(ratedShows);
    }

    @Test
    public void test_ratingsSeasons() throws IOException {
        List<RatedSeason> ratedSeasons = executeCall(
                getTrakt().users().ratingsSeasons(TestData.USERNAME, RatingsFilter.ALL,
                        Extended.DEFAULT_MIN));
        assertRatedEntities(ratedSeasons);
    }

    @Test
    public void test_ratingsEpisodes() throws IOException {
        List<RatedEpisode> ratedEpisodes = executeCall(
                getTrakt().users().ratingsEpisodes(TestData.USERNAME, RatingsFilter.ALL,
                        Extended.DEFAULT_MIN));
        assertRatedEntities(ratedEpisodes);
    }

    @Test
    public void test_watchlistMovies() throws IOException {
        List<BaseMovie> movies = executeCall(getTrakt().users().watchlistMovies(Username.ME,
                Extended.DEFAULT_MIN));
        assertSyncMovies(movies, "watchlist");
    }

    @Test
    public void test_watchlistShows() throws IOException {
        List<BaseShow> shows = executeCall(getTrakt().users().watchlistShows(Username.ME,
                Extended.DEFAULT_MIN));
        for (BaseShow show : shows) {
            assertThat(show.show).isNotNull();
            assertThat(show.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistSeasons() throws IOException {
        List<WatchlistedSeason> seasons = executeCall(getTrakt().users().watchlistSeasons(Username.ME,
                Extended.DEFAULT_MIN));
        for (WatchlistedSeason season : seasons) {
            assertThat(season.season).isNotNull();
            assertThat(season.show).isNotNull();
            assertThat(season.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistEpisodes() throws IOException {
        List<WatchlistedEpisode> episodes = executeCall(getTrakt().users().watchlistEpisodes(Username.ME,
                Extended.DEFAULT_MIN));
        for (WatchlistedEpisode episode : episodes) {
            assertThat(episode.episode).isNotNull();
            assertThat(episode.show).isNotNull();
            assertThat(episode.listed_at).isNotNull();
        }
    }


    @Test
    public void test_watchedMovies() throws IOException {
        List<BaseMovie> watchedMovies = executeCall(getTrakt().users().watchedMovies(TestData.USERNAME,
                Extended.DEFAULT_MIN));
        assertSyncMovies(watchedMovies, "watched");
    }

    @Test
    public void test_watchedShows() throws IOException {
        List<BaseShow> watchedShows = executeCall(getTrakt().users().watchedShows(TestData.USERNAME,
                Extended.DEFAULT_MIN));
        assertSyncShows(watchedShows, "watched");
    }

}
