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
import com.uwetrottmann.trakt5.entities.ListItemRank;
import com.uwetrottmann.trakt5.entities.ListReorderResponse;
import com.uwetrottmann.trakt5.entities.MovieIds;
import com.uwetrottmann.trakt5.entities.PersonIds;
import com.uwetrottmann.trakt5.entities.RatedEpisode;
import com.uwetrottmann.trakt5.entities.RatedMovie;
import com.uwetrottmann.trakt5.entities.RatedSeason;
import com.uwetrottmann.trakt5.entities.RatedShow;
import com.uwetrottmann.trakt5.entities.Settings;
import com.uwetrottmann.trakt5.entities.ShowIds;
import com.uwetrottmann.trakt5.entities.SyncItems;
import com.uwetrottmann.trakt5.entities.SyncMovie;
import com.uwetrottmann.trakt5.entities.SyncPerson;
import com.uwetrottmann.trakt5.entities.SyncResponse;
import com.uwetrottmann.trakt5.entities.SyncShow;
import com.uwetrottmann.trakt5.entities.TraktList;
import com.uwetrottmann.trakt5.entities.User;
import com.uwetrottmann.trakt5.entities.UserSlug;
import com.uwetrottmann.trakt5.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt5.entities.WatchlistedSeason;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.HistoryType;
import com.uwetrottmann.trakt5.enums.ListPrivacy;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.RatingsFilter;
import com.uwetrottmann.trakt5.enums.SortBy;
import com.uwetrottmann.trakt5.enums.SortHow;
import org.junit.Ignore;
import org.junit.Test;
import org.threeten.bp.LocalTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;
import retrofit2.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
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
        User user = executeCall(getTrakt().users().profile(TestData.USER_SLUG, Extended.FULL));
        assertThat(user.username).isEqualTo(TestData.USERNAME_STRING);
        assertThat(user.isPrivate).isEqualTo(false);
        assertThat(user.name).isEqualTo(TestData.USER_REAL_NAME);
        assertThat(user.vip).isEqualTo(true);
        assertThat(user.vip_ep).isEqualTo(true);
        assertThat(user.ids.slug).isEqualTo(TestData.USERNAME_STRING);
        assertThat(user.images.avatar.full).isNotEmpty();
    }

    @Test
    public void test_collectionMovies() throws IOException {
        List<BaseMovie> movies = executeCall(
                getTrakt().users().collectionMovies(TestData.USER_SLUG, null));
        assertSyncMovies(movies, "collection");
    }

    @Test
    public void test_collectionShows() throws IOException {
        List<BaseShow> shows = executeCall(getTrakt().users().collectionShows(TestData.USER_SLUG, null));
        assertSyncShows(shows, "collection");
    }

    @Test
    public void test_lists() throws IOException {
        List<TraktList> lists = executeCall(getTrakt().users().lists(UserSlug.ME));
        for (TraktList list : lists) {
            // ensure id and a title
            assertThat(list.ids).isNotNull();
            assertThat(list.ids.trakt).isNotNull();
            assertThat(list.name).isNotEmpty();
            assertThat(list.description).isNotEmpty();
            assertThat(list.privacy).isNotNull();
            assertThat(list.display_numbers).isNotNull();
            assertThat(list.allow_comments).isNotNull();
            assertThat(list.sort_by).isNotNull();
            assertThat(list.sort_how).isNotNull();
            assertThat(list.created_at).isNotNull();
            assertThat(list.updated_at).isNotNull();
            assertThat(list.item_count).isPositive();
            assertThat(list.comment_count).isGreaterThanOrEqualTo(0);
            assertThat(list.likes).isGreaterThanOrEqualTo(0);
            assertThat(list.user).isNotNull();
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
        list.sortBy(SortBy.ADDED);
        list.sortHow(SortHow.ASC);

        // create list...
        TraktList createdList = executeCall(getTrakt().users().createList(UserSlug.ME, list));
        assertThat(createdList.ids.trakt).isNotNull();
        assertThat(createdList.name).isEqualTo(list.name);
        assertThat(createdList.description).isEqualTo(list.description);
        assertThat(createdList.privacy).isEqualTo(ListPrivacy.PUBLIC);
        assertThat(createdList.allow_comments).isEqualTo(false);
        assertThat(createdList.display_numbers).isEqualTo(false);
        assertThat(createdList.sort_by).isEqualTo(SortBy.ADDED);
        assertThat(createdList.sort_how).isEqualTo(SortHow.DESC); // Note: created list is always desc, even on web.

        // ...and delete it again
        Response deleteResponse = getTrakt().users().deleteList(UserSlug.ME,
                String.valueOf(createdList.ids.trakt)).execute();
        assertSuccessfulResponse(deleteResponse);
        assertThat(deleteResponse.code()).isEqualTo(204);
    }


    @Test
    public void test_updateList() throws IOException {
        // change name (append a new suffix that changes frequently)
        int secondOfDay = LocalTime.now().toSecondOfDay();
        TraktList list = new TraktList();
        list.name("trakt-java " + secondOfDay);

        // create list...
        TraktList updatedList = executeCall(getTrakt().users().updateList(UserSlug.ME, String.valueOf(
                TEST_LIST_WITH_ITEMS_TRAKT_ID), list));
        assertThat(updatedList.ids.trakt).isEqualTo(TEST_LIST_WITH_ITEMS_TRAKT_ID);
        assertThat(updatedList.name).isEqualTo(list.name);
    }

    @Test
    public void test_listItems() throws IOException {
        List<ListEntry> entries = executeCall(getTrakt().users().listItems(UserSlug.ME,
                String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                null));
        for (ListEntry entry : entries) {
            assertThat(entry.listed_at).isNotNull();
            assertThat(entry.id).isNotNull();
            assertThat(entry.rank).isNotNull();
            assertThat(entry.type).isNotNull();
        }
    }

    @Test
    public void test_addListItems() throws IOException {
        SyncShow show = new SyncShow().id(ShowIds.tvdb(256227));
        SyncMovie movie = new SyncMovie().id(MovieIds.tmdb(TestData.MOVIE_TMDB_ID));
        SyncPerson person = new SyncPerson().id(PersonIds.tmdb(TestData.PERSON_TMDB_ID));

        SyncItems items = new SyncItems();
        items.shows(show);
        items.movies(movie);
        items.people(person);

        // add items...
        SyncResponse response = executeCall(getTrakt().users().addListItems(UserSlug.ME,
                String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                items));

        assertThat(response.added.shows).isEqualTo(1);
        assertThat(response.added.movies).isEqualTo(1);
        assertThat(response.added.people).isEqualTo(1);

        // ...and remove them again
        response = executeCall(
                getTrakt().users().deleteListItems(UserSlug.ME, String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                        items));

        assertThat(response.deleted.shows).isEqualTo(1);
        assertThat(response.deleted.movies).isEqualTo(1);
        assertThat(response.deleted.people).isEqualTo(1);
    }

    @Test
    public void test_reorderListItems() throws IOException {
        List<ListEntry> entries = executeCall(getTrakt().users().listItems(UserSlug.ME,
                String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                null));

        // reverse order
        List<Long> newRank = new ArrayList<>();
        for (int i = entries.size() - 1; i >= 0; i--) {
            newRank.add(entries.get(i).id);
        }

        ListReorderResponse response = executeCall(getTrakt().users().reorderListItems(
                UserSlug.ME,
                String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                ListItemRank.from(newRank)
        ));
        assertThat(response.updated).isEqualTo(entries.size());
    }

    @Test
    public void test_reorderLists() throws IOException {
        List<TraktList> lists = executeCall(getTrakt().users().lists(UserSlug.ME));

        // reverse order
        List<Long> newRank = new ArrayList<>();
        for (int i = lists.size() - 1; i >= 0; i--) {
            newRank.add(lists.get(i).ids.trakt.longValue());
        }

        ListReorderResponse response = executeCall(getTrakt().users().reorderLists(
                UserSlug.ME,
                ListItemRank.from(newRank)
        ));
        assertThat(response.updated).isEqualTo(lists.size());
    }

    @Ignore("Following is now a VIP feature")
    @Test
    public void test_unfollowAndFollow() throws InterruptedException, IOException {
        // unfollow first
        UserSlug userToFollow = new UserSlug(TestData.USER_TO_FOLLOW);
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
        List<Follower> followers = executeCall(getTrakt().users().followers(TestData.USER_SLUG, null));
        for (Follower follower : followers) {
            assertThat(follower.followed_at).isNotNull();
            assertThat(follower.user).isNotNull();
        }
    }

    @Test
    public void test_following() throws IOException {
        List<Follower> following = executeCall(getTrakt().users().following(TestData.USER_SLUG, null));
        for (Follower follower : following) {
            assertThat(follower.followed_at).isNotNull();
            assertThat(follower.user).isNotNull();
        }
    }

    @Test
    public void test_friends() throws IOException {
        List<Friend> friends = executeCall(getTrakt().users().friends(TestData.USER_SLUG, null));
        for (Friend friend : friends) {
            assertThat(friend.friends_at).isNotNull();
            assertThat(friend.user).isNotNull();
        }
    }

    @Test
    public void test_historyEpisodesAndMovies() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().users().history(TestData.USER_SLUG, 1,
                        DEFAULT_PAGE_SIZE, null,
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
                getTrakt().users().history(TestData.USER_SLUG, HistoryType.EPISODES, 1,
                        DEFAULT_PAGE_SIZE, null,
                        null, null));
        for (HistoryEntry entry : history) {
            assertThat(entry.id).isGreaterThan(0);
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isEqualTo("episode");
            assertThat(entry.episode).isNotNull();
            assertThat(entry.show).isNotNull();
            System.out.println(
                    "Episode watched at date: " + entry.watched_at + entry.watched_at.toInstant().toEpochMilli());
        }
    }

    @Test
    public void test_historyMovies() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().users().history(UserSlug.ME, HistoryType.MOVIES, 1,
                        DEFAULT_PAGE_SIZE, null,
                        null, null));
        assertMovieHistory(history);
    }

    @Test
    public void test_historyItem() throws IOException {
        List<HistoryEntry> history = executeCall(getTrakt().users().history(UserSlug.ME, HistoryType.MOVIES,
                TestData.MOVIE_WATCHED_TRAKT_ID, 1,
                DEFAULT_PAGE_SIZE, null,
                OffsetDateTime.of(2016, 8, 3, 9, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2016, 8, 3, 10, 0, 0, 0, ZoneOffset.UTC)));
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
                getTrakt().users().ratingsMovies(TestData.USER_SLUG, RatingsFilter.ALL,
                        null));
        assertRatedEntities(ratedMovies);
    }

    @Test
    public void test_ratingsMovies_filtered() throws IOException {
        List<RatedMovie> ratedMovies = executeCall(getTrakt().users().ratingsMovies(TestData.USER_SLUG,
                RatingsFilter.TOTALLYNINJA,
                null));
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(Rating.TOTALLYNINJA);
        }
    }

    @Test
    public void test_ratingsShows() throws IOException {
        List<RatedShow> ratedShows = executeCall(
                getTrakt().users().ratingsShows(TestData.USER_SLUG, RatingsFilter.ALL,
                        null));
        assertRatedEntities(ratedShows);
    }

    @Test
    public void test_ratingsSeasons() throws IOException {
        List<RatedSeason> ratedSeasons = executeCall(
                getTrakt().users().ratingsSeasons(TestData.USER_SLUG, RatingsFilter.ALL,
                        null));
        assertRatedEntities(ratedSeasons);
    }

    @Test
    public void test_ratingsEpisodes() throws IOException {
        List<RatedEpisode> ratedEpisodes = executeCall(
                getTrakt().users().ratingsEpisodes(TestData.USER_SLUG, RatingsFilter.ALL,
                        null));
        assertRatedEntities(ratedEpisodes);
    }

    @Test
    public void test_watchlistMovies() throws IOException {
        List<BaseMovie> movies = executeCall(getTrakt().users().watchlistMovies(UserSlug.ME,
                null));
        assertSyncMovies(movies, "watchlist");
    }

    @Test
    public void test_watchlistShows() throws IOException {
        List<BaseShow> shows = executeCall(getTrakt().users().watchlistShows(UserSlug.ME,
                null));
        for (BaseShow show : shows) {
            assertThat(show.show).isNotNull();
            assertThat(show.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistSeasons() throws IOException {
        List<WatchlistedSeason> seasons = executeCall(getTrakt().users().watchlistSeasons(UserSlug.ME,
                null));
        for (WatchlistedSeason season : seasons) {
            assertThat(season.season).isNotNull();
            assertThat(season.show).isNotNull();
            assertThat(season.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistEpisodes() throws IOException {
        List<WatchlistedEpisode> episodes = executeCall(getTrakt().users().watchlistEpisodes(UserSlug.ME,
                null));
        for (WatchlistedEpisode episode : episodes) {
            assertThat(episode.episode).isNotNull();
            assertThat(episode.show).isNotNull();
            assertThat(episode.listed_at).isNotNull();
        }
    }


    @Test
    public void test_watchedMovies() throws IOException {
        List<BaseMovie> watchedMovies = executeCall(getTrakt().users().watchedMovies(TestData.USER_SLUG,
                null));
        assertSyncMovies(watchedMovies, "watched");
    }

    @Test
    public void test_watchedShows() throws IOException {
        List<BaseShow> watchedShows = executeCall(getTrakt().users().watchedShows(TestData.USER_SLUG,
                null));
        assertSyncShows(watchedShows, "watched");
    }

}
