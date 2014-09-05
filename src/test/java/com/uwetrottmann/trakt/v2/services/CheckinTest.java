package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.CheckinError;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.EpisodeCheckin;
import com.uwetrottmann.trakt.v2.entities.EpisodeCheckinResponse;
import com.uwetrottmann.trakt.v2.entities.EpisodeIds;
import com.uwetrottmann.trakt.v2.entities.Movie;
import com.uwetrottmann.trakt.v2.entities.MovieCheckin;
import com.uwetrottmann.trakt.v2.entities.MovieCheckinResponse;
import com.uwetrottmann.trakt.v2.entities.MovieIds;
import com.uwetrottmann.trakt.v2.entities.ShareSettings;
import org.joda.time.DateTime;
import org.junit.Test;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.jodatime.api.Assertions.assertThat;


public class CheckinTest extends BaseTestCase {

    @Test
    public void test_checkin_episode() {
        EpisodeCheckin checkin = buildEpisodeCheckin();

        EpisodeCheckinResponse response = getTrakt().checkin().checkin(checkin);
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
        checkin.episode.ids = new EpisodeIds();
        checkin.episode.ids.trakt = 16;
        checkin.message = "This is a toasty episode!";
        checkin.sharing = new ShareSettings();
        checkin.sharing.facebook = true;
        checkin.app_version = "trakt-java-4";
        checkin.app_date = "2014";
        return checkin;
    }

    @Test
    public void test_checkin_movie() {
        MovieCheckin checkin = buildMovieCheckin();

        MovieCheckinResponse response = getTrakt().checkin().checkin(checkin);
        assertThat(response).isNotNull();
        // movie should be over in less than 3 hours
        assertThat(response.watched_at).isBefore(new DateTime().plusHours(3));
        MoviesTest.assertTestMovie(response.movie);

        test_checkin_delete();
    }

    private MovieCheckin buildMovieCheckin() {
        MovieCheckin checkin = new MovieCheckin();
        checkin.movie = new Movie();
        checkin.movie.ids = new MovieIds();
        checkin.movie.ids.trakt = TestData.MOVIE_TRAKT_ID;
        checkin.message = "This is a toasty movie!";
        checkin.sharing = new ShareSettings();
        checkin.sharing.facebook = true;
        checkin.app_version = "trakt-java-4";
        checkin.app_date = "2014";
        return checkin;
    }

    @Test
    public void test_checkin_blocked() {
        Checkin checkin = getTrakt().checkin();

        EpisodeCheckin episodeCheckin = buildEpisodeCheckin();
        checkin.checkin(episodeCheckin);

        MovieCheckin movieCheckin = buildMovieCheckin();
        try {
            checkin.checkin(movieCheckin);
        } catch (RetrofitError e) {
            assertThat(e.getResponse().getStatus()).isEqualTo(409);
            // episode check in should block until episode duration has passed
            CheckinError checkinError = (CheckinError) e.getBodyAs(CheckinError.class);
            assertThat(checkinError.expires_at).isBefore(new DateTime().plusHours(1));
        }

        // clean the check in
        test_checkin_delete();
    }


    @Test
    public void test_checkin_delete() {
        // tries to delete a check in even if none active
        Response response1 = getTrakt().checkin().deleteActiveCheckin();
        assertThat(response1.getStatus()).isEqualTo(204);
    }
}
