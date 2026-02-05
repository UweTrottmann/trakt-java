/*
 * Copyright 2014-2024 Uwe Trottmann
 * Copyright 2015 Manuel Laggner
 * Copyright 2019 Chris Banes
 * Copyright 2020 Sam Malone
 * Copyright 2021 DefHead
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

package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.entities.AccessToken;
import com.uwetrottmann.trakt5.entities.BaseEpisode;
import com.uwetrottmann.trakt5.entities.BaseMovie;
import com.uwetrottmann.trakt5.entities.BaseRatedEntity;
import com.uwetrottmann.trakt5.entities.BaseSeason;
import com.uwetrottmann.trakt5.entities.BaseShow;
import com.uwetrottmann.trakt5.entities.CastMember;
import com.uwetrottmann.trakt5.entities.Credits;
import com.uwetrottmann.trakt5.entities.CrewMember;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.entities.Stats;
import com.uwetrottmann.trakt5.entities.TraktError;
import com.uwetrottmann.trakt5.enums.Type;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;

import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assume.assumeTrue;

public class BaseTestCase {

    private static final String TEST_CLIENT_ID;
    private static final String TEST_ACCESS_TOKEN;

    static {
        Properties secrets = tryToloadSecrets();

        TEST_CLIENT_ID = getVarFromEnvOrProperties(secrets, "TEST_CLIENT_ID");
        TEST_ACCESS_TOKEN = getVarFromEnvOrProperties(secrets, "TEST_ACCESS_TOKEN");

        checkVarNotEmpty(TEST_CLIENT_ID, "TEST_CLIENT_ID");
        checkVarNotEmpty(TEST_ACCESS_TOKEN, "TEST_ACCESS_TOKEN");
    }

    private static final boolean DEBUG = true;

    protected static final Integer DEFAULT_PAGE_SIZE = 10;

    private static TraktV2 trakt;
    private static TraktV2 traktNoAuth;

    static class TestTraktV2 extends TraktV2 {

        public TestTraktV2(String apiKey) {
            super(apiKey);
        }

        public TestTraktV2(String apiKey, String clientSecret, String redirectUri) {
            super(apiKey, clientSecret, redirectUri);
        }

        @Override
        protected void setOkHttpClientDefaults(OkHttpClient.Builder builder) {
            super.setOkHttpClientDefaults(builder);
            if (DEBUG) {
                // add logging
                // standard output is easier to read
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.out::println);
                boolean isCI = System.getenv("CI") != null;
                // Reduce log size on CI server. If there is a response issue, should test on dev machine!
                logging.setLevel(isCI ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.BODY);
                // Note: currently not logging headers in CI.
//                if (isCI) {
//                    logging.redactHeader(TraktV2.HEADER_TRAKT_API_KEY);
//                    logging.redactHeader(TraktV2.HEADER_AUTHORIZATION);
//                }
                builder.addNetworkInterceptor(logging);
            }
        }
    }

    protected String getClientId() {
        return TEST_CLIENT_ID;
    }

    protected String getAccessToken() {
        return TEST_ACCESS_TOKEN;
    }

    protected TraktV2 getTrakt() {
        if (trakt == null) {
            trakt = new TestTraktV2(getClientId());
            trakt.accessToken(getAccessToken());
        }
        return trakt;
    }

    protected TraktV2 getUnauthenticatedTrakt() {
        if (traktNoAuth == null) {
            traktNoAuth = new TestTraktV2(getClientId());
        }
        return traktNoAuth;
    }

    /**
     * Execute call with non-Void response body.
     */
    public <T> T executeCall(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (!response.isSuccessful()) {
            handleFailedResponse(response); // will throw error
        }
        T body = response.body();
        if (body != null) {
            return body;
        } else {
            throw new IllegalStateException("Body should not be null for successful response");
        }
    }

    /**
     * Execute call with non-Void response body, without extracting it.
     */
    public <T> Response<T> executeCallWithoutReadingBody(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (!response.isSuccessful()) {
            handleFailedResponse(response); // will throw error
        }
        return response;
    }

    /**
     * Execute call with Void response body.
     */
    public <T> void executeVoidCall(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (!response.isSuccessful()) {
            handleFailedResponse(response); // will throw error
        }
    }

    public <T> void assertSuccessfulResponse(Response<T> response) {
        if (!response.isSuccessful()) {
            handleFailedResponse(response);
        }
    }

    private <T> void handleFailedResponse(Response<T> response) {
        if (TraktV2.isUnauthorized(response)) {
            fail("Authorization required, supply a valid OAuth access token: "
                    + response.code() + " " + response.message());
        } else {
            String message = "Request failed: " + response.code() + " " + response.message();
            TraktError error = getTrakt().checkForTraktError(response);
            if (error != null && error.message != null) {
                message += " message: " + error.message;
            }
            fail(message);
        }
    }

    protected static <T extends BaseRatedEntity> void assertRatedEntities(List<T> ratedEntities) {
        for (BaseRatedEntity ratedEntity : ratedEntities) {
            assertThat(ratedEntity.rated_at).isNotNull();
            assertThat(ratedEntity.rating).isNotNull();
        }
    }

    public void assertRatings(Ratings ratings) {
        // rating can be null, but we use a show where we can be sure it's rated
        assertThat(ratings.rating).isGreaterThanOrEqualTo(0);
        assertThat(ratings.votes).isGreaterThanOrEqualTo(0);
        assertThat(ratings.distribution).hasSize(10);
    }

    public void assertStats(Stats stats) {
        assertThat(stats.watchers).isGreaterThanOrEqualTo(0);
        assertThat(stats.plays).isGreaterThanOrEqualTo(0);
        assertThat(stats.collectors).isGreaterThanOrEqualTo(0);
        assertThat(stats.comments).isGreaterThanOrEqualTo(0);
        assertThat(stats.lists).isGreaterThanOrEqualTo(0);
        assertThat(stats.votes).isGreaterThanOrEqualTo(0);
    }

    public void assertShowStats(Stats stats) {
        assertStats(stats);
        assertThat(stats.collected_episodes).isGreaterThanOrEqualTo(0);
    }

    protected static void assertSyncMovies(List<BaseMovie> movies, String type) {
        assertThat(movies).isNotEmpty();
        for (BaseMovie movie : movies) {
            assertThat(movie.movie).isNotNull();
            switch (type) {
                case "collection":
                    assertThat(movie.collected_at).isNotNull();
                    break;
                case "watched":
                    assertThat(movie.plays).isPositive();
                    assertThat(movie.last_watched_at).isNotNull();
                    assertThat(movie.last_updated_at).isNotNull();
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
                assertThat(show.last_updated_at).isNotNull();
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

    public void assertCrewMembers(@Nullable List<CrewMember> crew, Type type) {
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

    /**
     * Checks that the promised pagination headers are returned and that the supplied page and limit parameters were
     * used.
     */
    public static void assertPaginationHeaders(Response<?> response, int expectedPage, int expectedLimit) {
        assertThat(TraktV2.getPageCount(response)).isNotNull();
        assertThat(TraktV2.getItemCount(response)).isNotNull();
        assertThat(response.headers().get("X-Pagination-Page")).isEqualTo(String.valueOf(expectedPage));
        assertThat(response.headers().get("X-Pagination-Limit")).isEqualTo(String.valueOf(expectedLimit));
    }

    public static void assertSortOrderHeaders(Response<?> response, String expectedSortBy, String expectedSortHow) {
        assertThat(response.headers().get("x-applied-sort-by")).isEqualTo(expectedSortBy);
        assertThat(response.headers().get("x-applied-sort-how")).isEqualTo(expectedSortHow);
    }

    protected void assertAccessTokenResponse(Response<AccessToken> response) {
        assertSuccessfulResponse(response);
        assertThat(response.body().access_token).isNotEmpty();
        assertThat(response.body().refresh_token).isNotEmpty();
        assertThat(response.body().created_at).isPositive();
        assertThat(response.body().expires_in).isPositive();

        System.out.println("Retrieved access token: " + response.body().access_token);
        System.out.println("Retrieved refresh token: " + response.body().refresh_token);
        System.out.println("Retrieved scope: " + response.body().scope);
        System.out.println("Retrieved expires in: " + response.body().expires_in + " seconds");
    }

    protected static Properties tryToloadSecrets() {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("secrets.properties")) {
            properties.load(input);
        } catch (IOException e) {
            // File not found or cannot be read, will try environment variables
        }

        return properties;
    }

    protected static String getVarFromEnvOrProperties(Properties properties, String key) {
        String value = System.getenv(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        return properties.getProperty(key);
    }

    private static void checkVarNotEmpty(String value, String name) {
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException(name + " must be set via environment variable or secrets.properties file");
        }
    }

    protected String getClientSecretFromEnvOrPropertiesOrIgnoreTest(Properties properties) {
        String clientSecret = getVarFromEnvOrProperties(properties, "TEST_CLIENT_SECRET");
        boolean hasClientSecret = clientSecret != null && !clientSecret.isEmpty();
        assumeTrue(
                "TEST_CLIENT_SECRET must be set via environment variable or secrets.properties file",
                hasClientSecret);
        return clientSecret;
    }

}
