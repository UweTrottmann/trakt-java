package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.CollectedMovie;
import com.uwetrottmann.trakt.v2.entities.CollectedShow;
import com.uwetrottmann.trakt.v2.entities.EpisodeHistoryEntry;
import com.uwetrottmann.trakt.v2.entities.MovieHistoryEntry;
import com.uwetrottmann.trakt.v2.entities.Settings;
import com.uwetrottmann.trakt.v2.entities.User;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersTest extends BaseTestCase {

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
        User user = getTrakt().users().profile(TestData.USERNAME);
        assertThat(user.username).isEqualTo(TestData.USERNAME);
        assertThat(user.isPrivate).isEqualTo(false);
        assertThat(user.name).isEqualTo(TestData.USER_NAME);
        assertThat(user.vip).isEqualTo(true);
    }

    @Test
    public void test_collectionMovies() throws OAuthUnauthorizedException {
        List<CollectedMovie> movies = getTrakt().users().collectionMovies(TestData.USERNAME);
        assertCollectedMovies(movies);
    }

    @Test
    public void test_collectionShows() throws OAuthUnauthorizedException {
        List<CollectedShow> shows = getTrakt().users().collectionShows(TestData.USERNAME);
        assertCollectedShows(shows);
    }

    @Test
    public void test_historyEpisodes() throws OAuthUnauthorizedException {
        List<EpisodeHistoryEntry> history = getTrakt().users().historyEpisodes(TestData.USERNAME, 1, DEFAULT_PAGE_SIZE);
        for (EpisodeHistoryEntry entry : history) {
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.episode).isNotNull();
            assertThat(entry.show).isNotNull();
        }
    }

    @Test
    public void test_historyMovies() throws OAuthUnauthorizedException {
        List<MovieHistoryEntry> history = getTrakt().users().historyMovies(TestData.USERNAME, 1, DEFAULT_PAGE_SIZE);
        for (MovieHistoryEntry entry : history) {
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.movie).isNotNull();
        }
    }

}
