package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.EpisodeCheckin;
import com.uwetrottmann.trakt.v2.entities.EpisodeCheckinResponse;
import com.uwetrottmann.trakt.v2.entities.EpisodeIds;
import com.uwetrottmann.trakt.v2.entities.Movie;
import com.uwetrottmann.trakt.v2.entities.MovieCheckin;
import com.uwetrottmann.trakt.v2.entities.MovieCheckinResponse;
import com.uwetrottmann.trakt.v2.entities.MovieIds;
import com.uwetrottmann.trakt.v2.entities.ShareSettings;
import com.uwetrottmann.trakt.v2.exceptions.CheckinInProgressException;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import org.joda.time.DateTime;
import org.junit.Test;
import retrofit.client.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.jodatime.api.Assertions.assertThat;


public class CheckinTest extends BaseTestCase {

    @Test
    public void test_checkin_episode() throws OAuthUnauthorizedException {
        EpisodeCheckin checkin = buildEpisodeCheckin();

        EpisodeCheckinResponse response = null;
        try {
            response = getTrakt().checkin().checkin(checkin);
        } catch (CheckinInProgressException e) {
            fail("Check-in still in progress, may be left over from failed test");
        }
        assertThat(response).isNotNull();
        // episode should be over in less than an hour
        assertThat(response.watched_at).isBefore(new DateTime().plusHours(1));
        assertThat(response.episode).isNotNull();
        assertThat(response.episode.ids).isNotNull();
        assertThat(response.episode.ids.trakt).isEqualTo(16);
        assertThat(response.show).isNotNull();

        test_checkin_delete();
    }

    private static EpisodeCheckin buildEpisodeCheckin() {
        EpisodeCheckin checkin = new EpisodeCheckin();
        checkin.episode = new Episode();
        checkin.episode.ids = EpisodeIds.trakt(16);
        checkin.message = "This is a toasty episode!";
        checkin.sharing = new ShareSettings();
        checkin.sharing.facebook = true;
        checkin.app_version = "trakt-java-4";
        checkin.app_date = "2014";
        return checkin;
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
        MovieCheckin checkin = new MovieCheckin();
        checkin.movie = new Movie();
        checkin.movie.ids = MovieIds.trakt(TestData.MOVIE_TRAKT_ID);
        checkin.message = "This is a toasty movie!";
        checkin.sharing = new ShareSettings();
        checkin.sharing.facebook = true;
        checkin.app_version = "trakt-java-4";
        checkin.app_date = "2014";
        return checkin;
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
