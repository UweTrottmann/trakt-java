package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.Ratings;
import com.uwetrottmann.trakt.v2.entities.Season;
import com.uwetrottmann.trakt.v2.enums.Extended;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SeasonsTest extends BaseTestCase {

    @Test
    public void test_summary() {
        List<Season> seasons = getTrakt().seasons().summary(TestData.SHOW_SLUG, Extended.FULLIMAGES);
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
    public void test_season() {
        List<Episode> season = getTrakt().seasons().season(TestData.SHOW_SLUG, TestData.EPISODE_SEASON,
                Extended.DEFAULT_MIN);
        assertThat(season).isNotEmpty();
        for (Episode episode : season) {
            assertThat(episode.season).isEqualTo(TestData.EPISODE_SEASON);
        }
    }

    @Test
    public void test_comments() {
        getTrakt().seasons().comments(TestData.SHOW_SLUG, TestData.EPISODE_SEASON);
    }

    @Test
    public void test_ratings() {
        Ratings ratings = getTrakt().seasons().ratings(TestData.SHOW_SLUG, TestData.EPISODE_SEASON);
        assertRatings(ratings);
    }

}
