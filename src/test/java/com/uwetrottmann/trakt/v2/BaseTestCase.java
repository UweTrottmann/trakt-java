package com.uwetrottmann.trakt.v2;

import com.uwetrottmann.trakt.v2.entities.CastMember;
import com.uwetrottmann.trakt.v2.entities.Credits;
import com.uwetrottmann.trakt.v2.entities.CrewMember;
import com.uwetrottmann.trakt.v2.entities.Ratings;
import com.uwetrottmann.trakt.v2.enums.Type;
import org.junit.BeforeClass;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseTestCase {

    protected static final String TEST_CLIENT_ID = "e683ed71dd4a4afe73ba73151a4645f511b8703464a7807045088c733ef8d634";
    public static final String TEST_ACCESS_TOKEN = "441cb73d1c6387540ebe83af86e8bac5c209d402a66c429b9f4a3374359df9fc"; // "uwe" on v2 test server

    private static final boolean DEBUG = true;

    private static final TraktV2 trakt = new TraktV2();
    protected static final Integer DEFAULT_PAGE_SIZE = 10;

    @BeforeClass
    public static void setUpOnce() {
        trakt.setApiKey(TEST_CLIENT_ID);
        trakt.setAccessToken(TEST_ACCESS_TOKEN);
        trakt.setIsDebug(DEBUG);
    }

    protected final TraktV2 getTrakt() {
        return trakt;
    }

    public void assertRatings(Ratings ratings) {
        // rating can be null, but we use a show where we can be sure it's rated
        assertThat(ratings.rating).isGreaterThanOrEqualTo(0);
        assertThat(ratings.votes).isGreaterThanOrEqualTo(0);
        assertThat(ratings.distribution).hasSize(10);
    }

    public void assertCast(Credits credits, Type type) {
        for (CastMember castMember : credits.cast) {
            assertThat(castMember.character).isNotEmpty();
            if (type == Type.SHOW) {
                assertThat(castMember.movie).isNull();
                assertThat(castMember.show).isNotNull();
                assertThat(castMember.person).isNull();
            } else if (type == Type.MOVIE) {
                assertThat(castMember.movie).isNotNull();
                assertThat(castMember.show).isNull();
                assertThat(castMember.person).isNull();
            } else if (type == Type.PERSON) {
                assertThat(castMember.movie).isNull();
                assertThat(castMember.show).isNull();
                assertThat(castMember.person).isNotNull();
            }
        }
    }

    public void assertCrew(Credits credits, Type type) {
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

    public void assertCrewMembers(List<CrewMember> crew, Type type) {
        if (crew == null) {
            return;
        }
        for (CrewMember crewMember : crew) {
            assertThat(crewMember.job).isNotNull(); // may be empty, so not checking for now
            if (type == Type.SHOW) {
                assertThat(crewMember.movie).isNull();
                assertThat(crewMember.show).isNotNull();
                assertThat(crewMember.person).isNull();
            } else if (type == Type.MOVIE) {
                assertThat(crewMember.movie).isNotNull();
                assertThat(crewMember.show).isNull();
                assertThat(crewMember.person).isNull();
            } else if (type == Type.PERSON) {
                assertThat(crewMember.movie).isNull();
                assertThat(crewMember.show).isNull();
                assertThat(crewMember.person).isNotNull();
            }
        }
    }

}
