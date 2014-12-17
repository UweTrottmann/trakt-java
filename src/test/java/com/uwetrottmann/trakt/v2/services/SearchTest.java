package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.SearchResult;
import com.uwetrottmann.trakt.v2.enums.IdType;
import com.uwetrottmann.trakt.v2.enums.Type;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends BaseTestCase {

    @Test
    public void test_textQuery_show() {
        List<SearchResult> results = getTrakt().search().textQuery("House", Type.SHOW, 1, DEFAULT_PAGE_SIZE);
        assertThat(results).isNotEmpty();
        for (SearchResult result : results) {
            assertThat(result.score).isPositive();
            assertThat(result.show).isNotNull();
        }
    }

    @Test
    public void test_textQuery_movie() {
        List<SearchResult> results = getTrakt().search().textQuery("Tron", Type.MOVIE, 1, DEFAULT_PAGE_SIZE);
        assertThat(results).isNotEmpty();
        for (SearchResult result : results) {
            assertThat(result.score).isPositive();
            assertThat(result.movie).isNotNull();
        }
    }

    @Test
    public void test_textQuery_person() {
        List<SearchResult> results = getTrakt().search().textQuery("Bryan Cranston", Type.PERSON, 1, DEFAULT_PAGE_SIZE);
        assertThat(results).isNotEmpty();
        for (SearchResult result : results) {
            assertThat(result.score).isPositive();
            assertThat(result.person).isNotNull();
        }
    }

    @Test
    public void test_idLookup() {
        List<SearchResult> results = getTrakt().search().idLookup(IdType.TVDB, String.valueOf(TestData.SHOW_TVDB_ID), 1,
                DEFAULT_PAGE_SIZE);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).score).isNull();

        results = getTrakt().search().idLookup(IdType.TMDB, String.valueOf(TestData.MOVIE_TMDB_ID), 1,
                DEFAULT_PAGE_SIZE);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).score).isNull();
    }

}
