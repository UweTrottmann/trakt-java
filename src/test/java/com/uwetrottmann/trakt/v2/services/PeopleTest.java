package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.entities.Person;
import com.uwetrottmann.trakt.v2.enums.Extended;
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

}
