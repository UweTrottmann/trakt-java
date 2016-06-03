package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.CheckinError;
import com.uwetrottmann.trakt5.entities.EpisodeCheckin;
import com.uwetrottmann.trakt5.entities.EpisodeCheckinResponse;
import com.uwetrottmann.trakt5.entities.EpisodeIds;
import com.uwetrottmann.trakt5.entities.MovieCheckin;
import com.uwetrottmann.trakt5.entities.MovieCheckinResponse;
import com.uwetrottmann.trakt5.entities.MovieIds;
import com.uwetrottmann.trakt5.entities.ShareSettings;
import com.uwetrottmann.trakt5.entities.Show;
import com.uwetrottmann.trakt5.entities.SyncEpisode;
import com.uwetrottmann.trakt5.entities.SyncMovie;
import org.joda.time.DateTime;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.jodatime.api.Assertions.assertThat;


public class CheckinTest extends BaseTestCase {

    private static final String APP_VERSION = "trakt-java-4";
    private static final String APP_DATE = "2014-10-15";

    private void assertNoCheckinInProgress(Response response) throws IOException {
        if (!response.isSuccessful() && getTrakt().handleCheckinError(response) != null) {
            fail("Check-in still in progress, may be left over from failed test");
        }
    }

    @Test
    public void test_checkin_episode() throws IOException {
        EpisodeCheckin checkin = buildEpisodeCheckin();

        Response<EpisodeCheckinResponse> response = getTrakt().checkin().checkin(checkin).execute();
        assertNoCheckinInProgress(response);
        assertSuccessfulResponse(response);

        // delete check-in first
        test_checkin_delete();

        assertEpisodeCheckin(response.body());
    }

    @Test
    public void test_checkin_episode_without_ids() throws IOException {
        EpisodeCheckin checkin = buildEpisodeCheckinWithoutIds();

        Response<EpisodeCheckinResponse> response = getTrakt().checkin().checkin(checkin).execute();
        assertNoCheckinInProgress(response);
        assertSuccessfulResponse(response);

        // delete check-in first
        test_checkin_delete();

        assertEpisodeCheckin(response.body());
    }

    private void assertEpisodeCheckin(EpisodeCheckinResponse response) {
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
        return new EpisodeCheckin.Builder(
                new SyncEpisode().season(TestData.EPISODE_SEASON).number(TestData.EPISODE_NUMBER), APP_VERSION,
                APP_DATE)
                .show(show)
                .message("This is a toasty episode!")
                .build();
    }

    @Test
    public void test_checkin_movie() throws IOException {
        MovieCheckin checkin = buildMovieCheckin();

        Response<MovieCheckinResponse> response = getTrakt().checkin().checkin(checkin).execute();
        assertNoCheckinInProgress(response);
        assertSuccessfulResponse(response);
        assertThat(response).isNotNull();
        // movie should be over in less than 3 hours
        assertThat(response.body().watched_at).isBefore(new DateTime().plusHours(3));
        MoviesTest.assertTestMovie(response.body().movie);

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
    public void test_checkin_blocked() throws IOException {
        Checkin checkin = getTrakt().checkin();

        EpisodeCheckin episodeCheckin = buildEpisodeCheckin();
        Response<EpisodeCheckinResponse> response = checkin.checkin(episodeCheckin).execute();
        assertNoCheckinInProgress(response);
        assertSuccessfulResponse(response);

        MovieCheckin movieCheckin = buildMovieCheckin();
        Response<MovieCheckinResponse> responseBlocked = checkin.checkin(movieCheckin).execute();
        if (response.code() == 401) {
            fail("Authorization required, supply a valid OAuth access token: "
                    + response.code() + " " + response.message());
        }
        if (responseBlocked.code() != 409) {
            fail("Check-in was not blocked");
        }
        CheckinError checkinError = getTrakt().handleCheckinError(responseBlocked);
        // episode check in should block until episode duration has passed
        assertThat(checkinError.expires_at.isBefore(new DateTime().plusHours(1)));

        // clean the check in
        test_checkin_delete();
    }


    @Test
    public void test_checkin_delete() throws IOException {
        // tries to delete a check in even if none active
        Response response = getTrakt().checkin().deleteActiveCheckin().execute();
        assertSuccessfulResponse(response);
        assertThat(response.code()).isEqualTo(204);
    }
}
