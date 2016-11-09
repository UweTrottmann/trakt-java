package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.BaseEpisode;
import com.uwetrottmann.trakt5.entities.BaseSeason;
import com.uwetrottmann.trakt5.entities.BaseShow;
import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Credits;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Show;
import com.uwetrottmann.trakt5.entities.Stats;
import com.uwetrottmann.trakt5.entities.Translation;
import com.uwetrottmann.trakt5.entities.TrendingShow;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.Type;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShowsTest extends BaseTestCase {

    @Test
    public void test_popular() throws IOException {
        List<Show> shows = executeCall(getTrakt().shows().popular(2, null, Extended.DEFAULT_MIN));
        assertThat(shows).isNotNull();
        assertThat(shows.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
        for (Show show : shows) {
            assertShowNotNull(show);
        }
    }

    @Test
    public void test_trending() throws IOException {
        List<TrendingShow> shows = executeCall(getTrakt().shows().trending(1, null, Extended.DEFAULT_MIN));
        assertThat(shows).isNotNull();
        assertThat(shows.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
        for (TrendingShow show : shows) {
            assertThat(show.watchers).isNotNull();
            assertShowNotNull(show.show);
        }
    }

    private void assertShowNotNull(Show show) {
        assertThat(show.title).isNotEmpty();
        assertThat(show.ids).isNotNull();
        assertThat(show.ids.trakt).isNotNull();
        assertThat(show.year).isNotNull();
    }

    @Test
    public void test_summary_slug() throws IOException {
        Show show = executeCall(getTrakt().shows().summary(TestData.SHOW_SLUG, Extended.FULL));
        assertTestShow(show);
    }

    @Test
    public void test_summary_trakt_id() throws IOException {
        Show show = executeCall(
                getTrakt().shows().summary(String.valueOf(TestData.SHOW_TRAKT_ID), Extended.FULL));
        assertTestShow(show);
    }

    private void assertTestShow(Show show) {
        assertThat(show).isNotNull();
        assertThat(show.title).isEqualTo(TestData.SHOW_TITLE);
        assertThat(show.year).isEqualTo(TestData.SHOW_YEAR);
        assertThat(show.ids).isNotNull();
        assertThat(show.ids.trakt).isEqualTo(TestData.SHOW_TRAKT_ID);
        assertThat(show.ids.slug).isEqualTo(TestData.SHOW_SLUG);
        assertThat(show.ids.imdb).isEqualTo(TestData.SHOW_IMDB_ID);
        assertThat(show.ids.tmdb).isEqualTo(TestData.SHOW_TMDB_ID);
        assertThat(show.ids.tvdb).isEqualTo(TestData.SHOW_TVDB_ID);
        assertThat(show.ids.tvrage).isEqualTo(TestData.SHOW_TVRAGE_ID);
    }

    @Test
    public void test_translations() throws IOException {
        List<Translation> translations = executeCall(getTrakt().shows().translations("breaking-bad"));
        assertThat(translations).isNotNull();
        for (Translation translation : translations) {
            assertThat(translation.language).isNotEmpty();
        }
    }

    @Test
    public void test_translation() throws IOException {
        List<Translation> translations = executeCall(getTrakt().shows().translation("breaking-bad", "de"));
        // we know that Breaking Bad has a German translation, otherwise this test would fail
        assertThat(translations).isNotNull();
        assertThat(translations).hasSize(1);
        assertThat(translations.get(0).language).isEqualTo("de");
    }

    @Test
    public void test_comments() throws IOException {
        List<Comment> comments = executeCall(getTrakt().shows().comments(TestData.SHOW_SLUG, 1, null,
                Extended.DEFAULT_MIN));
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
    }

    @Test
    public void test_people() throws IOException {
        Credits credits = executeCall(getTrakt().shows().people(TestData.SHOW_SLUG));
        assertCast(credits, Type.PERSON);
        assertCrew(credits, Type.PERSON);
    }

    @Test
    public void test_ratings() throws IOException {
        Ratings ratings = executeCall(getTrakt().shows().ratings(TestData.SHOW_SLUG));
        assertRatings(ratings);
    }

    @Test
    public void test_stats() throws IOException {
        Stats stats = executeCall(getTrakt().shows().stats(TestData.SHOW_SLUG));
        assertShowStats(stats);
    }

    @Test
    public void test_collected_progress() throws IOException {
        BaseShow show = executeCall(getTrakt().shows().collectedProgress(TestData.SHOW_SLUG, null, null,
                Extended.DEFAULT_MIN));
        assertCollectedProgress(show);
    }

    @Test
    public void test_watched_progress() throws IOException {
        BaseShow show = executeCall(getTrakt().shows().watchedProgress(TestData.SHOW_SLUG, null, null,
                Extended.DEFAULT_MIN));
        assertWatchedProgress(show);
    }

    private void assertCollectedProgress(BaseShow show) {
        assertThat(show).isNotNull();
        assertThat(show.last_collected_at).isNotNull();
        assertProgress(show);
    }

    private void assertWatchedProgress(BaseShow show) {
        assertThat(show).isNotNull();
        assertThat(show.last_watched_at).isNotNull();
        assertProgress(show);
    }

    private void assertProgress(BaseShow show) {
        assertThat(show.aired).isGreaterThan(60);
        assertThat(show.completed).isGreaterThanOrEqualTo(1);

        // Breaking Bad has 5 seasons
        assertThat(show.seasons).hasSize(5);

        BaseSeason season = show.seasons.get(0);
        assertThat(season.number).isEqualTo(1);
        // all aired
        assertThat(season.aired).isEqualTo(7);
        // always at least 1 watched
        assertThat(season.completed).isGreaterThanOrEqualTo(1);

        // episode 1 should always be watched
        BaseEpisode episode = season.episodes.get(0);
        assertThat(episode.number).isEqualTo(1);
        assertThat(episode.completed).isTrue();
    }

}
