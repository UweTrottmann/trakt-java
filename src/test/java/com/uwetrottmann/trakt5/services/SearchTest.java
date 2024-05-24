/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.SearchResult;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.IdType;
import com.uwetrottmann.trakt5.enums.Type;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends BaseTestCase {

    @Test
    public void test_textQuery_show() throws IOException {
        List<SearchResult> results = executeCall(getTrakt().search().textQueryShow("House",
                null, null, null, null, null, null, null, null, null,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE));
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        for (SearchResult result : results) {
            assertThat(result.score).isPositive();
            assertThat(result.show).isNotNull();
        }
    }

    @Test
    public void test_textQuery_show_withYear() throws IOException {
        List<SearchResult> results = executeCall(getTrakt().search().textQueryShow("Empire", "2015",
                null, null, null, null, null, null, null, null,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE));
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        for (SearchResult result : results) {
            assertThat(result.score).isPositive();
            assertThat(result.show).isNotNull();
        }
    }

    @Test
    public void test_textQuery_movie() throws IOException {
        List<SearchResult> results = executeCall(getTrakt().search().textQueryMovie("Tron",
                null, null, null, null, null, null, null,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE));
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        for (SearchResult result : results) {
            assertThat(result.score).isPositive();
            assertThat(result.movie).isNotNull();
        }
    }

    @Test
    public void test_textQuery_person() throws IOException {
        List<SearchResult> results = executeCall(getTrakt().search().textQuery(Type.PERSON, "Bryan Cranston",
                null, null, null, null, null, null,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE));
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        for (SearchResult result : results) {
            assertThat(result.score).isPositive();
            assertThat(result.person).isNotNull();
        }
    }

    @Test
    public void test_idLookup() throws IOException {
        List<SearchResult> results = executeCall(
                getTrakt().search().idLookup(IdType.TVDB, String.valueOf(TestData.SHOW_TVDB_ID), Type.SHOW,
                        Extended.FULL, 1, DEFAULT_PAGE_SIZE));
        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);

        results = executeCall(
                getTrakt().search().idLookup(IdType.TMDB, String.valueOf(TestData.MOVIE_TMDB_ID), Type.MOVIE,
                        null, 1, DEFAULT_PAGE_SIZE));
        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
    }

}
