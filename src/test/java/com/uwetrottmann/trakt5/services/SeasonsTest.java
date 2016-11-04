package com.uwetrottmann.trakt5.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Season;
import com.uwetrottmann.trakt5.entities.Stats;
import com.uwetrottmann.trakt5.enums.Extended;

public class SeasonsTest extends BaseTestCase {

    @Test
    public void test_summary() throws IOException {
        List<Season> seasons = executeCall(getTrakt().seasons().summary(TestData.SHOW_SLUG,
        Extended.FULLEPISODES));
        assertThat(seasons).isNotNull();
        assertThat(seasons).hasSize(6);
        for (Season season : seasons) {
            assertThat(season).isNotNull();
            // must have at least trakt and tvdb id
            assertThat(season.ids.trakt).isPositive();
            assertThat(season.ids.tvdb).isPositive();
            // 5 seasons + sepcials for Breaking Bad
            assertThat(season.number).isBetween(0, 5);
            assertThat(season.episode_count).isPositive();
            assertThat(season.aired_episodes).isPositive();
            assertThat(season.rating).isBetween(0.0, 10.0);
            assertThat(season.votes).isPositive();
            // Breaking Bad seasons all have a poster
            /* trakt.tv does not offer images any more
            assertThat(season.images.poster.full).isNotNull();
            assertThat(season.images.poster.medium).isNotNull();
            assertThat(season.images.poster.thumb).isNotNull(); */
            // episode details
            if (season.number == TestData.EPISODE_SEASON) {
                assertThat(season.episodes).isNotNull();
                assertThat(season.episodes).isNotEmpty();
                Episode firstEp = null;
                for (Episode episode : season.episodes) {
                    if (episode.number == TestData.EPISODE_NUMBER) {
                        firstEp = episode;
                        break;
                    }
                }
                assertThat(firstEp).isNotNull();
                assertThat(firstEp.title).isEqualTo(TestData.EPISODE_TITLE);
                assertThat(firstEp.season).isEqualTo(TestData.EPISODE_SEASON);
                assertThat(firstEp.number).isEqualTo(TestData.EPISODE_NUMBER);
                assertThat(firstEp.ids.imdb).isEqualTo(TestData.EPISODE_IMDB_ID);
                assertThat(firstEp.ids.tmdb).isEqualTo(TestData.EPISODE_TMDB_ID);
                assertThat(firstEp.ids.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID);
                assertThat(firstEp.overview).isNotEmpty();
            }
        }
    }

    @Test
    public void test_season() throws IOException {
        List<Episode> season = executeCall(getTrakt().seasons().season(TestData.SHOW_SLUG, TestData.EPISODE_SEASON,
                Extended.DEFAULT_MIN));
        assertThat(season).isNotNull();
        assertThat(season).isNotEmpty();
        for (Episode episode : season) {
            assertThat(episode.season).isEqualTo(TestData.EPISODE_SEASON);
        }
    }

    @Test
    public void test_comments() throws IOException {
        executeCall(getTrakt().seasons().comments(TestData.SHOW_SLUG, TestData.EPISODE_SEASON));
    }

    @Test
    public void test_ratings() throws IOException {
        Ratings ratings = executeCall(getTrakt().seasons().ratings(TestData.SHOW_SLUG, TestData.EPISODE_SEASON));
        assertRatings(ratings);
    }

    @Test
    public void test_stats() throws IOException {
        Stats stats = executeCall(getTrakt().seasons().stats(TestData.SHOW_SLUG, TestData.EPISODE_SEASON));
        assertShowStats(stats);
    }

}
