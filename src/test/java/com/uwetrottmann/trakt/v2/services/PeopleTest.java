package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.entities.Credits;
import com.uwetrottmann.trakt.v2.entities.Person;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.enums.Type;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PeopleTest extends BaseTestCase {

    private static final String TEST_PERSON_SLUG = "bryan-cranston";

    @Test
    public void test_summary() {
        Person person = getTrakt().people().summary(TEST_PERSON_SLUG, Extended.FULLIMAGES);
        assertThat(person.name).isNotEmpty();
        assertThat(person.ids).isNotNull();
        assertThat(person.ids.trakt).isNotNull();
        assertThat(person.ids.slug).isNotNull();
        assertThat(person.images).isNotNull();
    }

    @Test
    public void test_movieCredits() {
        Credits credits = getTrakt().people().movieCredits(TEST_PERSON_SLUG);
        assertCast(credits, Type.MOVIE);
        assertCrew(credits, Type.MOVIE);
    }

    @Test
    public void test_showCredits() {
        Credits credits = getTrakt().people().showCredits(TEST_PERSON_SLUG);
        assertCast(credits, Type.SHOW);
        assertCrew(credits, Type.SHOW);
    }

}
