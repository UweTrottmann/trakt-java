package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Show;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ShowsTest extends BaseTestCase {

    @Test
    public void test_popular() {
        List<Show> shows = getTrakt().shows().popular();
        assertThat(shows).isNotEmpty();
    }

    @Test
    public void test_trending() {
        List<Show> shows = getTrakt().shows().trending();
        assertThat(shows).isNotEmpty();
    }

    @Test
    public void test_summary_slug() {
        Show show = getTrakt().shows().summary(TestData.SHOW_SLUG);
        assertTestShow(show);
    }

    @Test
    public void test_summary_trakt_id() {
        Show show = getTrakt().shows().summary(TestData.SHOW_TRAKT_ID);
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

}
