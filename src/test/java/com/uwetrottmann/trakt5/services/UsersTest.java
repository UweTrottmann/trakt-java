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
        Response<Settings> response = getTrakt().users().settings().execute();
        assertSuccessfulResponse(response);
        Settings settings = response.body();
        assertThat(settings.user).isNotNull();
        assertThat(settings.account).isNotNull();
        assertThat(settings.connections).isNotNull();
        assertThat(settings.sharing_text).isNotNull();
    }

    @Test
    public void test_profile() throws IOException {
        Response<User> response = getTrakt().users().profile(TestData.USERNAME, Extended.FULLIMAGES).execute();
        assertSuccessfulResponse(response);
        User user = response.body();
        assertThat(user.username).isEqualTo(TestData.USERNAME_STRING);
        assertThat(user.isPrivate).isEqualTo(false);
        assertThat(user.name).isEqualTo(TestData.USER_REAL_NAME);
        assertThat(user.vip).isEqualTo(true);
    }

    @Test
    public void test_collectionMovies() throws IOException {
        Response<List<BaseMovie>> response = getTrakt().users().collectionMovies(TestData.USERNAME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseMovie> movies = response.body();
        assertSyncMovies(movies, "collection");
    }

    @Test
    public void test_collectionShows() throws IOException {
        Response<List<BaseShow>> response = getTrakt().users().collectionShows(TestData.USERNAME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseShow> shows = response.body();
        assertSyncShows(shows, "collection");
    }

    @Test
    public void test_lists() throws IOException {
        Response<List<TraktList>> response = getTrakt().users().lists(Username.ME).execute();
        assertSuccessfulResponse(response);
        List<TraktList> lists = response.body();
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
        Response<TraktList> response = getTrakt().users().createList(Username.ME, list).execute();
        assertSuccessfulResponse(response);
        TraktList createdList = response.body();
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
        Response<TraktList> response = getTrakt().users().updateList(Username.ME, String.valueOf(
                TEST_LIST_WITH_ITEMS_TRAKT_ID), list).execute();
        TraktList updatedList = response.body();
        assertThat(updatedList.ids.trakt).isEqualTo(TEST_LIST_WITH_ITEMS_TRAKT_ID);
        assertThat(updatedList.name).isEqualTo(list.name);
    }

    @Test
    public void test_listItems() throws IOException {
        Response<List<ListEntry>> response = getTrakt().users().listItems(Username.ME,
                String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<ListEntry> entries = response.body();
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
        Response<SyncResponse> response = getTrakt().users().addListItems(Username.ME,
                String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                items).execute();
        assertSuccessfulResponse(response);
        assertThat(response.body().added.shows).isEqualTo(1);
        assertThat(response.body().added.movies).isEqualTo(1);

        // ...and remove them again
        response = getTrakt().users().deleteListItems(Username.ME, String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                items).execute();
        assertSuccessfulResponse(response);
        assertThat(response.body().deleted.shows).isEqualTo(1);
        assertThat(response.body().deleted.movies).isEqualTo(1);
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
        Response<Followed> followedResponse = getTrakt().users().follow(userToFollow).execute();
        assertSuccessfulResponse(response);
        assertThat(followedResponse.body().user.username).isEqualTo(TestData.USER_TO_FOLLOW);
    }

    @Test
    public void test_followers() throws IOException {
        Response<List<Follower>> response = getTrakt().users().followers(TestData.USERNAME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<Follower> followers = response.body();
        for (Follower follower : followers) {
            assertThat(follower.followed_at).isNotNull();
            assertThat(follower.user).isNotNull();
        }
    }

    @Test
    public void test_following() throws IOException {
        Response<List<Follower>> response = getTrakt().users().following(TestData.USERNAME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<Follower> following = response.body();
        for (Follower follower : following) {
            assertThat(follower.followed_at).isNotNull();
            assertThat(follower.user).isNotNull();
        }
    }

    @Test
    public void test_friends() throws IOException {
        Response<List<Friend>> response = getTrakt().users().friends(TestData.USERNAME, Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<Friend> friends = response.body();
        for (Friend friend : friends) {
            assertThat(friend.friends_at).isNotNull();
            assertThat(friend.user).isNotNull();
        }
    }

    @Test
    public void test_historyEpisodesAndMovies() throws IOException {
        Response<List<HistoryEntry>> response = getTrakt().users().history(TestData.USERNAME, 1, DEFAULT_PAGE_SIZE,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<HistoryEntry> history = response.body();
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
        Response<List<HistoryEntry>> response = getTrakt().users().history(TestData.USERNAME, HistoryType.EPISODES, 1,
                DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<HistoryEntry> history = response.body();
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
        Response<List<HistoryEntry>> response = getTrakt().users().history(Username.ME, HistoryType.MOVIES, 1,
                DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<HistoryEntry> history = response.body();
        for (HistoryEntry entry : history) {
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isEqualTo("movie");
            assertThat(entry.movie).isNotNull();
        }
    }

    @Test
    public void test_historyItem() throws IOException {
        Response<List<HistoryEntry>> response = getTrakt().users().history(Username.ME, HistoryType.MOVIES,
                TestData.MOVIE_TRAKT_ID, 1,
                DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<HistoryEntry> history = response.body();
        for (HistoryEntry entry : history) {
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isEqualTo("movie");
            assertThat(entry.movie).isNotNull();
        }
    }

    @Test
    public void test_ratingsMovies() throws IOException {
        Response<List<RatedMovie>> response = getTrakt().users().ratingsMovies(TestData.USERNAME, RatingsFilter.ALL,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedMovie> ratedMovies = response.body();
        assertRatedEntities(ratedMovies);
    }

    @Test
    public void test_ratingsMovies_filtered() throws IOException {
        Response<List<RatedMovie>> response = getTrakt().users().ratingsMovies(TestData.USERNAME,
                RatingsFilter.TOTALLYNINJA,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedMovie> ratedMovies = response.body();
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(Rating.TOTALLYNINJA);
        }
    }

    @Test
    public void test_ratingsShows() throws IOException {
        Response<List<RatedShow>> response = getTrakt().users().ratingsShows(TestData.USERNAME, RatingsFilter.ALL,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedShow> ratedShows = response.body();
        assertRatedEntities(ratedShows);
    }

    @Test
    public void test_ratingsSeasons() throws IOException {
        Response<List<RatedSeason>> response = getTrakt().users().ratingsSeasons(TestData.USERNAME, RatingsFilter.ALL,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedSeason> ratedSeasons = response.body();
        assertRatedEntities(ratedSeasons);
    }

    @Test
    public void test_ratingsEpisodes() throws IOException {
        Response<List<RatedEpisode>> response = getTrakt().users().ratingsEpisodes(TestData.USERNAME, RatingsFilter.ALL,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedEpisode> ratedEpisodes = response.body();
        assertRatedEntities(ratedEpisodes);
    }

    @Test
    public void test_watchlistMovies() throws IOException {
        Response<List<BaseMovie>> response = getTrakt().users().watchlistMovies(Username.ME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseMovie> movies = response.body();
        assertSyncMovies(movies, "watchlist");
    }

    @Test
    public void test_watchlistShows() throws IOException {
        Response<List<BaseShow>> response = getTrakt().users().watchlistShows(Username.ME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseShow> shows = response.body();
        for (BaseShow show : shows) {
            assertThat(show.show).isNotNull();
            assertThat(show.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistSeasons() throws IOException {
        Response<List<WatchlistedSeason>> response = getTrakt().users().watchlistSeasons(Username.ME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<WatchlistedSeason> seasons = response.body();
        for (WatchlistedSeason season : seasons) {
            assertThat(season.season).isNotNull();
            assertThat(season.show).isNotNull();
            assertThat(season.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistEpisodes() throws IOException {
        Response<List<WatchlistedEpisode>> response = getTrakt().users().watchlistEpisodes(Username.ME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<WatchlistedEpisode> episodes = response.body();
        for (WatchlistedEpisode episode : episodes) {
            assertThat(episode.episode).isNotNull();
            assertThat(episode.show).isNotNull();
            assertThat(episode.listed_at).isNotNull();
        }
    }


    @Test
    public void test_watchedMovies() throws IOException {
        Response<List<BaseMovie>> response = getTrakt().users().watchedMovies(TestData.USERNAME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseMovie> watchedMovies = response.body();
        assertSyncMovies(watchedMovies, "watched");
    }

    @Test
    public void test_watchedShows() throws IOException {
        Response<List<BaseShow>> response = getTrakt().users().watchedShows(TestData.USERNAME,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseShow> watchedShows = response.body();
        assertSyncShows(watchedShows, "watched");
    }

}
