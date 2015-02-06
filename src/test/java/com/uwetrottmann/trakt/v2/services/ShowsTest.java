package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.BaseEpisode;
import com.uwetrottmann.trakt.v2.entities.BaseSeason;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Credits;
import com.uwetrottmann.trakt.v2.entities.Ratings;
import com.uwetrottmann.trakt.v2.entities.Show;
import com.uwetrottmann.trakt.v2.entities.Translation;
import com.uwetrottmann.trakt.v2.entities.TrendingShow;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.enums.Type;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShowsTest extends BaseTestCase {

    @Test
    public void test_popular() {
        List<Show> shows = getTrakt().shows().popular(2, null, Extended.DEFAULT_MIN);
        assertThat(shows.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
        for (Show show : shows) {
            assertShowNotNull(show);
        }
    }

    @Test
    public void test_trending() {
        List<TrendingShow> shows = getTrakt().shows().trending(1, null, Extended.DEFAULT_MIN);
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
    public void test_summary_slug() {
        Show show = getTrakt().shows().summary(TestData.SHOW_SLUG, Extended.FULLIMAGES);
        assertTestShow(show);
    }

    @Test
    public void test_summary_trakt_id() {
        Show show = getTrakt().shows().summary(String.valueOf(TestData.SHOW_TRAKT_ID), Extended.FULLIMAGES);
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
    public void test_translations() {
        List<Translation> translations = getTrakt().shows().translations("breaking-bad");
        for (Translation translation : translations) {
            assertThat(translation.language).isNotEmpty();
        }
    }

    @Test
    public void test_translation() {
        List<Translation> translations = getTrakt().shows().translation("breaking-bad", "de");
        // we know that Breaking Bad has a German translation, otherwise this test would fail
        assertThat(translations).hasSize(1);
        assertThat(translations.get(0).language).isEqualTo("de");
    }

    @Test
    public void test_comments() {
        List<Comment> comments = getTrakt().shows().comments(TestData.SHOW_SLUG, 1, null, Extended.DEFAULT_MIN);
        assertThat(comments.size()).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE);
    }

    @Test
    public void test_people() {
        Credits credits = getTrakt().shows().people(TestData.SHOW_SLUG);
        assertCast(credits, Type.PERSON);
        assertCrew(credits, Type.PERSON);
    }

    @Test
    public void test_ratings() {
        Ratings ratings = getTrakt().shows().ratings(TestData.SHOW_SLUG);
        assertRatings(ratings);
    }

    @Test
    public void test_watched_progress() throws OAuthUnauthorizedException {
        BaseShow show = getTrakt().shows().watchedProgress(TestData.SHOW_SLUG, Extended.DEFAULT_MIN);
        assertWatchedProgress(show);
    }

    private void assertWatchedProgress(BaseShow show) {
        assertThat(show).isNotNull();
        assertThat(show.aired).isGreaterThan(60);
        assertThat(show.completed).isGreaterThan(10);
        assertThat(show.next_episode).isNotNull();

        // Breaking Bad has 5 seasons
        assertThat(show.seasons).hasSize(5);

        BaseSeason season = show.seasons.get(0);
        assertThat(season.number).isEqualTo(1);
        // all aired
        assertThat(season.aired).isEqualTo(7);
        // always at least 1 watched
        assertThat(season.completed).isGreaterThan(1);

        // episode 1 should always be watched
        BaseEpisode episode = season.episodes.get(0);
        assertThat(episode.number).isEqualTo(1);
        assertThat(episode.completed).isTrue();
    }

}
