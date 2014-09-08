package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.SearchResult;
import com.uwetrottmann.trakt.v2.enums.IdType;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends BaseTestCase {

    @Test
    public void test_textQuery() {
        List<SearchResult> results = getTrakt().search().textQuery("House");
        assertThat(results).isNotEmpty();
        for (SearchResult result : results) {
            assertThat(result.score).isPositive();
        }
    }

    @Test
    public void test_idLookup() {
        List<SearchResult> results = getTrakt().search().idLookup(IdType.TVDB, String.valueOf(TestData.SHOW_TVDB_ID));
        assertThat(results).hasSize(1);
        assertThat(results.get(0).score).isNull();

        results = getTrakt().search().idLookup(IdType.TMDB, String.valueOf(TestData.MOVIE_TMDB_ID));
        assertThat(results).hasSize(1);
        assertThat(results.get(0).score).isNull();
    }

}
