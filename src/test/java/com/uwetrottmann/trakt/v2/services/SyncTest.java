package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.CollectedMovie;
import com.uwetrottmann.trakt.v2.entities.LastActivities;
import com.uwetrottmann.trakt.v2.entities.MovieIds;
import com.uwetrottmann.trakt.v2.entities.RatedEpisode;
import com.uwetrottmann.trakt.v2.entities.RatedMovie;
import com.uwetrottmann.trakt.v2.entities.RatedSeason;
import com.uwetrottmann.trakt.v2.entities.RatedShow;
import com.uwetrottmann.trakt.v2.entities.ShowIds;
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
import com.uwetrottmann.trakt.v2.entities.WatchedMovie;
import com.uwetrottmann.trakt.v2.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt.v2.entities.WatchlistedMovie;
import com.uwetrottmann.trakt.v2.entities.WatchlistedShow;
import com.uwetrottmann.trakt.v2.enums.Extended;
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
    public void test_lastActivites() throws OAuthUnauthorizedException {
        LastActivities lastActivities = getTrakt().sync().lastActivities();
        assertThat(lastActivities.movies).isNotNull();
        assertThat(lastActivities.episodes).isNotNull();
        assertThat(lastActivities.shows).isNotNull();
        assertThat(lastActivities.seasons).isNotNull();
    }

    @Test
    public void test_collectionMovies() throws OAuthUnauthorizedException {
        List<CollectedMovie> movies = getTrakt().sync().collectionMovies(Extended.DEFAULT_MIN);
        assertCollectedMovies(movies);
    }

    @Test
    public void test_collectionShows() throws OAuthUnauthorizedException {
        List<BaseShow> shows = getTrakt().sync().collectionShows(Extended.DEFAULT_MIN);
        assertSyncShows(shows, "collection");
    }

    @Test
    public void test_addItemsToCollection_movie() throws OAuthUnauthorizedException {
        SyncMovie movie = new SyncMovie();
        movie.ids = buildMovieIds();

        SyncItems items = new SyncItems().movies(movie);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_show() throws OAuthUnauthorizedException {
        SyncShow show = new SyncShow();
        show.ids = buildShowIds();

        SyncItems items = new SyncItems().shows(show);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_season() throws OAuthUnauthorizedException {
        // season
        SyncSeason season = new SyncSeason();
        season.number = 1;

        // show
        SyncShow show = new SyncShow();
        show.ids = ShowIds.slug("community");
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().shows(show);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_episode() throws OAuthUnauthorizedException {
        // episodes
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
        addItemsToCollection(items);
    }

    private void addItemsToCollection(SyncItems items) throws OAuthUnauthorizedException {
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
    public void test_watchedMovies() throws OAuthUnauthorizedException {
        List<WatchedMovie> watchedMovies = getTrakt().sync().watchedMovies(Extended.DEFAULT_MIN);
        assertWatchedMovies(watchedMovies);
    }

    @Test
    public void test_watchedShows() throws OAuthUnauthorizedException {
        List<BaseShow> watchedShows = getTrakt().sync().watchedShows(Extended.DEFAULT_MIN);
        assertSyncShows(watchedShows, "watched");
    }

    @Test
    public void test_addItemsToWatchedHistory() throws OAuthUnauthorizedException {
        // movie
        SyncMovie movie = new SyncMovie();
        movie.watched_at = new DateTime().minusHours(1);
        movie.ids = buildMovieIds();

        // episode
        SyncEpisode episode = new SyncEpisode();
        episode.number = TestData.EPISODE_NUMBER;
        episode.watched_at = new DateTime().minusHours(1);
        SyncEpisode episode2 = new SyncEpisode();
        episode2.number = 2;
        episode2.watched_at = new DateTime().minusMinutes(30);
        // season
        SyncSeason season = new SyncSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new LinkedList<>();
        season.episodes.add(episode);
        season.episodes.add(episode2);
        // show
        SyncShow show = new SyncShow();
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new LinkedList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().movies(movie).shows(show);

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
    public void test_ratingsMovies() throws OAuthUnauthorizedException {
        List<RatedMovie> ratedMovies = getTrakt().sync().ratingsMovies(RatingsFilter.ALL, Extended.DEFAULT_MIN);
        assertRatedEntities(ratedMovies);
    }

    @Test
    public void test_ratingsMovies_filtered() throws OAuthUnauthorizedException {
        List<RatedMovie> ratedMovies = getTrakt().sync().ratingsMovies(RatingsFilter.TOTALLYNINJA,
                Extended.DEFAULT_MIN);
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(Rating.TOTALLYNINJA);
        }
    }

    @Test
    public void test_ratingsShows() throws OAuthUnauthorizedException {
        List<RatedShow> ratedShows = getTrakt().sync().ratingsShows(RatingsFilter.ALL, Extended.DEFAULT_MIN);
        assertRatedEntities(ratedShows);
    }

    @Test
    public void test_ratingsSeasons() throws OAuthUnauthorizedException {
        List<RatedSeason> ratedSeasons = getTrakt().sync().ratingsSeasons(RatingsFilter.ALL, Extended.DEFAULT_MIN);
        assertRatedEntities(ratedSeasons);
    }

    @Test
    public void test_ratingsEpisodes() throws OAuthUnauthorizedException {
        List<RatedEpisode> ratedEpisodes = getTrakt().sync().ratingsEpisodes(RatingsFilter.ALL, Extended.DEFAULT_MIN);
        assertRatedEntities(ratedEpisodes);
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
    public void test_watchlistMovies() throws OAuthUnauthorizedException {
        List<WatchlistedMovie> movies = getTrakt().sync().watchlistMovies(Extended.FULLIMAGES);
        for (WatchlistedMovie movie : movies) {
            assertThat(movie.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistShows() throws OAuthUnauthorizedException {
        List<WatchlistedShow> shows = getTrakt().sync().watchlistShows(Extended.FULLIMAGES);
        for (WatchlistedShow show : shows) {
            assertThat(show.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistEpisodes() throws OAuthUnauthorizedException {
        List<WatchlistedEpisode> episodes = getTrakt().sync().watchlistEpisodes(Extended.FULLIMAGES);
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
