package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.TvShow;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class SearchServiceTest extends BaseTestCase {

    @Test
    public void test_shows() {
        List<TvShow> shows = getManager().searchService().shows("big bang theory");
        assertThat(shows).isNotEmpty();
        assertThat(shows.get(0)).isNotNull();
        assertThat(shows.get(0).title).isEqualTo("The Big Bang Theory");
    }

}
