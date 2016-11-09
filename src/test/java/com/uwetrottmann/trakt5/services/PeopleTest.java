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
