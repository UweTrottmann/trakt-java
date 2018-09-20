package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Season;
import com.uwetrottmann.trakt5.entities.Stats;
import com.uwetrottmann.trakt5.enums.Extended;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SeasonsTest extends BaseTestCase {

    @Test
    public void test_summary() throws IOException {
        List<Season> seasons = executeCall(getTrakt().seasons().summary(TestData.SHOW_SLUG,
        Extended.FULLEPISODES));
        assertThat(seasons).isNotNull();
        assertThat(seasons).hasSize(5);
        for (Season season : seasons) {
            assertThat(season).isNotNull();
            // must have at least trakt and tvdb id
            assertThat(season.ids.trakt).isPositive();
            if (season.ids.tvdb != null) {
                assertThat(season.ids.tvdb).isPositive();
            }
            assertThat(season.title).isNotNull();
            assertThat(season.network).isNotNull();
            // seasons start at 0 for specials
            assertThat(season.number).isGreaterThanOrEqualTo(0);
            assertThat(season.episode_count).isPositive();
            assertThat(season.aired_episodes).isGreaterThanOrEqualTo(0);
            assertThat(season.rating).isBetween(0.0, 10.0);
            assertThat(season.votes).isGreaterThanOrEqualTo(0);
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
                null));
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
