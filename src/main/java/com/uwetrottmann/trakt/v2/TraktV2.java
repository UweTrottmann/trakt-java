/*
 * Copyright 2014 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.uwetrottmann.trakt.v2;

import com.uwetrottmann.trakt.v2.services.Calendars;
import com.uwetrottmann.trakt.v2.services.Checkin;
import com.uwetrottmann.trakt.v2.services.Comments;
import com.uwetrottmann.trakt.v2.services.Episodes;
import com.uwetrottmann.trakt.v2.services.Genres;
import com.uwetrottmann.trakt.v2.services.Movies;
import com.uwetrottmann.trakt.v2.services.People;
import com.uwetrottmann.trakt.v2.services.Recommendations;
import com.uwetrottmann.trakt.v2.services.Search;
import com.uwetrottmann.trakt.v2.services.Seasons;
import com.uwetrottmann.trakt.v2.services.Shows;
import com.uwetrottmann.trakt.v2.services.Sync;
import com.uwetrottmann.trakt.v2.services.Users;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Helper class for easy usage of the trakt v2 API using retrofit.
 */
public class TraktV2 {

    /**
     * trakt API v2 URL.
     */
    public static final String SITE_URL = "https://trakt.tv";
    public static final String API_URL = "https://api-v2launch.trakt.tv";
    public static final String HEADER_TRAKT_API_VERSION_2 = "2";
    public static final String HEADER_CONTENT_TYPE_JSON = "application/json";

    public static final String OAUTH2_AUTHORIZATION_URL = SITE_URL + "/oauth/authorize";
    public static final String OAUTH2_TOKEN_URL = SITE_URL + "/oauth/token";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_TRAKT_API_KEY = "trakt-api-key";
    public static final String HEADER_TRAKT_API_VERSION = "trakt-api-version";

    /**
     * Build an OAuth 2.0 authorization request to obtain an authorization code.
     *
     * <p> Send the user to the location URI of this request. Once the user authorized your app, the server will
     * redirect to {@code redirectUri} with the authorization code and the sent state in the query parameter {@code
     * code}.
     *
     * <p> Ensure the state matches, then supply the authorization code to {@link #getAccessToken(String, String,
     * String, String)} to get an access token.
     *
     * @param clientId The OAuth client id obtained from trakt.
     * @param redirectUri The URI to redirect to with appended auth code and state query parameters.
     * @param state State variable to prevent request forgery attacks.
     * @param username Pre-fill the username field.
     * @return A trakt OAuth authorization request.
     * @throws OAuthSystemException
     */
    public static OAuthClientRequest getAuthorizationRequest(String clientId, String redirectUri, String state,
            String username) throws OAuthSystemException {
        OAuthClientRequest.AuthenticationRequestBuilder builder = OAuthClientRequest
                .authorizationLocation(OAUTH2_AUTHORIZATION_URL)
                .setResponseType(ResponseType.CODE.toString())
                .setClientId(clientId)
                .setRedirectURI(redirectUri)
                .setState(state);
        if (username != null && username.length() != 0) {
            builder.setParameter("username", username);
        }
        return builder.buildQueryMessage();
    }

    /**
     * Build an OAuth access token request.
     *
     * @param clientId The OAuth client id obtained from trakt.
     * @param clientSecret The OAuth client secret obtained from trakt.
     * @param redirectUri The redirect URI previously used for obtaining the auth code.
     * @param authCode A previously obtained auth code.
     * @return A trakt OAuth access token request.
     * @throws OAuthSystemException
     */
    public static OAuthClientRequest getAccessTokenRequest(String clientId, String clientSecret, String redirectUri,
            String authCode) throws OAuthSystemException {
        return OAuthClientRequest
                .tokenLocation(OAUTH2_TOKEN_URL)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setCode(authCode)
                .setRedirectURI(redirectUri)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .buildQueryMessage();
    }

    /**
     * Request an access token from trakt. Builds the request with {@link #getAccessTokenRequest(String, String, String,
     * String)} and executes it, then returns the response which includes the access token.
     *
     * <p> Supply the received access token to {@link #setAccessToken(String)}.
     *
     * <p> On failure re-authorization of your app is required (see {@link #getAuthorizationRequest(String, String,
     * String, String)}).
     *
     * @param clientId The OAuth client id obtained from trakt.
     * @param clientSecret The OAuth client secret obtained from trakt.
     * @param redirectUri The redirect URI previously used for obtaining the auth code.
     * @param authCode A valid authorization code (see {@link #getAuthorizationRequest(String, String, String,
     * String)}).
     */
    public static OAuthAccessTokenResponse getAccessToken(String clientId, String clientSecret, String redirectUri,
            String authCode) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request = getAccessTokenRequest(clientId, clientSecret, redirectUri, authCode);

        OAuthClient client = new OAuthClient(new TraktHttpClient());
        return client.accessToken(request);
    }

    private String apiKey;
    private String accessToken;
    private boolean isDebug;
    private RestAdapter restAdapter;

    /**
     * Get a new API manager instance.
     *
     * <p> Re-use this instance instead of calling this constructor again.
     */
    public TraktV2() {
    }

    /**
     * Set the trakt API key for this application.
     *
     * <p> Call this before creating a new service.
     *
     * @param apiKey The API key obtained from trakt, currently equal to the OAuth client id.
     * @return This class, to enable the builder pattern.
     */
    public TraktV2 setApiKey(String apiKey) {
        this.apiKey = apiKey;
        restAdapter = null;
        return this;
    }

    /**
     * Sets the OAuth 2.0 access token to be appended to requests.
     *
     * <p> If set, some methods will return user-specific data.
     *
     * <p> Call this before creating a new service.
     *
     * @param accessToken A valid access token, obtained via e.g. {@link #getAccessToken(String, String, String,
     * String)}.
     * @return This class, to enable the builder pattern.
     */
    public TraktV2 setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        restAdapter = null;
        return this;
    }

    /**
     * Set the {@link retrofit.RestAdapter} log level.
     *
     * @param isDebug If true, the log level is set to {@link retrofit.RestAdapter.LogLevel#FULL}. Otherwise {@link
     * retrofit.RestAdapter.LogLevel#NONE}.
     */
    public TraktV2 setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;

        if (restAdapter != null) {
            restAdapter.setLogLevel(isDebug ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
        }

        return this;
    }

    /**
     * Create a new {@link retrofit.RestAdapter.Builder}. Override this to e.g. set your own client or executor.
     *
     * @return A {@link retrofit.RestAdapter.Builder} with no modifications.
     */
    protected RestAdapter.Builder newRestAdapterBuilder() {
        return new RestAdapter.Builder();
    }

    /**
     * Return the current {@link retrofit.RestAdapter} instance. If none exists (first call, API key changed), builds a
     * new one.
     *
     * <p> When building, sets the endpoint, a {@link retrofit.RequestInterceptor} which adds the API key and version
     * headers and sets the log level.
     */
    protected RestAdapter getRestAdapter() {
        if (restAdapter == null) {
            RestAdapter.Builder builder = newRestAdapterBuilder();
            builder.setEndpoint(API_URL);
            builder.setConverter(new GsonConverter(TraktV2Helper.getGsonBuilder().create()));

            // supply the API key and if available OAuth access token
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    if (accessToken != null && accessToken.length() != 0) {
                        request.addHeader(HEADER_AUTHORIZATION, "Bearer" + " " + accessToken);
                    }
                    request.addHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_JSON);
                    request.addHeader(HEADER_TRAKT_API_KEY, apiKey);
                    request.addHeader(HEADER_TRAKT_API_VERSION, HEADER_TRAKT_API_VERSION_2);
                }
            });

            // add custom error handling to intercept OAuth errors
            builder.setErrorHandler(new TraktErrorHandler());

            if (isDebug) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
            }

            restAdapter = builder.build();
        }

        return restAdapter;
    }

    /**
     * By default, the calendar will return all shows or movies for the specified time period. If OAuth is sent, the
     * items returned will be limited to what the user has watched, collected, or added to their watchlist. You'll most
     * likely want to send OAuth to make the calendar more relevant to the user.
     */
    public Calendars calendars() {
        return getRestAdapter().create(Calendars.class);
    }

    /**
     * Checking in is a manual process used by mobile apps. While not as effortless as scrobbling, checkins help fill in
     * the gaps. You might be watching live tv, at a friend's house, or watching a movie in theaters. You can simply
     * checkin from your phone or tablet in those situations.
     */
    public Checkin checkin() {
        return getRestAdapter().create(Checkin.class);
    }

    /**
     * Comments are attached to any movie, show, season, episode, or list and can be shorter shouts or more in depth
     * reviews. Each comment can have replies and can be voted up or down. These votes are used to determine popular
     * comments.
     */
    public Comments comments() {
        return getRestAdapter().create(Comments.class);
    }

    /**
     * One or more genres are attached to all movies and shows. Some API methods allow filtering by genre, so it's good
     * to cache this list in your app.
     */
    public Genres genres() {
        return getRestAdapter().create(Genres.class);
    }

    public Movies movies() {
        return getRestAdapter().create(Movies.class);
    }

    public People people() {
        return getRestAdapter().create(People.class);
    }

    /**
     * Recommendations are based on the watched history for a user and their friends. There are other factors that go
     * into the algorithm as well to further personalize what gets recommended.
     */
    public Recommendations recommendations() {
        return getRestAdapter().create(Recommendations.class);
    }

    /**
     * Searches can use queries or ID lookups. Queries will search fields like the title and description. ID lookups are
     * helpful if you have an external ID and want to get the trakt ID and info. This method will search for movies,
     * shows, episodes, people, users, and lists.
     */
    public Search search() {
        return getRestAdapter().create(Search.class);
    }

    public Shows shows() {
        return getRestAdapter().create(Shows.class);
    }

    public Seasons seasons() {
        return getRestAdapter().create(Seasons.class);
    }

    public Episodes episodes() {
        return getRestAdapter().create(Episodes.class);
    }

    public Sync sync() {
        return getRestAdapter().create(Sync.class);
    }

    public Users users() {
        return getRestAdapter().create(Users.class);
    }

}
