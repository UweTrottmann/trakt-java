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
import com.uwetrottmann.trakt.v2.entities.SyncCollectedItems;
import com.uwetrottmann.trakt.v2.entities.SyncCollectedMovie;
import com.uwetrottmann.trakt.v2.entities.SyncCollectedShow;
import com.uwetrottmann.trakt.v2.entities.SyncEpisode;
import com.uwetrottmann.trakt.v2.entities.SyncItems;
import com.uwetrottmann.trakt.v2.entities.SyncMovie;
import com.uwetrottmann.trakt.v2.entities.SyncRatedEpisode;
import com.uwetrottmann.trakt.v2.entities.SyncRatedItems;
import com.uwetrottmann.trakt.v2.entities.SyncRatedMovie;
import com.uwetrottmann.trakt.v2.entities.SyncRatedSeason;
import com.uwetrottmann.trakt.v2.entities.SyncRatedShow;
import com.uwetrottmann.trakt.v2.entities.SyncResponse;
import com.uwetrottmann.trakt.v2.entities.SyncSeason;
import com.uwetrottmann.trakt.v2.entities.SyncShow;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedEpisode;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedItems;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedMovie;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedSeason;
import com.uwetrottmann.trakt.v2.entities.SyncWatchedShow;
import com.uwetrottmann.trakt.v2.entities.WatchedEpisode;
import com.uwetrottmann.trakt.v2.entities.WatchedMovie;
import com.uwetrottmann.trakt.v2.entities.WatchedSeason;
import com.uwetrottmann.trakt.v2.entities.WatchedShow;
import com.uwetrottmann.trakt.v2.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt.v2.entities.WatchlistedMovie;
import com.uwetrottmann.trakt.v2.entities.WatchlistedShow;
import com.uwetrottmann.trakt.v2.enums.Rating;
import com.uwetrottmann.trakt.v2.enums.RatingsFilter;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncTest extends BaseTestCase {

    @Test
    public void test_getCollectionMovies() throws OAuthUnauthorizedException {
        List<CollectedMovie> movies = getTrakt().sync().getCollectionMovies();
        for (CollectedMovie movie : movies) {
            assertThat(movie.collected_at).isNotNull();
        }
    }

    @Test
    public void test_getCollectionShows() throws OAuthUnauthorizedException {
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
    public void test_addItemsToCollection_movie() throws OAuthUnauthorizedException {
        SyncCollectedMovie movie = new SyncCollectedMovie();
        movie.ids = buildMovieIds();

        SyncCollectedItems items = new SyncCollectedItems().movies(movie);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_show() throws OAuthUnauthorizedException {
        SyncCollectedShow show = new SyncCollectedShow();
        show.ids = buildShowIds();

        SyncCollectedItems items = new SyncCollectedItems().shows(show);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_season() throws OAuthUnauthorizedException {
        // season
        CollectedSeason season = new CollectedSeason();
        season.number = 1;

        // show
        SyncCollectedShow show = new SyncCollectedShow();
        show.ids = ShowIds.slug("community");
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncCollectedItems items = new SyncCollectedItems().shows(show);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_episode() throws OAuthUnauthorizedException {
        // episodes
        CollectedEpisode episode1 = new CollectedEpisode();
        episode1.number = 1;
        CollectedEpisode episode2 = new CollectedEpisode();
        episode2.number = 2;

        // season
        CollectedSeason season = new CollectedSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode1);
        season.episodes.add(episode2);

        // show
        SyncCollectedShow show = new SyncCollectedShow();
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncCollectedItems items = new SyncCollectedItems().shows(show);
        addItemsToCollection(items);
    }

    private void addItemsToCollection(SyncCollectedItems items) throws OAuthUnauthorizedException {
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
    public void test_deleteItemsFromCollection() throws OAuthUnauthorizedException {
        SyncResponse response = getTrakt().sync().deleteItemsFromCollection(buildItemsForDeletion());
        assertSyncResponseDelete(response);
    }

    private void assertSyncResponseDelete(SyncResponse response) {
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
        assertThat(response.added).isNull();
    }

    private SyncItems buildItemsForDeletion() {
        // movie
        SyncMovie movie = new SyncMovie();
        movie.ids = buildMovieIds();

        // episode
        SyncEpisode episode2 = new SyncEpisode();
        episode2.number = 2;

        SyncSeason season = new SyncSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode2);

        SyncShow show = new SyncShow();
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        return new SyncItems().movies(movie).shows(show);
    }

    @Test
    public void test_getWatchedMovies() throws OAuthUnauthorizedException {
        List<WatchedMovie> watchedMovies = getTrakt().sync().getWatchedMovies();
        for (WatchedMovie movie : watchedMovies) {
            assertThat(movie.plays).isPositive();
        }
    }

    @Test
    public void test_getWatchedShows() throws OAuthUnauthorizedException {
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
    public void test_addItemsToWatchedHistory() throws OAuthUnauthorizedException {
        // movie
        SyncWatchedMovie movie = new SyncWatchedMovie();
        movie.watched_at = new DateTime().minusHours(1);
        movie.ids = buildMovieIds();

        // episode
        SyncWatchedEpisode episode = new SyncWatchedEpisode();
        episode.number = TestData.EPISODE_NUMBER;
        episode.watched_at = new DateTime().minusHours(1);
        SyncWatchedEpisode episode2 = new SyncWatchedEpisode();
        episode2.number = 2;
        episode2.watched_at = new DateTime().minusMinutes(30);
        // season
        SyncWatchedSeason season = new SyncWatchedSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode);
        season.episodes.add(episode2);
        // show
        SyncWatchedShow show = new SyncWatchedShow();
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncWatchedItems items = new SyncWatchedItems().movies(movie).shows(show);

        SyncResponse response = getTrakt().sync().addItemsToWatchedHistory(items);
        assertThat(response.added.movies).isNotNull();
        assertThat(response.added.episodes).isNotNull();
        assertThat(response.existing).isNull();
        assertThat(response.deleted).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_deleteItemsFromWatchedHistory() throws OAuthUnauthorizedException {
        SyncItems items = buildItemsForDeletion();

        SyncResponse response = getTrakt().sync().deleteItemsFromWatchedHistory(items);
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.added).isNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_getRatingsMovies() throws OAuthUnauthorizedException {
        List<RatedMovie> ratedMovies = getTrakt().sync().getRatingsMovies(RatingsFilter.ALL);
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isNotNull();
        }
    }

    @Test
    public void test_getRatingsMovies_filtered() throws OAuthUnauthorizedException {
        List<RatedMovie> ratedMovies = getTrakt().sync().getRatingsMovies(RatingsFilter.TOTALLYNINJA);
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(10);
        }
    }

    @Test
    public void test_getRatingsShows() throws OAuthUnauthorizedException {
        List<RatedShow> ratedShows = getTrakt().sync().getRatingsShows(RatingsFilter.ALL);
        for (RatedShow show : ratedShows) {
            assertThat(show.rated_at).isNotNull();
            assertThat(show.rating).isNotNull();
        }
    }

    @Test
    public void test_getRatingsSeasons() throws OAuthUnauthorizedException {
        List<RatedSeason> ratedSeasons = getTrakt().sync().getRatingsSeasons(RatingsFilter.ALL);
        for (RatedSeason season : ratedSeasons) {
            assertThat(season.rated_at).isNotNull();
            assertThat(season.rating).isNotNull();
        }
    }

    @Test
    public void test_getRatingsEpisodes() throws OAuthUnauthorizedException {
        List<RatedEpisode> ratedEpisodes = getTrakt().sync().getRatingsEpisodes(RatingsFilter.ALL);
        for (RatedEpisode episode : ratedEpisodes) {
            assertThat(episode.rated_at).isNotNull();
            assertThat(episode.rating).isNotNull();
        }
    }

    @Test
    public void test_addRatings_movie() throws OAuthUnauthorizedException {
        SyncRatedMovie movie = new SyncRatedMovie();
        movie.rating = Rating.TOTALLYNINJA;
        movie.ids = MovieIds.slug(TestData.MOVIE_SLUG);

        SyncRatedItems items = new SyncRatedItems().movies(movie);
        getTrakt().sync().addRatings(items);
    }

    @Test
    public void test_addRatings_show() throws OAuthUnauthorizedException {
        SyncRatedShow show = new SyncRatedShow();
        show.rating = Rating.TOTALLYNINJA;
        show.ids = ShowIds.slug(TestData.SHOW_SLUG);

        SyncRatedItems items = new SyncRatedItems().shows(show);
        getTrakt().sync().addRatings(items);
    }

    @Test
    public void test_addRatings_season() throws OAuthUnauthorizedException {
        SyncRatedSeason season = new SyncRatedSeason();
        season.rating = Rating.TOTALLYNINJA;
        season.number = TestData.EPISODE_SEASON;

        SyncRatedShow show = new SyncRatedShow();
        show.ids = ShowIds.slug("community");
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncRatedItems items = new SyncRatedItems().shows(show);
        getTrakt().sync().addRatings(items);
    }

    @Test
    public void test_addRatings_episode() throws OAuthUnauthorizedException {
        SyncRatedEpisode episode1 = new SyncRatedEpisode();
        episode1.rating = Rating.TOTALLYNINJA;
        episode1.number = TestData.EPISODE_NUMBER;
        SyncRatedEpisode episode2 = new SyncRatedEpisode();
        episode2.rating = Rating.GREAT;
        episode2.number = 2;

        SyncRatedSeason season = new SyncRatedSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode1);
        season.episodes.add(episode2);

        SyncRatedShow show = new SyncRatedShow();
        show.ids = ShowIds.slug(TestData.SHOW_SLUG);
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncRatedItems items = new SyncRatedItems().shows(show);
        getTrakt().sync().addRatings(items);
    }

    @Test
    public void test_deleteRatings() throws OAuthUnauthorizedException {
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

    @Test
    public void test_getWatchlistMovies() throws OAuthUnauthorizedException {
        List<WatchlistedMovie> movies = getTrakt().sync().getWatchlistMovies();
        for (WatchlistedMovie movie : movies) {
            assertThat(movie.listed_at).isNotNull();
        }
    }

    @Test
    public void test_getWatchlistShows() throws OAuthUnauthorizedException {
        List<WatchlistedShow> shows = getTrakt().sync().getWatchlistShows();
        for (WatchlistedShow show : shows) {
            assertThat(show.listed_at).isNotNull();
        }
    }

    @Test
    public void test_getWatchlistEpisodes() throws OAuthUnauthorizedException {
        List<WatchlistedEpisode> episodes = getTrakt().sync().getWatchlistEpisodes();
        for (WatchlistedEpisode episode : episodes) {
            assertThat(episode.listed_at).isNotNull();
        }
    }

    @Test
    public void test_addItemsToWatchlist_movie() throws OAuthUnauthorizedException {
        SyncMovie movie = new SyncMovie();
        movie.ids = buildMovieIds();

        SyncItems items = new SyncItems().movies(movie);
        addItemsToWatchlist(items);
    }

    @Test
    public void test_addItemsToWatchlist_show() throws OAuthUnauthorizedException {
        SyncShow show = new SyncShow();
        show.ids = buildShowIds();

        SyncItems items = new SyncItems().shows(show);
        addItemsToWatchlist(items);
    }

    @Test
    public void test_addItemsToWatchlist_season() throws OAuthUnauthorizedException {
        // season
        SyncSeason season = new SyncSeason();
        season.number = 1;

        // show
        SyncShow show = new SyncShow();
        show.ids = ShowIds.slug("community");
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().shows(show);
        addItemsToWatchlist(items);
    }

    @Test
    public void test_addItemsToWatchlist_episodes() throws OAuthUnauthorizedException {
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
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().shows(show);
        addItemsToWatchlist(items);
    }

    private void addItemsToWatchlist(SyncItems items) throws OAuthUnauthorizedException {
        SyncResponse response = getTrakt().sync().addItemsToWatchlist(items);
        assertSyncResponse(response);
    }

    @Test
    public void test_deleteItemsFromWatchlist() throws OAuthUnauthorizedException {
        SyncResponse response = getTrakt().sync().deleteItemsFromWatchlist(buildItemsForDeletion());
        assertSyncResponseDelete(response);
    }


    private MovieIds buildMovieIds() {
        return MovieIds.tmdb(TestData.MOVIE_TMDB_ID);
    }

    private ShowIds buildShowIds() {
        return ShowIds.slug("the-walking-dead");
    }

}
