package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.BaseMovie;
import com.uwetrottmann.trakt5.entities.BaseShow;
import com.uwetrottmann.trakt5.entities.LastActivities;
import com.uwetrottmann.trakt5.entities.LastActivity;
import com.uwetrottmann.trakt5.entities.LastActivityMore;
import com.uwetrottmann.trakt5.entities.MovieIds;
import com.uwetrottmann.trakt5.entities.RatedEpisode;
import com.uwetrottmann.trakt5.entities.RatedMovie;
import com.uwetrottmann.trakt5.entities.RatedSeason;
import com.uwetrottmann.trakt5.entities.RatedShow;
import com.uwetrottmann.trakt5.entities.ShowIds;
import com.uwetrottmann.trakt5.entities.SyncEpisode;
import com.uwetrottmann.trakt5.entities.SyncItems;
import com.uwetrottmann.trakt5.entities.SyncMovie;
import com.uwetrottmann.trakt5.entities.SyncResponse;
import com.uwetrottmann.trakt5.entities.SyncSeason;
import com.uwetrottmann.trakt5.entities.SyncShow;
import com.uwetrottmann.trakt5.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt5.entities.WatchlistedSeason;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.RatingsFilter;
import org.joda.time.DateTime;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncTest extends BaseTestCase {

    @Test
    public void test_lastActivites() throws IOException {
        Response<LastActivities> response = getTrakt().sync().lastActivities().execute();
        assertSuccessfulResponse(response);
        LastActivities lastActivities = response.body();
        assertThat(lastActivities.all).isNotNull();
        assertLastActivityMore(lastActivities.movies);
        assertLastActivityMore(lastActivities.episodes);
        assertLastActivity(lastActivities.shows);
        assertLastActivity(lastActivities.seasons);
    }

    private void assertLastActivityMore(LastActivityMore activityMore) {
        assertLastActivity(activityMore);
        assertThat(activityMore.collected_at).isNotNull();
        assertThat(activityMore.watched_at).isNotNull();
    }

    private void assertLastActivity(LastActivity activity) {
        assertThat(activity).isNotNull();
        assertThat(activity.commented_at).isNotNull();
        assertThat(activity.rated_at).isNotNull();
        assertThat(activity.watchlisted_at).isNotNull();
    }

    @Test
    public void test_collectionMovies() throws IOException {
        Response<List<BaseMovie>> response = getTrakt().sync().collectionMovies(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseMovie> movies = response.body();
        assertSyncMovies(movies, "collection");
    }

    @Test
    public void test_collectionShows() throws IOException {
        Response<List<BaseShow>> response = getTrakt().sync().collectionShows(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseShow> shows = response.body();
        assertSyncShows(shows, "collection");
    }

    @Test
    public void test_addItemsToCollection_movie() throws IOException {
        SyncMovie movie = new SyncMovie();
        movie.ids = buildMovieIds();

        SyncItems items = new SyncItems().movies(movie);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_show() throws IOException {
        SyncShow show = new SyncShow();
        show.ids = buildShowIds();

        SyncItems items = new SyncItems().shows(show);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_season() throws IOException {
        // season
        SyncSeason season = new SyncSeason();
        season.number = 1;

        // show
        SyncShow show = new SyncShow();
        show.ids = ShowIds.slug("community");
        show.seasons = new ArrayList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().shows(show);
        addItemsToCollection(items);
    }

    @Test
    public void test_addItemsToCollection_episode() throws IOException {
        // episodes
        SyncEpisode episode1 = new SyncEpisode();
        episode1.number = 1;
        SyncEpisode episode2 = new SyncEpisode();
        episode2.number = 2;

        // season
        SyncSeason season = new SyncSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new ArrayList<>();
        season.episodes.add(episode1);
        season.episodes.add(episode2);

        // show
        SyncShow show = new SyncShow();
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new ArrayList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().shows(show);
        addItemsToCollection(items);
    }

    private void addItemsToCollection(SyncItems items) throws IOException {
        Response<SyncResponse> response = getTrakt().sync().addItemsToCollection(items).execute();
        assertSuccessfulResponse(response);
        assertSyncResponse(response.body());
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
    public void test_deleteItemsFromCollection() throws IOException {
        Response<SyncResponse> response = getTrakt().sync().deleteItemsFromCollection(
                buildItemsForDeletion()).execute();
        assertSuccessfulResponse(response);
        assertSyncResponseDelete(response.body());
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
        season.episodes = new ArrayList<>();
        season.episodes.add(episode2);

        SyncShow show = new SyncShow();
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new ArrayList<>();
        show.seasons.add(season);

        return new SyncItems().movies(movie).shows(show);
    }

    @Test
    public void test_watchedMovies() throws IOException {
        Response<List<BaseMovie>> response = getTrakt().sync().watchedMovies(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseMovie> watchedMovies = response.body();
        assertSyncMovies(watchedMovies, "watched");
    }

    @Test
    public void test_watchedShows() throws IOException {
        Response<List<BaseShow>> response = getTrakt().sync().watchedShows(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseShow> watchedShows = response.body();
        assertSyncShows(watchedShows, "watched");
    }

    @Test
    public void test_addItemsToWatchedHistory() throws IOException {
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
        season.episodes = new ArrayList<>();
        season.episodes.add(episode);
        season.episodes.add(episode2);
        // show
        SyncShow show = new SyncShow();
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new ArrayList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().movies(movie).shows(show);

        Response<SyncResponse> requestResponse = getTrakt().sync().addItemsToWatchedHistory(items).execute();
        assertSuccessfulResponse(requestResponse);
        SyncResponse response = requestResponse.body();
        assertThat(response.added.movies).isNotNull();
        assertThat(response.added.episodes).isNotNull();
        assertThat(response.existing).isNull();
        assertThat(response.deleted).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_deleteItemsFromWatchedHistory() throws IOException {
        SyncItems items = buildItemsForDeletion();

        Response<SyncResponse> requestResponse = getTrakt().sync().deleteItemsFromWatchedHistory(items).execute();
        assertSuccessfulResponse(requestResponse);
        SyncResponse response = requestResponse.body();
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.added).isNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_ratingsMovies() throws IOException {
        Response<List<RatedMovie>> response = getTrakt().sync().ratingsMovies(RatingsFilter.ALL,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedMovie> ratedMovies = response.body();
        assertRatedEntities(ratedMovies);
    }

    @Test
    public void test_ratingsMovies_filtered() throws IOException {
        Response<List<RatedMovie>> response = getTrakt().sync().ratingsMovies(RatingsFilter.TOTALLYNINJA,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedMovie> ratedMovies = response.body();
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(Rating.TOTALLYNINJA);
        }
    }

    @Test
    public void test_ratingsShows() throws IOException {
        Response<List<RatedShow>> response = getTrakt().sync().ratingsShows(RatingsFilter.ALL,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedShow> ratedShows = response.body();
        assertRatedEntities(ratedShows);
    }

    @Test
    public void test_ratingsSeasons() throws IOException {
        Response<List<RatedSeason>> response = getTrakt().sync().ratingsSeasons(RatingsFilter.ALL,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedSeason> ratedSeasons = response.body();
        assertRatedEntities(ratedSeasons);
    }

    @Test
    public void test_ratingsEpisodes() throws IOException {
        Response<List<RatedEpisode>> response = getTrakt().sync().ratingsEpisodes(RatingsFilter.ALL,
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<RatedEpisode> ratedEpisodes = response.body();
        assertRatedEntities(ratedEpisodes);
    }

    @Test
    public void test_addRatings_movie() throws IOException {
        SyncMovie movie = new SyncMovie()
                .id(MovieIds.slug(TestData.MOVIE_SLUG))
                .rating(Rating.MEH);

        SyncItems items = new SyncItems().movies(movie);
        getTrakt().sync().addRatings(items).execute();
    }

    @Test
    public void test_addRatings_show() throws IOException {
        SyncShow show = new SyncShow()
                .id(ShowIds.slug(TestData.SHOW_SLUG))
                .rating(Rating.TERRIBLE);

        SyncItems items = new SyncItems().shows(show);
        getTrakt().sync().addRatings(items).execute();
    }

    @Test
    public void test_addRatings_season() throws IOException {
        SyncSeason season = new SyncSeason()
                .number(TestData.EPISODE_SEASON)
                .rating(Rating.FAIR);

        SyncShow show = new SyncShow()
                .id(ShowIds.slug("community"))
                .seasons(season);

        SyncItems items = new SyncItems().shows(show);
        getTrakt().sync().addRatings(items).execute();
    }

    @Test
    public void test_addRatings_episode() throws IOException {
        SyncEpisode episode1 = new SyncEpisode()
                .number(1)
                .rating(Rating.TOTALLYNINJA);
        SyncEpisode episode2 = new SyncEpisode()
                .number(2)
                .rating(Rating.GREAT);

        ArrayList<SyncEpisode> episodes = new ArrayList<>();
        episodes.add(episode1);
        episodes.add(episode2);

        SyncSeason season = new SyncSeason()
                .number(TestData.EPISODE_SEASON)
                .episodes(episodes);

        SyncShow show = new SyncShow()
                .id(ShowIds.slug(TestData.SHOW_SLUG))
                .seasons(season);

        SyncItems items = new SyncItems().shows(show);
        getTrakt().sync().addRatings(items).execute();
    }

    @Test
    public void test_deleteRatings() throws IOException {
        SyncItems items = buildItemsForDeletion();

        Response<SyncResponse> requestResponse = getTrakt().sync().deleteRatings(items).execute();
        assertSuccessfulResponse(requestResponse);
        SyncResponse response = requestResponse.body();
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.shows).isNotNull();
        assertThat(response.deleted.seasons).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.added).isNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_watchlistMovies() throws IOException {
        Response<List<BaseMovie>> response = getTrakt().sync().watchlistMovies(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseMovie> movies = response.body();
        assertSyncMovies(movies, "watchlist");
    }

    @Test
    public void test_watchlistShows() throws IOException {
        Response<List<BaseShow>> response = getTrakt().sync().watchlistShows(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<BaseShow> shows = response.body();
        for (BaseShow show : shows) {
            assertThat(show.show).isNotNull();
            assertThat(show.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistSeasons() throws IOException {
        Response<List<WatchlistedSeason>> response = getTrakt().sync().watchlistSeasons(Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<WatchlistedSeason> seasons = response.body();
        for (WatchlistedSeason season : seasons) {
            assertThat(season.season).isNotNull();
            assertThat(season.show).isNotNull();
            assertThat(season.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistEpisodes() throws IOException {
        Response<List<WatchlistedEpisode>> response = getTrakt().sync().watchlistEpisodes(
                Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
        List<WatchlistedEpisode> episodes = response.body();
        for (WatchlistedEpisode episode : episodes) {
            assertThat(episode.episode).isNotNull();
            assertThat(episode.show).isNotNull();
            assertThat(episode.listed_at).isNotNull();
        }
    }

    @Test
    public void test_addItemsToWatchlist_movie() throws IOException {
        SyncMovie movie = new SyncMovie();
        movie.ids = buildMovieIds();

        SyncItems items = new SyncItems().movies(movie);
        addItemsToWatchlist(items);
    }

    @Test
    public void test_addItemsToWatchlist_show() throws IOException {
        SyncShow show = new SyncShow();
        show.ids = buildShowIds();

        SyncItems items = new SyncItems().shows(show);
        addItemsToWatchlist(items);
    }

    @Test
    public void test_addItemsToWatchlist_season() throws IOException {
        // season
        SyncSeason season = new SyncSeason();
        season.number = 1;

        // show
        SyncShow show = new SyncShow();
        show.ids = ShowIds.slug("community");
        show.seasons = new ArrayList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().shows(show);
        addItemsToWatchlist(items);
    }

    @Test
    public void test_addItemsToWatchlist_episodes() throws IOException {
        // episode
        SyncEpisode episode1 = new SyncEpisode();
        episode1.number = 1;
        SyncEpisode episode2 = new SyncEpisode();
        episode2.number = 2;
        // season
        SyncSeason season = new SyncSeason();
        season.number = TestData.EPISODE_SEASON;
        season.episodes = new ArrayList<>();
        season.episodes.add(episode1);
        season.episodes.add(episode2);
        // show
        SyncShow show = new SyncShow();
        show.ids = ShowIds.tvdb(TestData.SHOW_TVDB_ID);
        show.seasons = new ArrayList<>();
        show.seasons.add(season);

        SyncItems items = new SyncItems().shows(show);
        addItemsToWatchlist(items);
    }

    private void addItemsToWatchlist(SyncItems items) throws IOException {
        Response<SyncResponse> requestResponse = getTrakt().sync().addItemsToWatchlist(items).execute();
        assertSyncResponse(requestResponse.body());
    }

    @Test
    public void test_deleteItemsFromWatchlist() throws IOException {
        Response<SyncResponse> requestResponse = getTrakt().sync().deleteItemsFromWatchlist(
                buildItemsForDeletion()).execute();
        assertSuccessfulResponse(requestResponse);
        assertSyncResponseDelete(requestResponse.body());
    }


    private MovieIds buildMovieIds() {
        return MovieIds.tmdb(TestData.MOVIE_TMDB_ID);
    }

    private ShowIds buildShowIds() {
        return ShowIds.slug("the-walking-dead");
    }

}
