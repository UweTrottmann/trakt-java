package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.*;
import com.uwetrottmann.trakt.v2.exceptions.CheckinInProgressException;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import org.joda.time.DateTime;
import org.junit.Test;
import retrofit.client.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.jodatime.api.Assertions.assertThat;


public class CheckinTest extends BaseTestCase {

    private static final String APP_VERSION = "trakt-java-4";
    private static final String APP_DATE = "2014-10-15";

    @Test
    public void test_checkin_episode() throws OAuthUnauthorizedException {
        EpisodeCheckin checkin = buildEpisodeCheckin();

        EpisodeCheckinResponse response = null;
        try {
            response = getTrakt().checkin().checkin(checkin);
        } catch (CheckinInProgressException e) {
            fail("Check-in still in progress, may be left over from failed test");
        }

        // delete check-in first
        test_checkin_delete();

        assertThat(response).isNotNull();
        // episode should be over in less than an hour
        assertThat(response.watched_at).isBefore(new DateTime().plusHours(1));
        assertThat(response.episode).isNotNull();
        assertThat(response.episode.ids).isNotNull();
        assertThat(response.episode.ids.trakt).isEqualTo(TestData.EPISODE_TRAKT_ID);
        assertThat(response.episode.ids.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID);
        assertThat(response.show).isNotNull();
    }

    @Test
    public void test_checkin_episode_without_ids() throws OAuthUnauthorizedException {
        EpisodeCheckin checkin = buildEpisodeCheckinWithoutIds();

        EpisodeCheckinResponse response = null;
        try {
            response = getTrakt().checkin().checkin(checkin);
        } catch (CheckinInProgressException e) {
            fail("Check-in still in progress, may be left over from failed test");
        }

        // delete check-in first
        test_checkin_delete();

        assertThat(response).isNotNull();
        // episode should be over in less than an hour
        assertThat(response.watched_at).isBefore(new DateTime().plusHours(1));
        assertThat(response.episode).isNotNull();
        assertThat(response.episode.ids).isNotNull();
        assertThat(response.episode.ids.trakt).isEqualTo(TestData.EPISODE_TRAKT_ID);
        assertThat(response.episode.ids.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID);
        assertThat(response.show).isNotNull();
    }

    private static EpisodeCheckin buildEpisodeCheckin() {
        return new EpisodeCheckin.Builder(new SyncEpisode().id(EpisodeIds.tvdb(TestData.EPISODE_TVDB_ID)), APP_VERSION,
                APP_DATE)
                .message("This is a toasty episode!")
                .build();
    }

    private static EpisodeCheckin buildEpisodeCheckinWithoutIds() {
        Show show = new Show();
        show.title = TestData.SHOW_TITLE;
        return new EpisodeCheckin.Builder(new SyncEpisode().season(TestData.EPISODE_SEASON).number(TestData.EPISODE_NUMBER), APP_VERSION,
                APP_DATE)
                .show(show)
                .message("This is a toasty episode!")
                .build();
    }

    @Test
    public void test_checkin_movie() throws OAuthUnauthorizedException {
        MovieCheckin checkin = buildMovieCheckin();

        MovieCheckinResponse response = null;
        try {
            response = getTrakt().checkin().checkin(checkin);
        } catch (CheckinInProgressException e) {
            fail("Check-in still in progress, may be left over from failed test");
        }
        assertThat(response).isNotNull();
        // movie should be over in less than 3 hours
        assertThat(response.watched_at).isBefore(new DateTime().plusHours(3));
        MoviesTest.assertTestMovie(response.movie);

        test_checkin_delete();
    }

    private MovieCheckin buildMovieCheckin() {
        ShareSettings shareSettings = new ShareSettings();
        shareSettings.facebook = true;
        return new MovieCheckin.Builder(new SyncMovie().id(MovieIds.trakt(TestData.MOVIE_TRAKT_ID)), APP_VERSION,
                APP_DATE)
                .message("This is a toasty movie!")
                .sharing(shareSettings)
                .build();
    }

    @Test
    public void test_checkin_blocked() throws OAuthUnauthorizedException {
        Checkin checkin = getTrakt().checkin();

        EpisodeCheckin episodeCheckin = buildEpisodeCheckin();
        try {
            checkin.checkin(episodeCheckin);
        } catch (CheckinInProgressException e) {
            fail("Check-in still in progress, may be left over from failed test");
        }

        MovieCheckin movieCheckin = buildMovieCheckin();
        try {
            checkin.checkin(movieCheckin);
            fail("Check-in was not blocked");
        } catch (CheckinInProgressException e) {
            // episode check in should block until episode duration has passed
            assertThat(e.getExpiresAt()).isBefore(new DateTime().plusHours(1));
        }

        // clean the check in
        test_checkin_delete();
    }


    @Test
    public void test_checkin_delete() throws OAuthUnauthorizedException {
        // tries to delete a check in even if none active
        Response response1 = getTrakt().checkin().deleteActiveCheckin();
        assertThat(response1.getStatus()).isEqualTo(204);
    }
}
