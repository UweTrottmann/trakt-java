package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.CollectedEpisode;
import com.uwetrottmann.trakt.v2.entities.CollectedMovie;
import com.uwetrottmann.trakt.v2.entities.CollectedSeason;
import com.uwetrottmann.trakt.v2.entities.CollectedShow;
import com.uwetrottmann.trakt.v2.entities.MovieIds;
import com.uwetrottmann.trakt.v2.entities.ShowIds;
import com.uwetrottmann.trakt.v2.entities.SyncEntity;
import com.uwetrottmann.trakt.v2.entities.SyncEpisode;
import com.uwetrottmann.trakt.v2.entities.SyncMovie;
import com.uwetrottmann.trakt.v2.entities.SyncResponse;
import com.uwetrottmann.trakt.v2.entities.SyncSeason;
import com.uwetrottmann.trakt.v2.entities.SyncShow;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class SyncTest extends BaseTestCase {

    @Test
    public void test_getCollectionMovies() {
        List<CollectedMovie> movies = getTrakt().sync().getCollectionMovies();
        for (CollectedMovie movie : movies) {
            assertThat(movie.collected_at).isNotNull();
        }
    }

    @Test
    public void test_getCollectionShows() {
        List<CollectedShow> shows = getTrakt().sync().getCollectionShows();
        for (CollectedShow show : shows) {
            assertThat(show.collected_at).isNotNull();
            for (CollectedSeason season : show.seasons) {
                for (CollectedEpisode episode : season.episodes) {
                    assertThat(episode.collected_at).isNotNull();
                }
            }
        }
    }

    @Test
    public void test_addItemsToCollection() {
        SyncEntity items = new SyncEntity();

        SyncMovie movie = new SyncMovie();
        movie.ids = new MovieIds();
        movie.ids.tmdb = TestData.MOVIE_TMDB_ID;
        items.movies = new LinkedList<>();
        items.movies.add(movie);


        // episode
        SyncEpisode episode = new SyncEpisode();
        episode.number = TestData.EPISODE_NUMBER;
        SyncEpisode episode2 = new SyncEpisode();
        episode2.number = 2;
        // season
        SyncSeason season = new SyncSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode);
        season.episodes.add(episode2);
        // show
        SyncShow show = new SyncShow();
        show.ids = new ShowIds();
        show.ids.tvdb = TestData.SHOW_TVDB_ID;
        show.seasons = new LinkedList<>();
        show.seasons.add(season);
        items.shows = new LinkedList<>();
        items.shows.add(show);

        SyncResponse response = getTrakt().sync().addItemsToCollection(items);
        assertThat(response.added.movies).isNotNull();
        assertThat(response.added.episodes).isNotNull();
        assertThat(response.existing.movies).isNotNull();
        assertThat(response.existing.episodes).isNotNull();
        assertThat(response.not_found).isNotNull();
        assertThat(response.deleted).isNull();
    }

    @Test
    public void test_deleteItemsFromCollection() {
        SyncEntity items = new SyncEntity();

        SyncMovie movie = new SyncMovie();
        movie.ids = new MovieIds();
        movie.ids.tmdb = 118340;
        items.movies = new LinkedList<>();
        items.movies.add(movie);

        // episode
        SyncEpisode episode2 = new SyncEpisode();
        episode2.number = 2;
        // season
        SyncSeason season = new SyncSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode2);
        // show
        SyncShow show = new SyncShow();
        show.ids = new ShowIds();
        show.ids.tvdb = TestData.SHOW_TVDB_ID;
        show.seasons = new LinkedList<>();
        show.seasons.add(season);
        items.shows = new LinkedList<>();
        items.shows.add(show);

        SyncResponse response = getTrakt().sync().deleteItemsFromCollection(items);
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
        assertThat(response.added).isNull();
    }

}
