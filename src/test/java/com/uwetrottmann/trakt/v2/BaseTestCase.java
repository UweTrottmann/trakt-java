package com.uwetrottmann.trakt.v2;

import com.uwetrottmann.trakt.v2.entities.BaseEpisode;
import com.uwetrottmann.trakt.v2.entities.BaseMovie;
import com.uwetrottmann.trakt.v2.entities.BaseRatedEntity;
import com.uwetrottmann.trakt.v2.entities.BaseSeason;
import com.uwetrottmann.trakt.v2.entities.BaseShow;
import com.uwetrottmann.trakt.v2.entities.CastMember;
import com.uwetrottmann.trakt.v2.entities.Credits;
import com.uwetrottmann.trakt.v2.entities.CrewMember;
import com.uwetrottmann.trakt.v2.entities.Ratings;
import com.uwetrottmann.trakt.v2.enums.Type;
import org.junit.BeforeClass;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseTestCase {

    protected static final String TEST_CLIENT_ID = "35a671df22d3d98b09aab1c0bc52977e902e696a7704cab94f4d12c2672041e4";
    public static final String TEST_ACCESS_TOKEN = "387c8e9b9d88177f6e3a35692f579101c74dde371ff3cfc83b29682b669a1e57"; // "sgtest" on production

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

    protected static <T extends BaseRatedEntity> void assertRatedEntities(List<T> ratedMovies) {
        for (BaseRatedEntity movie : ratedMovies) {
            assertThat(movie.rated_at).isNotNull();
            assertThat(movie.rating).isNotNull();
        }
    }

    public void assertRatings(Ratings ratings) {
        // rating can be null, but we use a show where we can be sure it's rated
        assertThat(ratings.rating).isGreaterThanOrEqualTo(0);
        assertThat(ratings.votes).isGreaterThanOrEqualTo(0);
        assertThat(ratings.distribution).hasSize(10);
    }

    protected static void assertSyncMovies(List<BaseMovie> movies, String type) {
        for (BaseMovie movie : movies) {
            assertThat(movie.movie).isNotNull();
            switch (type) {
                case "collection":
                    assertThat(movie.collected_at).isNotNull();
                    break;
                case "watched":
                    assertThat(movie.plays).isPositive();
                    assertThat(movie.last_watched_at).isNotNull();
                    break;
                case "watchlist":
                    assertThat(movie.listed_at).isNotNull();
                    break;
            }
        }
    }

    protected static void assertSyncShows(List<BaseShow> shows, String type) {
        for (BaseShow show : shows) {
            assertThat(show.show).isNotNull();
            if ("collection".equals(type)) {
                assertThat(show.last_collected_at).isNotNull();
            } else if ("watched".equals(type)) {
                assertThat(show.plays).isPositive();
                assertThat(show.last_watched_at).isNotNull();
            }

            for (BaseSeason season : show.seasons) {
                assertThat(season.number).isGreaterThanOrEqualTo(0);
                for (BaseEpisode episode : season.episodes) {
                    assertThat(episode.number).isGreaterThanOrEqualTo(0);

                    if ("collection".equals(type)) {
                        assertThat(episode.collected_at).isNotNull();
                    } else if ("watched".equals(type)) {
                        assertThat(episode.plays).isPositive();
                        assertThat(episode.last_watched_at).isNotNull();
                    }
                }
            }
        }
    }

    public void assertCast(Credits credits, Type type) {
        for (CastMember castMember : credits.cast) {
            assertThat(castMember.character).isNotNull();
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
