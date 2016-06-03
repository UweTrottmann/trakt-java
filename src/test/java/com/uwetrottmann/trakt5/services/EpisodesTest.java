package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.enums.Extended;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EpisodesTest extends BaseTestCase {

    @Test
    public void test_summary() {
        Episode episode = getTrakt().episodes().summary(String.valueOf(TestData.SHOW_TRAKT_ID), TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER, Extended.FULLIMAGES);
        assertThat(episode.title).isEqualTo(TestData.EPISODE_TITLE);
        assertThat(episode.season).isEqualTo(TestData.EPISODE_SEASON);
        assertThat(episode.number).isEqualTo(TestData.EPISODE_NUMBER);
        assertThat(episode.ids.imdb).isEqualTo(TestData.EPISODE_IMDB_ID);
        assertThat(episode.ids.tmdb).isEqualTo(TestData.EPISODE_TMDB_ID);
        assertThat(episode.ids.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID);
    }

    @Test
    public void test_comments() {
        getTrakt().episodes().comments(TestData.SHOW_SLUG, TestData.EPISODE_SEASON, TestData.EPISODE_NUMBER,
                1, DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN);
    }

    @Test
    public void test_ratings() {
        Ratings ratings = getTrakt().episodes().ratings(TestData.SHOW_SLUG, TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER);
        assertRatings(ratings);
    }

}
