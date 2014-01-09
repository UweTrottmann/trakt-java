package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Comment;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.Stats;
import com.jakewharton.trakt.entities.TvEntity;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.TvShowEpisode;
import com.jakewharton.trakt.enumerations.DayOfTheWeek;
import com.jakewharton.trakt.enumerations.Extended2;
import com.jakewharton.trakt.enumerations.Status;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShowServiceTest extends BaseTestCase {

    @Test
    public void test_comments() {
        List<Comment> shouts = getManager().showService().comments("the-walking-dead");
        assertThat(shouts).isNotEmpty();
        assertThat(shouts.get(0)).isNotNull();
    }

    @Test
    public void test_episodeComments() {
        List<Comment> comments = getManager().showService().episodeComments("the-walking-dead", 1,
                1);
        assertThat(comments).isNotEmpty();
        assertThat(comments.get(0)).isNotNull();
    }

    @Test
    public void test_episodeLibrary() {
        Response response = getManager().showService().episodeLibrary(new ShowService.Episodes(
                153021, 1, 1
        ));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_episodeSeen() {
        Response response = getManager().showService().episodeSeen(new ShowService.Episodes(
                153021, 1, 1
        ));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_episodesSeen() {
        List<ShowService.Episodes.Episode> episodes = new ArrayList<ShowService.Episodes.Episode>();
        episodes.add(new ShowService.Episodes.Episode(1, 1));
        episodes.add(new ShowService.Episodes.Episode(1, 2));
        Response response = getManager().showService().episodeSeen(new ShowService.Episodes(
                153021, episodes
        ));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_episodeStats() {
        Stats stats = getManager().showService().episodeStats("the-walking-dead", 1, 1);
        assertThat(stats).isNotNull();
        assertThat(stats.ratings).isNotNull();
        assertThat(stats.ratings.distribution).isNotNull();
        assertThat(stats.scrobbles).isNotNull();
        assertThat(stats.checkins).isNotNull();
        assertThat(stats.collection).isNotNull();
        assertThat(stats.lists).isNotNull();
        assertThat(stats.comments).isNotNull();
    }

    @Test
    public void test_episodeSummary() {
        TvEntity entity = getManager().showService().episodeSummary("the-league", 1, 1);
        assertThat(entity).isNotNull();

        // TODO If someone has the time: port to assertThat()
        TvShow show = entity.show;
        assertThat(show).isNotNull();
        assertThat(show.title).isEqualTo("The League");
        assertNotNull("Show year was null.", show.year);
        assertEquals("Show year does not match.", 2009, show.year.intValue());
        assertNotNull("Show URL was null.", show.url);
        assertThat(show.first_aired_utc).isEqualTo(1256869800);
        assertEquals("Show country does not match.", "United States", show.country);
        assertNotNull("Show overview was null.", show.overview);
        assertNotNull("Show runtime was null.", show.runtime);
        assertEquals("Show runtime does not match.", 30, show.runtime.intValue());
        assertThat(show.network).isEqualTo("FXX");
        assertThat(show.airDay).isEqualTo(DayOfTheWeek.Wednesday);
        assertEquals("Show air time does not mtach.", "10:30pm", show.airTime);
        assertEquals("Show certification does not match.", "TV-MA", show.certification);
        assertEquals("Show IMDB ID does not match.", "tt1480684", show.imdb_id);
        assertThat(show.tvdb_id).isEqualTo(114701);
        assertThat(show.tvrage_id).isEqualTo(24173);
        assertThat(show.images).isNotNull();
        assertThat(show.ratings).isNotNull();
        assertNotNull("Show in watchlist boolean was null.", show.inWatchlist);
        assertEquals("Show in watchlist boolean does not match.", false,
                show.inWatchlist.booleanValue());

        TvShowEpisode episode = entity.episode;
        assertNotNull("Episode was null.");
        assertNotNull("Episode season was null.", episode.season);
        assertEquals("Episode season does not match.", 1, episode.season);
        assertNotNull("Episode number was null.", episode.number);
        assertEquals("Episode number does not match.", 1, episode.number);
        assertEquals("Episode title does not match.", "The Draft", episode.title);
        assertNotNull("Episode overview was null.", episode.overview);
        assertNotNull("Episode URL was null.", episode.url);
        assertThat(episode.first_aired_utc).isEqualTo(1256869800);
        assertNotNull("Episode images was null.", episode.images); //TODO own test cases
        assertNotNull("Episode ratings was null.", episode.ratings); //TODO own test cases
        assertNotNull("Episode watched boolean was null.", episode.watched);
        assertEquals("Episode watched boolean does not match.", false,
                episode.watched.booleanValue());
        assertNotNull("Episode plays was null.", episode.plays);
        assertEquals("Episode plays does not match.", 0, episode.plays.intValue());
        //TODO rating
        assertNotNull("Episode in watchlist boolean was null.", episode.in_watchlist);
        assertEquals("Episode in watchlist boolean does not match.", false,
                episode.in_watchlist.booleanValue());
    }

    @Test
    public void test_episodeUnlibrary() {
        Response response = getManager().showService().episodeUnlibrary(new ShowService.Episodes(
                153021, 1, 1
        ));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_episodeUnseen() {
        Response response = getManager().showService().episodeUnseen(new ShowService.Episodes(
                153021, 1, 1
        ));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_episodesUnseen() {
        List<ShowService.Episodes.Episode> episodes = new ArrayList<ShowService.Episodes.Episode>();
        episodes.add(new ShowService.Episodes.Episode(1, 1));
        episodes.add(new ShowService.Episodes.Episode(1, 2));
        Response response = getManager().showService().episodeUnseen(new ShowService.Episodes(
                153021, episodes
        ));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_seasonLibrary() {
        Response response = getManager().showService().seasonLibrary(new ShowService.Season(
                153021, 1
        ));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_seasonSeen() {
        Response response = getManager().showService().seasonSeen(new ShowService.Season(
                153021, 1
        ));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_showLibrary() {
        Response response = getManager().showService().showLibrary(new ShowService.Show(153021));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_showSeen() {
        Response response = getManager().showService().showSeen(new ShowService.Show(153021));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_showUnlibrary() {
        Response response = getManager().showService().showUnlibrary(new ShowService.Show(153021));
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void test_stats() {
        Stats stats = getManager().showService().stats("the-walking-dead");
        assertThat(stats).isNotNull();
        assertThat(stats.ratings).isNotNull();
        assertThat(stats.ratings.distribution).isNotNull();
        assertThat(stats.scrobbles).isNotNull();
        assertThat(stats.checkins).isNotNull();
        assertThat(stats.collection).isNotNull();
        assertThat(stats.lists).isNotNull();
        assertThat(stats.comments).isNotNull();
    }

    @Test
    public void test_summary() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
        cal.set(2010, 9, 31, 21, 0);
        Date firstAired = cal.getTime();

        TvShow show = getManager().showService().summaryExtended("the-walking-dead");
        assertThat(show).isNotNull();
        assertThat(show.title).isEqualTo("The Walking Dead");
        assertThat(show.year).isNotNull();
        assertThat(show.year.intValue()).isEqualTo(2010);
        assertThat(show.url).isEqualTo("http://trakt.tv/show/the-walking-dead");
        assertThat(show.first_aired_utc).isEqualTo(1288573200);
        assertThat(show.country).isEqualTo("United States");
        assertThat(show.overview).isNotNull();
        assertThat(show.runtime).isNotNull();
        assertThat(show.runtime.intValue()).isEqualTo(60);
        assertThat(show.network).isEqualTo("AMC");
        assertThat(show.airDay).isEqualTo(DayOfTheWeek.Sunday);
        assertThat(show.airTime).isEqualTo("9:00pm");
        assertThat(show.certification).isEqualTo("TV-14");
        assertThat(show.imdb_id).isEqualTo("tt1520211");
        assertThat(show.tvdb_id).isEqualTo(153021);
        assertThat(show.tvrage_id).isEqualTo(25056);
    }

    @Test
    public void test_summaries() {
        // default
        List<TvShow> shows = getManager().showService()
                .summaries("the-walking-dead,256227", Extended2.DEFAULT);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
        assertThat(shows.get(0).title).isEqualTo("The Walking Dead");

        // full
        shows = getManager().showService()
                .summaries("the-walking-dead,256227", Extended2.FULL);
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
        assertThat(shows.get(0).title).isEqualTo("The Walking Dead");
    }

    @Test
    public void test_trending() {
        List<TvShow> shows = getManager().showService().trending();
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
    }


}
