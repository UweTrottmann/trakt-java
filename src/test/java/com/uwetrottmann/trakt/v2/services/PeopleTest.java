package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.entities.CastMember;
import com.uwetrottmann.trakt.v2.entities.Credits;
import com.uwetrottmann.trakt.v2.entities.CrewMember;
import com.uwetrottmann.trakt.v2.entities.Person;
import com.uwetrottmann.trakt.v2.enums.Extended;
import com.uwetrottmann.trakt.v2.enums.Type;
import org.junit.Test;

import java.util.List;

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
        for (CastMember castMember : credits.cast) {
            assertThat(castMember.character).isNotEmpty();
            assertThat(castMember.movie).isNotNull();
            assertThat(castMember.show).isNull();
        }
        assertCrew(credits, Type.MOVIE);
    }

    @Test
    public void test_showCredits() {
        Credits credits = getTrakt().people().showCredits(TEST_PERSON_SLUG);
        for (CastMember castMember : credits.cast) {
            assertThat(castMember.character).isNotEmpty();
            assertThat(castMember.movie).isNull();
            assertThat(castMember.show).isNotNull();
        }
        assertCrew(credits, Type.SHOW);
    }

    private void assertCrew(Credits credits, Type type) {
        if (credits.crew != null) {
            assertCrewMembers(credits.crew.production, type);
            assertCrewMembers(credits.crew.writing, type);
            assertCrewMembers(credits.crew.directing, type);
            assertCrewMembers(credits.crew.costumeAndMakeUp, type);
            assertCrewMembers(credits.crew.sound, type);
            assertCrewMembers(credits.crew.art, type);
            assertCrewMembers(credits.crew.camera, type);
        }
    }

    private void assertCrewMembers(List<CrewMember> crew, Type type) {
        if (crew == null) {
            return;
        }
        for (CrewMember crewMember : crew) {
            assertThat(crewMember.job).isNotNull(); // may be empty, so not checking for now
            if (type == Type.SHOW) {
                assertThat(crewMember.movie).isNull();
                assertThat(crewMember.show).isNotNull();
            } else if (type == Type.MOVIE) {
                assertThat(crewMember.movie).isNotNull();
                assertThat(crewMember.show).isNull();
            }
        }
    }

}
