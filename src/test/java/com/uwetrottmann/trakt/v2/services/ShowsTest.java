package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Show;
import com.uwetrottmann.trakt.v2.entities.TrendingShow;
import com.uwetrottmann.trakt.v2.enums.Extended;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShowsTest extends BaseTestCase {

    @Test
    public void test_popular() {
        List<Show> shows = getTrakt().shows().popular();
        for (Show show : shows) {
            assertShowNotNull(show);
        }
    }

    @Test
    public void test_trending() {
        List<TrendingShow> shows = getTrakt().shows().trending();
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
    public void test_comments() {
        getTrakt().shows().comments(TestData.SHOW_SLUG);
    }

}
