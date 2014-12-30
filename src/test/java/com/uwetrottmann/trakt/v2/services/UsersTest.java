package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.HistoryEntry;
import com.uwetrottmann.trakt.v2.entities.BaseMovie;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.ListEntry;
import com.uwetrottmann.trakt.v2.entities.MovieIds;
import com.uwetrottmann.trakt.v2.entities.RatedEpisode;
import com.uwetrottmann.trakt.v2.entities.RatedMovie;
import com.uwetrottmann.trakt.v2.entities.RatedSeason;
import com.uwetrottmann.trakt.v2.entities.RatedShow;
import com.uwetrottmann.trakt.v2.entities.Settings;
import com.uwetrottmann.trakt.v2.entities.ShowIds;
import com.uwetrottmann.trakt.v2.entities.SyncItems;
import com.uwetrottmann.trakt.v2.entities.SyncMovie;
import com.uwetrottmann.trakt.v2.entities.SyncResponse;
import com.uwetrottmann.trakt.v2.entities.SyncShow;
import com.uwetrottmann.trakt.v2.entities.User;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.enums.ListPrivacy;
import com.uwetrottmann.trakt.v2.enums.RatingsFilter;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import org.joda.time.DateTime;
import org.junit.Test;
import retrofit.client.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersTest extends BaseTestCase {

    private static final int TEST_LIST_WITH_ITEMS_TRAKT_ID = 619;

    @Test
    public void test_getSettings() throws OAuthUnauthorizedException {
        Settings settings = getTrakt().users().settings();
        assertThat(settings.user).isNotNull();
        assertThat(settings.account).isNotNull();
        assertThat(settings.connections).isNotNull();
        assertThat(settings.sharing_text).isNotNull();
    }

    @Test
    public void test_profile() throws OAuthUnauthorizedException {
        User user = getTrakt().users().profile(TestData.USERNAME, Extended.FULLIMAGES);
        assertThat(user.username).isEqualTo(TestData.USERNAME);
        assertThat(user.isPrivate).isEqualTo(false);
        assertThat(user.name).isEqualTo(TestData.USER_REAL_NAME);
        assertThat(user.vip).isEqualTo(true);
    }

    @Test
    public void test_collectionMovies() throws OAuthUnauthorizedException {
        List<BaseMovie> movies = getTrakt().users().collectionMovies(TestData.USERNAME, Extended.DEFAULT_MIN);
        assertSyncMovies(movies, "collection");
    }

    @Test
    public void test_collectionShows() throws OAuthUnauthorizedException {
        List<BaseShow> shows = getTrakt().users().collectionShows(TestData.USERNAME, Extended.DEFAULT_MIN);
        assertSyncShows(shows, "collection");
    }

    @Test
    public void test_lists() throws OAuthUnauthorizedException {
        List<com.uwetrottmann.trakt.v2.entities.List> lists = getTrakt().users().lists("me");
        for (com.uwetrottmann.trakt.v2.entities.List list : lists) {
            // ensure id and a title
            assertThat(list.ids).isNotNull();
            assertThat(list.ids.trakt).isNotNull();
            assertThat(list.name).isNotEmpty();
        }
    }

    @Test
    public void test_createList() throws OAuthUnauthorizedException {
        com.uwetrottmann.trakt.v2.entities.List list = new com.uwetrottmann.trakt.v2.entities.List();
        list.name("trakt-java");
        list.description("trakt-java test list");
        list.privacy(ListPrivacy.PUBLIC);
        list.allowComments(false);
        list.displayNumbers(false);

        // create list...
        com.uwetrottmann.trakt.v2.entities.List createdList = getTrakt().users().createList("me", list);
        assertThat(createdList.ids.trakt).isNotNull();
        assertThat(createdList.name).isEqualTo(list.name);
        assertThat(createdList.description).isEqualTo(list.description);

        // ...and delete it again
        Response response = getTrakt().users().deleteList("me", String.valueOf(createdList.ids.trakt));
        assertThat(response.getStatus()).isEqualTo(204);
    }


    @Test
    public void test_updateList() throws OAuthUnauthorizedException {
        // change name (append a new suffix that changes frequently)
        int secondOfDay = new DateTime().getSecondOfDay();
        com.uwetrottmann.trakt.v2.entities.List list = new com.uwetrottmann.trakt.v2.entities.List();
        list.name("trakt-java " + secondOfDay);

        // create list...
        com.uwetrottmann.trakt.v2.entities.List updatedList = getTrakt().users().updateList("me", String.valueOf(
                TEST_LIST_WITH_ITEMS_TRAKT_ID), list);
        assertThat(updatedList.ids.trakt).isEqualTo(TEST_LIST_WITH_ITEMS_TRAKT_ID);
        assertThat(updatedList.name).isEqualTo(list.name);
    }

    @Test
    public void test_listItems() throws OAuthUnauthorizedException {
        List<ListEntry> entries = getTrakt().users().listItems("me", String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                Extended.DEFAULT_MIN);
        for (ListEntry entry : entries) {
            assertThat(entry.listed_at).isNotNull();
        }
    }

    @Test
    public void test_addListItems() throws OAuthUnauthorizedException {
        SyncShow show = new SyncShow().id(ShowIds.tvdb(256227));
        SyncMovie movie = new SyncMovie().id(MovieIds.tmdb(TestData.MOVIE_TMDB_ID));

        SyncItems items = new SyncItems();
        items.shows(show);
        items.movies(movie);

        // add items...
        SyncResponse response = getTrakt().users().addListItems("me", String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID),
                items);
        assertThat(response.added.shows).isEqualTo(1);
        assertThat(response.added.movies).isEqualTo(1);

        // ...and remove them again
        response = getTrakt().users().deleteListItems("me", String.valueOf(TEST_LIST_WITH_ITEMS_TRAKT_ID), items);
        assertThat(response.deleted.shows).isEqualTo(1);
        assertThat(response.deleted.movies).isEqualTo(1);
    }

    @Test
    public void test_historyEpisodes() throws OAuthUnauthorizedException {
        List<HistoryEntry> history = getTrakt().users().historyEpisodes(TestData.USERNAME, 1, DEFAULT_PAGE_SIZE,
                Extended.DEFAULT_MIN);
        for (HistoryEntry entry : history) {
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.episode).isNotNull();
            assertThat(entry.show).isNotNull();
        }
    }

    @Test
    public void test_historyMovies() throws OAuthUnauthorizedException {
        List<HistoryEntry> history = getTrakt().users().historyMovies(TestData.USERNAME, 1, DEFAULT_PAGE_SIZE,
                Extended.DEFAULT_MIN);
        for (HistoryEntry entry : history) {
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.movie).isNotNull();
        }
    }

    @Test
    public void test_ratingsMovies() throws OAuthUnauthorizedException {
        List<RatedMovie> ratedMovies = getTrakt().users().ratingsMovies(TestData.USERNAME, RatingsFilter.ALL,
                Extended.DEFAULT_MIN);
        assertRatedEntities(ratedMovies);
    }

    @Test
    public void test_ratingsMovies_filtered() throws OAuthUnauthorizedException {
        List<RatedMovie> ratedMovies = getTrakt().users().ratingsMovies(TestData.USERNAME, RatingsFilter.TOTALLYNINJA,
                Extended.DEFAULT_MIN);
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(10);
        }
    }

    @Test
    public void test_ratingsShows() throws OAuthUnauthorizedException {
        List<RatedShow> ratedShows = getTrakt().users().ratingsShows(TestData.USERNAME, RatingsFilter.ALL,
                Extended.DEFAULT_MIN);
        assertRatedEntities(ratedShows);
    }

    @Test
    public void test_ratingsSeasons() throws OAuthUnauthorizedException {
        List<RatedSeason> ratedSeasons = getTrakt().users().ratingsSeasons(TestData.USERNAME, RatingsFilter.ALL,
                Extended.DEFAULT_MIN);
        assertRatedEntities(ratedSeasons);
    }

    @Test
    public void test_ratingsEpisodes() throws OAuthUnauthorizedException {
        List<RatedEpisode> ratedEpisodes = getTrakt().users().ratingsEpisodes(TestData.USERNAME, RatingsFilter.ALL,
                Extended.DEFAULT_MIN);
        assertRatedEntities(ratedEpisodes);
    }


    @Test
    public void test_watchedMovies() throws OAuthUnauthorizedException {
        List<BaseMovie> watchedMovies = getTrakt().users().watchedMovies(TestData.USERNAME, Extended.DEFAULT_MIN);
        assertSyncMovies(watchedMovies, "watched");
    }

    @Test
    public void test_watchedShows() throws OAuthUnauthorizedException {
        List<BaseShow> watchedShows = getTrakt().users().watchedShows(TestData.USERNAME, Extended.DEFAULT_MIN);
        assertSyncShows(watchedShows, "watched");
    }

}
