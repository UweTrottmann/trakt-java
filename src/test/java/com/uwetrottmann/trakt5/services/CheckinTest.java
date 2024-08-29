/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.junit.Test;
import org.threeten.bp.OffsetDateTime;
import retrofit2.Call;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class CheckinTest extends BaseTestCase {

    private static final String APP_VERSION = "trakt-java-4";
    private static final String APP_DATE = "2014-10-15";

    @Nonnull
    public <T> T executeCheckInCall(@Nonnull Call<T> call) throws IOException, InterruptedException {
        Response<T> response = call.execute();
        if (!response.isSuccessful()) {
            if (getTrakt().checkForCheckinError(response) != null) {
                fail("Check-in still in progress, may be left over from failed test");
            } else if (response.code() == 401) {
                fail("Authorization required, supply a valid OAuth access token: "
                        + response.code() + " " + response.message());
            } else {
                fail("Request failed: " + response.code() + " " + response.message());
            }
        }
        T body = response.body();
        if (body != null) {
            // Give the server some time to update its state for next tests
            Thread.sleep(500);
            return body;
        } else {
            throw new IllegalStateException("Body should not be null for successful response");
        }
    }

    @Test
    public void test_checkin_episode() throws IOException, InterruptedException {
        EpisodeCheckin checkin = buildEpisodeCheckin();

        EpisodeCheckinResponse response = executeCheckInCall(getTrakt().checkin().checkin(checkin));

        // delete check-in first
        test_checkin_delete();

        assertEpisodeCheckin(response);
    }

    @Test
    public void test_checkin_episode_without_ids() throws IOException, InterruptedException {
        EpisodeCheckin checkin = buildEpisodeCheckinWithoutIds();

        EpisodeCheckinResponse response = executeCheckInCall(getTrakt().checkin().checkin(checkin));

        // delete check-in first
        test_checkin_delete();

        assertEpisodeCheckin(response);
    }

    private void assertEpisodeCheckin(EpisodeCheckinResponse response) {
        assertThat(response).isNotNull();
        // episode should be over in less than an hour
        assertThat(response.watched_at).isNotNull();
        assertThat(response.watched_at.isBefore(OffsetDateTime.now().plusHours(1))).isTrue();
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
    public void test_checkin_movie() throws IOException, InterruptedException {
        MovieCheckin checkin = buildMovieCheckin();

        MovieCheckinResponse response = executeCheckInCall(getTrakt().checkin().checkin(checkin));
        assertThat(response).isNotNull();
        // movie should be over in less than 3 hours
        assertThat(response.watched_at).isNotNull();
        assertThat(response.watched_at.isBefore(OffsetDateTime.now().plusHours(3))).isTrue();
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
    public void test_checkin_blocked() throws IOException, InterruptedException {
        Checkin checkin = getTrakt().checkin();

        EpisodeCheckin episodeCheckin = buildEpisodeCheckin();
        executeCheckInCall(checkin.checkin(episodeCheckin));

        MovieCheckin movieCheckin = buildMovieCheckin();
        Response<MovieCheckinResponse> responseBlocked = checkin.checkin(movieCheckin).execute();
        if (responseBlocked.code() == 401) {
            fail("Authorization required, supply a valid OAuth access token: "
                    + responseBlocked.code() + " " + responseBlocked.message());
        }
        if (responseBlocked.code() != 409) {
            fail("Check-in was not blocked");
        }
        CheckinError checkinError = getTrakt().checkForCheckinError(responseBlocked);
        // episode check in should block until episode duration has passed
        assertThat(checkinError).isNotNull();
        assertThat(checkinError.expires_at).isNotNull();
        assertThat(checkinError.expires_at.isBefore(OffsetDateTime.now().plusHours(1))).isTrue();

        // clean the check in
        test_checkin_delete();
    }


    @Test
    public void test_checkin_delete() throws IOException, InterruptedException {
        // tries to delete a check in even if none active
        Response<Void> response = getTrakt().checkin().deleteActiveCheckin().execute();
        assertSuccessfulResponse(response);
        assertThat(response.code()).isEqualTo(204);
        // Give the server some time to update its state for next tests
        Thread.sleep(500);
    }
}
