package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Show;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ShowsTest extends BaseTestCase {

    @Test
    public void test_summary_slug() {
        Show show = getTrakt().shows().summary(TestData.SHOW_SLUG);
        assertThat(show).isNotNull();
        assertThat(show.ids).isNotNull();
        assertThat(show.ids.slug).isEqualTo(TestData.SHOW_SLUG);
    }

    @Test
    public void test_summary_trakt_id() {
        Show show = getTrakt().shows().summary(TestData.SHOW_TRAKT_ID);
        assertThat(show).isNotNull();
        assertThat(show.ids).isNotNull();
        assertThat(show.ids.trakt).isEqualTo(TestData.SHOW_TRAKT_ID);
    }

}
