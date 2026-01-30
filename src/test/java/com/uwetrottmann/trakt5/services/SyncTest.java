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
import com.uwetrottmann.trakt5.entities.BaseMovie;
import com.uwetrottmann.trakt5.entities.BaseShow;
import com.uwetrottmann.trakt5.entities.EpisodeIds;
import com.uwetrottmann.trakt5.entities.HistoryEntry;
import com.uwetrottmann.trakt5.entities.LastActivities;
import com.uwetrottmann.trakt5.entities.LastActivity;
import com.uwetrottmann.trakt5.entities.LastActivityAccount;
import com.uwetrottmann.trakt5.entities.LastActivityComments;
import com.uwetrottmann.trakt5.entities.LastActivityMore;
import com.uwetrottmann.trakt5.entities.LastActivityUpdated;
import com.uwetrottmann.trakt5.entities.ListsLastActivity;
import com.uwetrottmann.trakt5.entities.MovieIds;
import com.uwetrottmann.trakt5.entities.PlaybackResponse;
import com.uwetrottmann.trakt5.entities.RatedEpisode;
import com.uwetrottmann.trakt5.entities.RatedMovie;
import com.uwetrottmann.trakt5.entities.RatedSeason;
import com.uwetrottmann.trakt5.entities.RatedShow;
import com.uwetrottmann.trakt5.entities.ScrobbleProgress;
import com.uwetrottmann.trakt5.entities.ShowIds;
import com.uwetrottmann.trakt5.entities.SyncEpisode;
import com.uwetrottmann.trakt5.entities.SyncItems;
import com.uwetrottmann.trakt5.entities.SyncMovie;
import com.uwetrottmann.trakt5.entities.SyncResponse;
import com.uwetrottmann.trakt5.entities.SyncSeason;
import com.uwetrottmann.trakt5.entities.SyncShow;
import com.uwetrottmann.trakt5.entities.WatchlistedEpisode;
import com.uwetrottmann.trakt5.entities.WatchlistedSeason;
import com.uwetrottmann.trakt5.enums.HistoryType;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.RatingsFilter;
import org.junit.Test;
import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.temporal.ChronoUnit;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.uwetrottmann.trakt5.services.HistoryAssertions.assertEpisodeHistory;
import static com.uwetrottmann.trakt5.services.HistoryAssertions.assertHistory;
import static com.uwetrottmann.trakt5.services.HistoryAssertions.assertMovieHistory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SyncTest extends BaseTestCase {

    @Test
    public void test_lastActivites() throws IOException {
        LastActivities lastActivities = executeCall(getTrakt().sync().lastActivities());
        assertThat(lastActivities).isNotNull();
        assertThat(lastActivities.all).isNotNull();
        assertLastActivityMore(lastActivities.movies);
        assertLastActivityMore(lastActivities.episodes);
        assertLastActivity(lastActivities.shows);
        assertLastActivity(lastActivities.seasons);
        assertLastActivityComments(lastActivities.comments);
        assertListsLastActivity(lastActivities.lists);
        assertLastActivityUpdated(lastActivities.watchlist);
        assertLastActivityUpdated(lastActivities.favorites);
        assertLastActivityAccount(lastActivities.account);
        assertLastActivityUpdated(lastActivities.saved_filters);
        assertLastActivityUpdated(lastActivities.notes);
    }

    @Test
    public void test_getPlayback() throws IOException, InterruptedException {
        // Make sure there are paused entries.
        int agentsOfShield = 74005; /* S01E01 */
        SyncEpisode episode = new SyncEpisode().id(EpisodeIds.trakt(agentsOfShield));
        ScrobbleProgress episodeProgress = new ScrobbleProgress(episode, 25.0, null, null);
        PlaybackResponse episodeResponse = executeCall(getTrakt().scrobble().pauseWatching(episodeProgress));
        assertThat(episodeResponse.action).isEqualTo("pause");

        // Give the server some time to process the request.
        Thread.sleep(1500);

        int interstellar = 157336;
        SyncMovie movie = new SyncMovie().id(MovieIds.tmdb(157336));
        ScrobbleProgress movieProgress = new ScrobbleProgress(movie, 32.0, null, null);
        PlaybackResponse movieResponse = executeCall(getTrakt().scrobble().pauseWatching(movieProgress));
        assertThat(movieResponse.action).isEqualTo("pause");

        // Give the server some time to process the request.
        Thread.sleep(1500);

        List<PlaybackResponse> playbacks = executeCall(getTrakt().sync().playback(null, null, null, 10));
        assertThat(playbacks).isNotNull();
        boolean foundEpisode = false;
        boolean foundMovie = false;

        for (PlaybackResponse playback : playbacks) {
            assertThat(playback.type).isNotNull();

            if (playback.episode != null && playback.episode.ids != null && playback.episode.ids.trakt != null
                    && playback.episode.ids.trakt == agentsOfShield) {
                foundEpisode = true;
                assertThat(playback.paused_at).isNotNull();
                assertThat(playback.progress).isEqualTo(25.0);
            }

            if (playback.movie != null && playback.movie.ids != null && playback.movie.ids.tmdb != null
                    && playback.movie.ids.tmdb == interstellar) {
                foundMovie = true;
                assertThat(playback.paused_at).isNotNull();
                assertThat(playback.progress).isEqualTo(32.0);
            }
        }

        if (!foundEpisode) {
            fail("Agents of Shield episode not paused.");
        }
        if (!foundMovie) {
            fail("Interstellar movie not paused.");
        }

        for (PlaybackResponse playback : playbacks) {
            assertThat(playback.id).isNotNull();
            executeVoidCall(getTrakt().sync().removePlayback(playback.id));
        }
    }

    private void assertLastActivityMore(LastActivityMore activityMore) {
        assertLastActivity(activityMore);
        assertThat(activityMore.paused_at).isNotNull();
        assertThat(activityMore.collected_at).isNotNull();
        assertThat(activityMore.watched_at).isNotNull();
    }

    private void assertLastActivity(LastActivity activity) {
        assertThat(activity).isNotNull();
        assertThat(activity.commented_at).isNotNull();
        assertThat(activity.rated_at).isNotNull();
        assertThat(activity.watchlisted_at).isNotNull();
    }

    private void assertListsLastActivity(ListsLastActivity activity) {
        assertThat(activity).isNotNull();
        assertThat(activity.commented_at).isNotNull();
        assertThat(activity.liked_at).isNotNull();
        assertThat(activity.updated_at).isNotNull();
    }

    private void assertLastActivityComments(LastActivityComments activity) {
        assertThat(activity).isNotNull();
        assertThat(activity.liked_at).isNotNull();
        assertThat(activity.blocked_at).isNotNull();
    }

    private void assertLastActivityAccount(LastActivityAccount activity) {
        assertThat(activity).isNotNull();
        assertThat(activity.settings_at).isNotNull();
        assertThat(activity.followed_at).isNotNull();
        assertThat(activity.following_at).isNotNull();
        assertThat(activity.pending_at).isNotNull();
        assertThat(activity.requested_at).isNotNull();
    }

    private void assertLastActivityUpdated(LastActivityUpdated activity) {
        assertThat(activity).isNotNull();
        assertThat(activity.updated_at).isNotNull();
    }

    @Test
    public void test_collectionMovies() throws IOException {
        List<BaseMovie> movies = executeCall(getTrakt().sync().collectionMovies(1, 1000, null));
        assertSyncMovies(movies, "collection");
    }

    @Test
    public void test_collectionShows() throws IOException {
        List<BaseShow> shows = executeCall(getTrakt().sync().collectionShows(1, 1000, null));
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
        // Fri Jul 14 2017 02:40:00 UTC
        OffsetDateTime collectedAt = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1500000000000L), ZoneOffset.UTC);

        // episodes
        SyncEpisode episode1 = new SyncEpisode();
        episode1.number = 1;
        episode1.collectedAt(collectedAt);
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
        SyncResponse response = executeCall(getTrakt().sync().addItemsToCollection(items));
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
    public void test_deleteItemsFromCollection() throws IOException {
        SyncResponse response = executeCall(getTrakt().sync().deleteItemsFromCollection(buildItemsForDeletion()));
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
        List<BaseMovie> watchedMovies = executeCall(getTrakt().sync().watchedMovies(null));
        assertSyncMovies(watchedMovies, "watched");
    }

    @Test
    public void test_watchedShows() throws IOException {
        List<BaseShow> watchedShows = executeCall(getTrakt().sync().watchedShows(null));
        assertSyncShows(watchedShows, "watched");
    }

    @Test
    public void test_addItemsToWatchedHistory() throws IOException {
        // movie
        SyncMovie movie = new SyncMovie();
        // Trakt only stores minute precision for watched_at
        movie.watched_at = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusHours(1);
        movie.ids = buildMovieIds();

        // episode
        SyncEpisode episode = new SyncEpisode();
        episode.number = TestData.EPISODE_NUMBER;
        episode.watched_at = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusHours(1);
        SyncEpisode episode2 = new SyncEpisode();
        episode2.number = 2;
        episode2.watched_at = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(30);
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

        SyncResponse response = executeCall(getTrakt().sync().addItemsToWatchedHistory(items));
        assertThat(response).isNotNull();
        assertThat(response.added.movies).isNotNull();
        assertThat(response.added.episodes).isNotNull();
        assertThat(response.existing).isNull();
        assertThat(response.deleted).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_deleteItemsFromWatchedHistory() throws IOException {
        SyncItems items = buildItemsForDeletion();

        SyncResponse response = executeCall(getTrakt().sync().deleteItemsFromWatchedHistory(items));
        assertThat(response).isNotNull();
        assertThat(response.deleted.movies).isNotNull();
        assertThat(response.deleted.episodes).isNotNull();
        assertThat(response.added).isNull();
        assertThat(response.existing).isNull();
        assertThat(response.not_found).isNotNull();
    }

    @Test
    public void test_ratingsMovies() throws IOException {
        List<RatedMovie> ratedMovies = executeCall(getTrakt().sync().ratingsMovies(RatingsFilter.ALL, null, 1, 2));
        assertRatedEntities(ratedMovies);
    }

    @Test
    public void test_ratingsMovies_filtered() throws IOException {
        List<RatedMovie> ratedMovies = executeCall(getTrakt().sync().ratingsMovies(RatingsFilter.TOTALLYNINJA,
                null, null, null));
        assertThat(ratedMovies).isNotNull();
        for (RatedMovie movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isEqualTo(Rating.TOTALLYNINJA);
        }
    }

    @Test
    public void test_ratingsMovies_with_pagination() throws IOException {
        int page = 1;
        int limit = 2;
        Call<List<RatedMovie>> call = getTrakt().sync().ratingsMovies(RatingsFilter.ALL, null, page, limit);
        Response<List<RatedMovie>> response = executeCallWithoutReadingBody(call);

        assertPaginationHeaders(response, page, limit);
    }

    @Test
    public void test_ratingsShows() throws IOException {
        List<RatedShow> ratedShows = executeCall(getTrakt().sync().ratingsShows(RatingsFilter.ALL, null, null, null));
        assertRatedEntities(ratedShows);
    }

    @Test
    public void test_ratingsShows_with_pagination() throws IOException {
        int page = 1;
        int limit = 2;
        Call<List<RatedShow>> call = getTrakt().sync().ratingsShows(RatingsFilter.ALL, null, page, limit);
        Response<List<RatedShow>> response = executeCallWithoutReadingBody(call);

        assertPaginationHeaders(response, page, limit);
    }

    @Test
    public void test_ratingsSeasons() throws IOException {
        List<RatedSeason> ratedSeasons = executeCall(getTrakt().sync().ratingsSeasons(RatingsFilter.ALL, null, null,
                null));
        assertRatedEntities(ratedSeasons);
    }

    @Test
    public void test_ratingsSeasons_with_pagination() throws IOException {
        int page = 1;
        int limit = 5;
        Call<List<RatedSeason>> call = getTrakt().sync().ratingsSeasons(RatingsFilter.ALL, null, page, limit);
        Response<List<RatedSeason>> response = executeCallWithoutReadingBody(call);
        
        assertPaginationHeaders(response, page, limit);
    }

    @Test
    public void test_ratingsEpisodes() throws IOException {
        List<RatedEpisode> ratedEpisodes = executeCall(getTrakt().sync().ratingsEpisodes(RatingsFilter.ALL, null, null,
                null));
        assertRatedEntities(ratedEpisodes);
    }

    @Test
    public void test_ratingsEpisodes_with_pagination() throws IOException {
        int page = 1;
        int limit = 2;
        Call<List<RatedEpisode>> call = getTrakt().sync().ratingsEpisodes(RatingsFilter.ALL, null, page, limit);
        Response<List<RatedEpisode>> response = executeCallWithoutReadingBody(call);

        assertPaginationHeaders(response, page, limit);
    }

    @Test
    public void test_addRatings_movie() throws IOException {
        SyncMovie movie = new SyncMovie()
                .id(MovieIds.slug(TestData.MOVIE_SLUG))
                .rating(Rating.MEH);

        SyncItems items = new SyncItems().movies(movie);
        executeCall(getTrakt().sync().addRatings(items));
    }

    @Test
    public void test_addRatings_show() throws IOException {
        SyncShow show = new SyncShow()
                .id(ShowIds.slug(TestData.SHOW_SLUG))
                .rating(Rating.TERRIBLE);

        SyncItems items = new SyncItems().shows(show);
        executeCall(getTrakt().sync().addRatings(items));
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
        executeCall(getTrakt().sync().addRatings(items));
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
        executeCall(getTrakt().sync().addRatings(items));
    }

    @Test
    public void test_deleteRatings() throws IOException {
        SyncItems items = buildItemsForDeletion();

        SyncResponse response = executeCall(getTrakt().sync().deleteRatings(items));
        assertThat(response).isNotNull();
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
        List<BaseMovie> movies = executeCall(getTrakt().sync().watchlistMovies(null));
        assertSyncMovies(movies, "watchlist");
    }

    @Test
    public void test_watchlistShows() throws IOException {
        List<BaseShow> shows = executeCall(getTrakt().sync().watchlistShows(null));
        assertThat(shows).isNotNull();
        for (BaseShow show : shows) {
            assertThat(show.show).isNotNull();
            assertThat(show.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistSeasons() throws IOException {
        List<WatchlistedSeason> seasons = executeCall(getTrakt().sync().watchlistSeasons(null));
        assertThat(seasons).isNotNull();
        for (WatchlistedSeason season : seasons) {
            assertThat(season.season).isNotNull();
            assertThat(season.show).isNotNull();
            assertThat(season.listed_at).isNotNull();
        }
    }

    @Test
    public void test_watchlistEpisodes() throws IOException {
        List<WatchlistedEpisode> episodes = executeCall(getTrakt().sync().watchlistEpisodes(null));
        assertThat(episodes).isNotNull();
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
        SyncResponse requestResponse = executeCall(getTrakt().sync().addItemsToWatchlist(items));
        assertSyncResponse(requestResponse);
    }

    @Test
    public void test_deleteItemsFromWatchlist() throws IOException {
        SyncResponse requestResponse = executeCall(getTrakt().sync().deleteItemsFromWatchlist(
                buildItemsForDeletion()));
        assertSyncResponseDelete(requestResponse);
    }

    @Test
    public void test_history() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().sync().history(null, null, null, null, null)
        );
        assertHistory(history);
    }

    @Test
    public void test_history_withPagination() throws IOException {
        int page = 1;
        int limit = 5;
        Call<List<HistoryEntry>> call = getTrakt().sync().history(page, limit, null, null, null);
        Response<List<HistoryEntry>> response = executeCallWithoutReadingBody(call);

        assertPaginationHeaders(response, page, limit);

        List<HistoryEntry> body = response.body();
        if (body == null) {
            throw new IllegalStateException("Body should not be null for successful response");
        }
        assertHistory(body);
    }

    @Test
    public void test_history_episodes() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().sync().history(HistoryType.EPISODES, null, null, null, null, null)
        );

        assertEpisodeHistory(history);
    }

    @Test
    public void test_history_movies() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().sync().history(HistoryType.MOVIES, null, null, null, null, null));

        assertMovieHistory(history);
    }

    @Test
    public void test_history_item() throws IOException {
        List<HistoryEntry> history = executeCall(
                getTrakt().sync().history(HistoryType.MOVIES,
                        TestData.MOVIE_WATCHED_TRAKT_ID, null, null, null,
                        OffsetDateTime.of(2016, 8, 3, 9, 0, 0, 0, ZoneOffset.UTC),
                        OffsetDateTime.of(2016, 8, 3, 10, 0, 0, 0, ZoneOffset.UTC))
        );

        assertThat(history.size()).isGreaterThan(0);
        assertMovieHistory(history);
    }


    private MovieIds buildMovieIds() {
        return MovieIds.tmdb(TestData.MOVIE_TMDB_ID);
    }

    private ShowIds buildShowIds() {
        return ShowIds.slug("the-walking-dead");
    }

}
