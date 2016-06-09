package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Season;
import com.uwetrottmann.trakt5.enums.Extended;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SeasonsTest extends BaseTestCase {

    @Test
    public void test_summary() throws IOException {
        List<Season> seasons = executeCall(getTrakt().seasons().summary(TestData.SHOW_SLUG,
                Extended.FULLIMAGES));
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
            assertThat(season.images.poster.full).isNotNull();
            assertThat(season.images.poster.medium).isNotNull();
            assertThat(season.images.poster.thumb).isNotNull();
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

}
