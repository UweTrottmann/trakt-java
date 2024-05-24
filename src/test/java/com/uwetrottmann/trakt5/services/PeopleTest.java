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
import com.uwetrottmann.trakt5.entities.Credits;
import com.uwetrottmann.trakt5.entities.Person;
import com.uwetrottmann.trakt5.enums.Extended;
import com.uwetrottmann.trakt5.enums.Type;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PeopleTest extends BaseTestCase {

    private static final String TEST_PERSON_SLUG = "bryan-cranston";

    @Test
    public void test_summary() throws IOException {
        Person person = executeCall(getTrakt().people().summary(TEST_PERSON_SLUG, Extended.FULL));
        assertThat(person).isNotNull();
        assertThat(person.name).isNotEmpty();
        assertThat(person.ids).isNotNull();
        assertThat(person.ids.trakt).isNotNull();
        assertThat(person.ids.slug).isNotNull();
    }

    @Test
    public void test_movieCredits() throws IOException {
        Credits credits = executeCall(getTrakt().people().movieCredits(TEST_PERSON_SLUG));
        assertCast(credits, Type.MOVIE);
        assertCrew(credits, Type.MOVIE);
    }

    @Test
    public void test_showCredits() throws IOException {
        Credits credits = executeCall(getTrakt().people().showCredits(TEST_PERSON_SLUG));
        assertCast(credits, Type.SHOW);
        assertCrew(credits, Type.SHOW);
    }

}
