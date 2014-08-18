package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.EpisodeCheckin;
import com.uwetrottmann.trakt.v2.entities.EpisodeIds;
import com.uwetrottmann.trakt.v2.entities.Movie;
import com.uwetrottmann.trakt.v2.entities.MovieCheckin;
import com.uwetrottmann.trakt.v2.entities.MovieIds;
import com.uwetrottmann.trakt.v2.entities.ShareSettings;
import org.junit.Test;
import retrofit.client.Response;

public class CheckinTest extends BaseTestCase {

    @Test
    public void test_checkin_episode() {
        EpisodeCheckin checkin = new EpisodeCheckin();
        checkin.episode = new Episode();
        checkin.episode.ids = new EpisodeIds();
        checkin.episode.ids.trakt = 16;
        checkin.message = "This is a toasty episode!";
        checkin.sharing = new ShareSettings();
        checkin.sharing.facebook = true;
        checkin.app_version = "trakt-java-4";
        checkin.app_date = "2014";

        Response response = getTraktWithAuth().checkin().checkin(checkin);
    }

    @Test
    public void test_checkin_movie() {
        MovieCheckin checkin = new MovieCheckin();
        checkin.movie = new Movie();
        checkin.movie.ids = new MovieIds();
        checkin.movie.ids.trakt = TestData.MOVIE_TRAKT_ID;
        checkin.message = "This is a toasty movie!";
        checkin.sharing = new ShareSettings();
        checkin.sharing.facebook = true;
        checkin.app_version = "trakt-java-4";
        checkin.app_date = "2014";

        Response response = getTraktWithAuth().checkin().checkin(checkin);
    }

}
