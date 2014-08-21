package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.CollectedEpisode;
import com.uwetrottmann.trakt.v2.entities.CollectedMovie;
import com.uwetrottmann.trakt.v2.entities.CollectedSeason;
import com.uwetrottmann.trakt.v2.entities.CollectedShow;
import com.uwetrottmann.trakt.v2.entities.MovieIds;
import com.uwetrottmann.trakt.v2.entities.RatedEpisode;
import com.uwetrottmann.trakt.v2.entities.RatedMovie;
import com.uwetrottmann.trakt.v2.entities.RatedSeason;
import com.uwetrottmann.trakt.v2.entities.RatedShow;
import com.uwetrottmann.trakt.v2.entities.ShowIds;
import com.uwetrottmann.trakt.v2.entities.SyncItems;
import com.uwetrottmann.trakt.v2.entities.SyncEpisode;
import com.uwetrottmann.trakt.v2.entities.SyncMovie;
import com.uwetrottmann.trakt.v2.entities.SyncRatedEpisode;
import com.uwetrottmann.trakt.v2.entities.SyncRatedItems;
import com.uwetrottmann.trakt.v2.entities.SyncRatedMovie;
import com.uwetrottmann.trakt.v2.entities.SyncRatedSeason;
import com.uwetrottmann.trakt.v2.entities.SyncRatedShow;
import com.uwetrottmann.trakt.v2.entities.SyncResponse;
import com.uwetrottmann.trakt.v2.entities.SyncSeason;
import com.uwetrottmann.trakt.v2.entities.SyncShow;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedItems;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedEpisode;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedMovie;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedSeason;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedShow;
import com.uwetrottmann.trakt.v2.entities.WatchedEpisode;
import com.uwetrottmann.trakt.v2.entities.WatchedMovie;
import com.uwetrottmann.trakt.v2.entities.WatchedSeason;
import com.uwetrottmann.trakt.v2.entities.WatchedShow;
import com.uwetrottmann.trakt.v2.enums.Rating;
import com.uwetrottmann.trakt.v2.enums.RatingsFilter;
import org.junit.Test;

import java.util.Date;
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
    public void test_addItemsToCollection_movie() {
        SyncItems items = new SyncItems();

        SyncMovie movie = new SyncMovie();
        movie.ids = new MovieIds();
        movie.ids.tmdb = TestData.MOVIE_TMDB_ID;
        items.movies = new LinkedList<>();
        items.movies.add(movie);

        SyncResponse response = getTrakt().sync().addItemsToCollection(items);
        assertSyncResponse(response);
    }

    @Test
    public void test_addItemsToCollection_show() {
        SyncItems items = new SyncItems();

        // show
        SyncShow show = new SyncShow();
        show.ids = new ShowIds();
        show.ids.slug = "the-walking-dead";
        items.shows = new LinkedList<>();
        items.shows.add(show);

        SyncResponse response = getTrakt().sync().addItemsToCollection(items);
        assertSyncResponse(response);
    }

    @Test
    public void test_addItemsToCollection_season() {
        SyncItems items = new SyncItems();

        // season
        SyncSeason season = new SyncSeason();
        season.number = 1;
        // show
        SyncShow show = new SyncShow();
        show.ids = new ShowIds();
        show.ids.slug = "community";
        show.seasons = new LinkedList<>();
        show.seasons.add(season);
        items.shows = new LinkedList<>();
        items.shows.add(show);

        SyncResponse response = getTrakt().sync().addItemsToCollection(items);
        assertSyncResponse(response);
    }

    @Test
    public void test_addItemsToCollection_episode() {
        SyncItems items = new SyncItems();

        // episode
        SyncEpisode episode1 = new SyncEpisode();
        episode1.number = 1;
        SyncEpisode episode2 = new SyncEpisode();
        episode2.number = 2;
        // season
        SyncSeason season = new SyncSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode1);
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
        assertSyncResponse(response);
    }

    private void assertSyncResponse(SyncResponse response) {
        assertThat(response.added.movies).isNotNull();
        assertThat(response.added.episodes).isNotNull();
        assertThat(response.existing.movies).isNotNull();
        assertThat(response.existing.episodes).isNotNull();
        assertThat(response.not_found).isNotNull();
        assertThat(response.deleted).isNull();
    }

    @Test
    public void test_deleteItemsFromCollection() {
        SyncItems items = buildItemsForDeletion();

        SyncResponse response = getTrakt().sync().deleteItemsFromCollection(items);
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
        assertThat(response.added).isNull();
    }

    private SyncItems buildItemsForDeletion() {
        SyncItems items = new SyncItems();

        SyncMovie movie = new SyncMovie();
        movie.ids = new MovieIds();
        movie.ids.tmdb = TestData.MOVIE_TMDB_ID;
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
        return items;
    }

    @Test
    public void test_getWatchedMovies() {
        List<WatchedMovie> watchedMovies = getTrakt().sync().getWatchedMovies();
        for (WatchedMovie movie : watchedMovies) {
            assertThat(movie.plays).isPositive();
        }
    }

    @Test
    public void test_getWatchedShows() {
        List<WatchedShow> watchedShows = getTrakt().sync().getWatchedShows();
        for (WatchedShow show : watchedShows) {
            assertThat(show.plays).isPositive();
            for (WatchedSeason season : show.seasons) {
                for (WatchedEpisode episode : season.episodes) {
                    assertThat(episode.plays).isPositive();
                }
            }
        }
    }

    @Test
    public void test_addItemsToWatchedHistory() {
        SyncWatchedItems items = new SyncWatchedItems();

        SyncWatchedMovie movie = new SyncWatchedMovie();
        movie.watched_at = new Date(System.currentTimeMillis() - 3600000);
        movie.ids = new MovieIds();
        movie.ids.tmdb = TestData.MOVIE_TMDB_ID;
        items.movies = new LinkedList<>();
        items.movies.add(movie);


        // episode
        SyncWatchedEpisode episode = new SyncWatchedEpisode();
        episode.number = TestData.EPISODE_NUMBER;
        episode.watched_at = new Date(System.currentTimeMillis() - 3600000);
        SyncWatchedEpisode episode2 = new SyncWatchedEpisode();
        episode2.number = 2;
        episode2.watched_at = new Date(System.currentTimeMillis() - 1800000);
        // season
        SyncWatchedSeason season = new SyncWatchedSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode);
        season.episodes.add(episode2);
        // show
        SyncWatchedShow show = new SyncWatchedShow();
        show.ids = new ShowIds();
        show.ids.tvdb = TestData.SHOW_TVDB_ID;
        show.seasons = new LinkedList<>();
        show.seasons.add(season);
        items.shows = new LinkedList<>();
        items.shows.add(show);

        SyncResponse response = getTrakt().sync().addItemsToWatchedHistory(items);
        assertThat(response.added.movies).isNotNull();
        assertThat(response.added.episodes).isNotNull();
        assertThat(response.existing).isNull();
        assertThat(response.deleted).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_deleteItemsFromWatchedHistory() {
        SyncItems items = buildItemsForDeletion();

        SyncResponse response = getTrakt().sync().deleteItemsFromWatchedHistory(items);
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.added).isNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_getRatingsMovies() {
        List<RatedMovie> ratedMovies = getTrakt().sync().getRatingsMovies(RatingsFilter.ALL);
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isNotNull();
        }
    }

    @Test
    public void test_getRatingsMovies_filtered() {
        List<RatedMovie> ratedMovies = getTrakt().sync().getRatingsMovies(RatingsFilter.TOTALLYNINJA);
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(10);
        }
    }

    @Test
    public void test_getRatingsShows() {
        List<RatedShow> ratedShows = getTrakt().sync().getRatingsShows(RatingsFilter.ALL);
        for (RatedShow show : ratedShows) {
            assertThat(show.rated_at).isNotNull();
            assertThat(show.rating).isNotNull();
        }
    }

    @Test
    public void test_getRatingsSeasons() {
        List<RatedSeason> ratedSeasons = getTrakt().sync().getRatingsSeasons(RatingsFilter.ALL);
        for (RatedSeason season : ratedSeasons) {
            assertThat(season.rated_at).isNotNull();
            assertThat(season.rating).isNotNull();
        }
    }

    @Test
    public void test_getRatingsEpisodes() {
        List<RatedEpisode> ratedEpisodes = getTrakt().sync().getRatingsEpisodes(RatingsFilter.ALL);
        for (RatedEpisode episode : ratedEpisodes) {
            assertThat(episode.rated_at).isNotNull();
            assertThat(episode.rating).isNotNull();
        }
    }

    @Test
    public void test_addRatings_movie() {
        SyncRatedItems items = new SyncRatedItems();

        SyncRatedMovie movie = new SyncRatedMovie();
        movie.rating = Rating.TOTALLYNINJA;
        movie.ids = new MovieIds();
        movie.ids.slug = TestData.MOVIE_SLUG;

        items.movies = new LinkedList<>();
        items.movies.add(movie);

        getTrakt().sync().addRatings(items);
    }

    @Test
    public void test_addRatings_show() {
        SyncRatedItems items = new SyncRatedItems();

        SyncRatedShow show = new SyncRatedShow();
        show.rating = Rating.TOTALLYNINJA;
        show.ids = new ShowIds();
        show.ids.slug = TestData.SHOW_SLUG;

        items.shows = new LinkedList<>();
        items.shows.add(show);

        getTrakt().sync().addRatings(items);
    }

    @Test
    public void test_addRatings_season() {
        SyncRatedItems items = new SyncRatedItems();

        SyncRatedSeason season = new SyncRatedSeason();
        season.rating = Rating.TOTALLYNINJA;
        season.number = TestData.EPISODE_SEASON;

        SyncRatedShow show = new SyncRatedShow();
        show.ids = new ShowIds();
        show.ids.slug = TestData.SHOW_SLUG;
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        items.shows = new LinkedList<>();
        items.shows.add(show);

        getTrakt().sync().addRatings(items);
    }

    @Test
    public void test_addRatings_episode() {
        SyncRatedItems items = new SyncRatedItems();

        SyncRatedEpisode episode  = new SyncRatedEpisode();
        episode.rating = Rating.TOTALLYNINJA;
        episode.number = TestData.EPISODE_NUMBER;

        SyncRatedSeason season = new SyncRatedSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode);

        SyncRatedShow show = new SyncRatedShow();
        show.ids = new ShowIds();
        show.ids.slug = TestData.SHOW_SLUG;
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        items.shows = new LinkedList<>();
        items.shows.add(show);

        getTrakt().sync().addRatings(items);
    }

    @Test
    public void test_deleteRatings() {
        SyncItems items = buildItemsForDeletion();

        SyncResponse response = getTrakt().sync().deleteRatings(items);
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.shows).isNotNull();
        assertThat(response.deleted.seasons).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.added).isNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
    }

}
