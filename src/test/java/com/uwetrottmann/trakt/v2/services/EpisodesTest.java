package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Episode;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class EpisodesTest extends BaseTestCase {

    @Test
    public void test_summary() {
        Episode episode = getTrakt().episodes().summary(String.valueOf(TestData.SHOW_TRAKT_ID), TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER);
        assertThat(episode.title).isEqualTo(TestData.EPISODE_TITLE);
        assertThat(episode.season).isEqualTo(TestData.EPISODE_SEASON);
        assertThat(episode.number).isEqualTo(TestData.EPISODE_NUMBER);
        assertThat(episode.ids.imdb).isEqualTo(TestData.EPISODE_IMDB_ID);
        assertThat(episode.ids.tmdb).isEqualTo(TestData.EPISODE_TMDB_ID);
        assertThat(episode.ids.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID);
    }

    @Test
    public void test_comments() {
        getTrakt().episodes().comments(TestData.SHOW_SLUG, TestData.EPISODE_SEASON, TestData.EPISODE_NUMBER);
    }

}
