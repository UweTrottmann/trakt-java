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

package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.services.Calendars;
import com.uwetrottmann.trakt5.services.Checkin;
import com.uwetrottmann.trakt5.services.Comments;
import com.uwetrottmann.trakt5.services.Episodes;
import com.uwetrottmann.trakt5.services.Genres;
import com.uwetrottmann.trakt5.services.Movies;
import com.uwetrottmann.trakt5.services.People;
import com.uwetrottmann.trakt5.services.Recommendations;
import com.uwetrottmann.trakt5.services.Search;
import com.uwetrottmann.trakt5.services.Seasons;
import com.uwetrottmann.trakt5.services.Shows;
import com.uwetrottmann.trakt5.services.Sync;
import com.uwetrottmann.trakt5.services.Users;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Helper class for easy usage of the trakt v2 API using retrofit.
 */
public class TraktV2 {

    /**
     * trakt API v2 URL.
     */
    public static final String API_HOST = "api-v2launch.trakt.tv";
    public static final String API_URL = "https://" + API_HOST;
    public static final String API_VERSION = "2";

    public static final String SITE_URL = "https://trakt.tv";
    public static final String OAUTH2_AUTHORIZATION_URL = SITE_URL + "/oauth/authorize";
    public static final String OAUTH2_TOKEN_URL = SITE_URL + "/oauth/token";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_TRAKT_API_VERSION = "trakt-api-version";
    public static final String HEADER_TRAKT_API_KEY = "trakt-api-key";

    /**
     * Build an OAuth 2.0 authorization request to obtain an authorization code.
     *
     * <p>Send the user to the location URI of this request. Once the user authorized your app, the server will redirect
     * to {@code redirectUri} with the authorization code and the sent state in the query parameter {@code code}.
     *
     * <p>Ensure the state matches, then supply the authorization code to {@link #getAccessToken} to get an access
     * token.
     *
     * @param clientId The OAuth client id obtained from trakt.
     * @param redirectUri The URI as configured on trakt to redirect to with appended auth code and state query
     * parameters.
     * @param state State variable to prevent request forgery attacks.
     */
    public static OAuthClientRequest getAuthorizationRequest(String clientId, String redirectUri,
            String state) throws OAuthSystemException {
        return OAuthClientRequest
                .authorizationLocation(OAUTH2_AUTHORIZATION_URL)
                .setResponseType(ResponseType.CODE.toString())
                .setClientId(clientId)
                .setRedirectURI(redirectUri)
                .setState(state)
                .buildQueryMessage();
    }

    /**
     * Build an OAuth 2.0 access token request. The grant is based on an authorization code that was just obtained from
     * an authorization request.
     *
     * @param clientId The OAuth client id obtained from trakt.
     * @param clientSecret The OAuth client secret obtained from trakt.
     * @param redirectUri The redirect URI as configured on trakt.
     * @param authCode A just obtained authorization code.
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
     * Request an access token from trakt. Builds the request with {@link #getAccessTokenRequest} and executes it, then
     * returns the response which includes the access token.
     *
     * <p>Supply the received access token to {@link #accessToken(String)} and store the refresh token to later refresh
     * the access token once it has expired.
     *
     * <p>On failure re-authorization of your app is required (see {@link #getAuthorizationRequest}).
     *
     * @param clientId The OAuth client id obtained from trakt.
     * @param clientSecret The OAuth client secret obtained from trakt.
     * @param redirectUri The redirect URI as configured on trakt.
     * @param authCode A valid authorization code (see {@link #getAuthorizationRequest(String, String, String)}).
     */
    public static OAuthAccessTokenResponse getAccessToken(String clientId, String clientSecret, String redirectUri,
            String authCode) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request = getAccessTokenRequest(clientId, clientSecret, redirectUri, authCode);

        OAuthClient client = new OAuthClient(new TraktHttpClient());
        return client.accessToken(request);
    }

    /**
     * Build an OAuth 2.0 access token request. The grant is based on the refresh token obtained with the last access
     * token request response.
     *
     * @param clientId The OAuth client id obtained from trakt.
     * @param clientSecret The OAuth client secret obtained from trakt.
     * @param redirectUri The redirect URI as configured on trakt.
     * @param refreshToken The refresh token obtained with the last access token request response.
     */
    public static OAuthClientRequest getAccessTokenRefreshRequest(String clientId, String clientSecret,
            String redirectUri, String refreshToken) throws OAuthSystemException {
        return OAuthClientRequest
                .tokenLocation(OAUTH2_TOKEN_URL)
                .setGrantType(GrantType.REFRESH_TOKEN)
                .setRefreshToken(refreshToken)
                .setRedirectURI(redirectUri)
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .buildQueryMessage();
    }

    /**
     * Request to refresh an expired access token for trakt. If your app is still authorized, returns a response which
     * includes a new access token.
     *
     * <p>Supply the received access token to {@link #accessToken(String)} and store the refresh token to later refresh
     * the access token once it has expired.
     *
     * <p>On failure re-authorization of your app is required (see {@link #getAuthorizationRequest}).
     *
     * @param clientId The OAuth client id obtained from trakt.
     * @param clientSecret The OAuth client secret obtained from trakt.
     * @param redirectUri The redirect URI as configured on trakt.
     * @param refreshToken The refresh token obtained with the last access token request response.
     */
    public static OAuthAccessTokenResponse refreshAccessToken(String clientId, String clientSecret, String redirectUri,
            String refreshToken) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request = getAccessTokenRefreshRequest(clientId, clientSecret, redirectUri, refreshToken);

        OAuthClient client = new OAuthClient(new TraktHttpClient());
        return client.accessToken(request);
    }

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HttpLoggingInterceptor logging;

    private boolean enableDebugLogging;

    private String apiKey;
    private String accessToken;

    /**
     * Get a new API manager instance.
     *
     * @param apiKey The API key obtained from trakt, currently equal to the OAuth client id.
     */
    public TraktV2(String apiKey) {
        this.apiKey = apiKey;
    }

    public String apiKey() {
        return apiKey;
    }

    public void apiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String accessToken() {
        return accessToken;
    }

    /**
     * Sets the OAuth 2.0 access token to be appended to requests.
     *
     * <p> If set, some methods will return user-specific data.
     *
     * @param accessToken A valid access token, obtained via e.g. {@link #getAccessToken(String, String, String,
     * String)}.
     */
    public TraktV2 accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Enable debug log output.
     *
     * @param enable If true, the log level is set to {@link HttpLoggingInterceptor.Level#BODY}. Otherwise {@link
     * HttpLoggingInterceptor.Level#NONE}.
     */
    public TraktV2 enableDebugLogging(boolean enable) {
        this.enableDebugLogging = enable;
        if (logging != null) {
            logging.setLevel(enable ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        }
        return this;
    }

    /**
     * Creates a {@link Retrofit.Builder} that sets the base URL, adds a Gson converter and sets {@link #okHttpClient()}
     * as its client.
     *
     * @see #okHttpClient()
     */
    protected Retrofit.Builder retrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(TraktV2Helper.getGsonBuilder().create()))
                .client(okHttpClient());
    }

    /**
     * Returns the default OkHttp client instance. It is strongly recommended to override this and use your app
     * instance.
     *
     * @see #setOkHttpClientDefaults(OkHttpClient.Builder)
     */
    protected synchronized OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            setOkHttpClientDefaults(builder);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    /**
     * Adds a network interceptor to add version and auth headers and a regular interceptor to log requests.
     */
    protected void setOkHttpClientDefaults(OkHttpClient.Builder builder) {
        builder.addNetworkInterceptor(new TraktV2Interceptor(this));
        if (enableDebugLogging) {
            logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
    }

    /**
     * Return the {@link Retrofit} instance. If called for the first time builds the instance, so if desired make sure
     * to call {@link #enableDebugLogging(boolean)} before.
     */
    protected Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = retrofitBuilder().build();
        }
        return retrofit;
    }

    /**
     * By default, the calendar will return all shows or movies for the specified time period. If OAuth is sent, the
     * items returned will be limited to what the user has watched, collected, or added to their watchlist. You'll most
     * likely want to send OAuth to make the calendar more relevant to the user.
     */
    public Calendars calendars() {
        return retrofit().create(Calendars.class);
    }

    /**
     * Checking in is a manual process used by mobile apps. While not as effortless as scrobbling, checkins help fill in
     * the gaps. You might be watching live tv, at a friend's house, or watching a movie in theaters. You can simply
     * checkin from your phone or tablet in those situations.
     */
    public Checkin checkin() {
        return retrofit().create(Checkin.class);
    }

    /**
     * Comments are attached to any movie, show, season, episode, or list and can be shorter shouts or more in depth
     * reviews. Each comment can have replies and can be voted up or down. These votes are used to determine popular
     * comments.
     */
    public Comments comments() {
        return retrofit().create(Comments.class);
    }

    /**
     * One or more genres are attached to all movies and shows. Some API methods allow filtering by genre, so it's good
     * to cache this list in your app.
     */
    public Genres genres() {
        return retrofit().create(Genres.class);
    }

    public Movies movies() {
        return retrofit().create(Movies.class);
    }

    public People people() {
        return retrofit().create(People.class);
    }

    /**
     * Recommendations are based on the watched history for a user and their friends. There are other factors that go
     * into the algorithm as well to further personalize what gets recommended.
     */
    public Recommendations recommendations() {
        return retrofit().create(Recommendations.class);
    }

    /**
     * Searches can use queries or ID lookups. Queries will search fields like the title and description. ID lookups are
     * helpful if you have an external ID and want to get the trakt ID and info. This method will search for movies,
     * shows, episodes, people, users, and lists.
     */
    public Search search() {
        return retrofit().create(Search.class);
    }

    public Shows shows() {
        return retrofit().create(Shows.class);
    }

    public Seasons seasons() {
        return retrofit().create(Seasons.class);
    }

    public Episodes episodes() {
        return retrofit().create(Episodes.class);
    }

    public Sync sync() {
        return retrofit().create(Sync.class);
    }

    public Users users() {
        return retrofit().create(Users.class);
    }

}
