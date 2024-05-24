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
import com.uwetrottmann.trakt5.entities.Genre;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GenresTest extends BaseTestCase {

    @Test
    public void test_genres_shows() throws IOException {
        List<Genre> genres = executeCall(getTrakt().genres().shows());
        assertGenres(genres);
    }

    @Test
    public void test_genres_movies() throws IOException {
        List<Genre> genres = executeCall(getTrakt().genres().movies());
        assertGenres(genres);
    }

    private void assertGenres(List<Genre> genres) {
        for (Genre genre : genres) {
            assertThat(genre.name).isNotEmpty();
            assertThat(genre.slug).isNotEmpty();
        }
    }

}
