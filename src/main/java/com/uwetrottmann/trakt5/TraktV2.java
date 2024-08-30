/*
 * Copyright 2014-2024 Uwe Trottmann
 * Copyright 2019 Marc de Courville
 * Copyright 2020 Sam Malone
 * Copyright 2020 srggsf
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
import com.uwetrottmann.trakt5.entities.AccessTokenRefreshRequest;
import com.uwetrottmann.trakt5.entities.AccessTokenRequest;
import com.uwetrottmann.trakt5.entities.CheckinError;
import com.uwetrottmann.trakt5.entities.ClientId;
import com.uwetrottmann.trakt5.entities.DeviceCode;
import com.uwetrottmann.trakt5.entities.DeviceCodeAccessTokenRequest;
import com.uwetrottmann.trakt5.entities.TraktError;
import com.uwetrottmann.trakt5.entities.TraktOAuthError;
import com.uwetrottmann.trakt5.services.Authentication;
import com.uwetrottmann.trakt5.services.Calendars;
import com.uwetrottmann.trakt5.services.Checkin;
import com.uwetrottmann.trakt5.services.Comments;
import com.uwetrottmann.trakt5.services.Episodes;
import com.uwetrottmann.trakt5.services.Genres;
import com.uwetrottmann.trakt5.services.Movies;
import com.uwetrottmann.trakt5.services.Notes;
import com.uwetrottmann.trakt5.services.People;
import com.uwetrottmann.trakt5.services.Recommendations;
import com.uwetrottmann.trakt5.services.Scrobble;
import com.uwetrottmann.trakt5.services.Search;
import com.uwetrottmann.trakt5.services.Seasons;
import com.uwetrottmann.trakt5.services.Shows;
import com.uwetrottmann.trakt5.services.Sync;
import com.uwetrottmann.trakt5.services.Users;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;

/**
 * Helper class for easy usage of the Trakt v2 API using retrofit.
 */
public class TraktV2 {

    /**
     * Trakt API v2 URL.
     */
    public static final String API_HOST = "api.trakt.tv";
    public static final String API_STAGING_HOST = "api-staging.trakt.tv";
    public static final String API_URL = "https://" + API_HOST + "/";
    public static final String API_STAGING_URL = "https://" + API_STAGING_HOST + "/";
    public static final String API_VERSION = "2";

    public static final String SITE_URL = "https://trakt.tv";
    public static final String OAUTH2_AUTHORIZATION_URL = SITE_URL + "/oauth/authorize";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_TRAKT_API_VERSION = "trakt-api-version";
    public static final String HEADER_TRAKT_API_KEY = "trakt-api-key";

    @Nullable private OkHttpClient okHttpClient;
    @Nullable private Retrofit retrofit;

    private String apiKey;
    @Nullable private String clientSecret;
    @Nullable private String redirectUri;
    @Nullable private String accessToken;
    @Nullable private String refreshToken;
    private final HttpUrl apiBaseUrl;

    /**
     * Get a new API manager instance.
     *
     * @param apiKey The API key obtained from Trakt, currently equal to the OAuth client id.
     * @see #TraktV2(java.lang.String, boolean)
     */
    public TraktV2(String apiKey) {
        this(apiKey, false);
    }

    /**
     * Get a new API manager instance capable of calling OAuth2 protected endpoints.
     *
     * @param apiKey The API key obtained from Trakt, currently equal to the OAuth client id.
     * @param clientSecret The client secret obtained from Trakt.
     * @param redirectUri The redirect URI to use for OAuth2 token requests.
     * @see #TraktV2(java.lang.String, java.lang.String, java.lang.String, boolean)
     */
    public TraktV2(String apiKey, String clientSecret, String redirectUri) {
        this(apiKey, clientSecret, redirectUri, false);
    }

    /**
     * Get a new API manager instance.
     *
     * @param apiKey The API key obtained from Trakt, currently equal to the OAuth client id.
     * @param staging Use {@link TraktV2#API_STAGING_URL} if {true} or {@link TraktV2#API_URL} otherwise.
     * @see #TraktV2(java.lang.String)
     */
    public TraktV2(String apiKey, boolean staging){
        this.apiKey = apiKey;
        apiBaseUrl = HttpUrl.get(staging ? API_STAGING_URL : API_URL);
    }

    /**
     * Get a new API manager instance capable of calling OAuth2 protected endpoints.
     *
     * @param apiKey The API key obtained from Trakt, currently equal to the OAuth client id.
     * @param clientSecret The client secret obtained from Trakt.
     * @param redirectUri The redirect URI to use for OAuth2 token requests.
     * @param staging Use {@link TraktV2#API_STAGING_URL} if {true} or {@link TraktV2#API_URL} otherwise.
     * @see #TraktV2(java.lang.String, java.lang.String, java.lang.String)
     */
    public TraktV2(String apiKey, String clientSecret, String redirectUri, boolean staging) {
        this(apiKey, staging);
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String apiHost(){
        return apiBaseUrl.host();
    }

    public String apiKey() {
        return apiKey;
    }

    public TraktV2 apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    @Nullable
    public String accessToken() {
        return accessToken;
    }

    /**
     * Sets the OAuth 2.0 access token to be appended to requests.
     *
     * <p> If set, some methods will return user-specific data.
     *
     * @param accessToken A valid access token, obtained via e.g. {@link #exchangeCodeForAccessToken(String)}.
     */
    public TraktV2 accessToken(@Nullable String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Nullable
    public String refreshToken() {
        return refreshToken;
    }

    /**
     * Sets the OAuth 2.0 refresh token to be used, in case the current access token has expired, to get a new access
     * token.
     */
    public TraktV2 refreshToken(@Nullable String refreshToken) {
        this.refreshToken = refreshToken;
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
                .baseUrl(apiBaseUrl)
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
     * Adds {@link TraktV2Interceptor} as an application interceptor and {@link TraktV2Authenticator} as an authenticator.
     */
    protected void setOkHttpClientDefaults(OkHttpClient.Builder builder) {
        builder.addInterceptor(new TraktV2Interceptor(this));
        builder.authenticator(new TraktV2Authenticator(this));
    }

    /**
     * Return the {@link Retrofit} instance. If called for the first time builds the instance.
     */
    protected Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = retrofitBuilder().build();
        }
        return retrofit;
    }

    /**
     * Build an OAuth 2.0 authorization URL to obtain an authorization code.
     *
     * <p>Send the user to the URL. Once the user authorized your app, the server will redirect to {@code redirectUri}
     * with the authorization code and the sent state in the query parameter {@code code}.
     *
     * <p>Ensure the state matches, then supply the authorization code to {@link #exchangeCodeForAccessToken} to get an
     * access token.
     *
     * @param state State variable to prevent request forgery attacks.
     */
    public String buildAuthorizationUrl(String state) {
        if (redirectUri == null) {
            throw new IllegalStateException("redirectUri not provided");
        }

        @SuppressWarnings("StringBufferReplaceableByString")
        StringBuilder authUrl = new StringBuilder(OAUTH2_AUTHORIZATION_URL);
        authUrl.append("?").append("response_type=code");
        authUrl.append("&").append("redirect_uri=").append(urlEncode(redirectUri));
        authUrl.append("&").append("state=").append(urlEncode(state));
        authUrl.append("&").append("client_id=").append(urlEncode(apiKey()));
        return authUrl.toString();
    }

    private String urlEncode(String content) {
        try {
            // can not use java.nio.charset.StandardCharsets as on Android only available since API 19
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * Request a code to start the device authentication process from Trakt.
     *
     * The {@code device_code} and {@code interval} will be used later to poll for the {@code access_token}.
     * The {@code user_code} and {@code verification_url} should be presented to the user.
     */
    public Response<DeviceCode> generateDeviceCode() throws IOException {
        ClientId clientId = new ClientId();
        clientId.client_id = apiKey;
        return authentication().generateDeviceCode(clientId).execute();
    }

    /**
     * Request an access token from Trakt using device authentication.
     *
     * <p>Supply the received access token to {@link #accessToken(String)} and store the refresh token to later refresh
     * the access token once it has expired.
     *
     * <p>On failure re-authorization of your app is required (see {@link #generateDeviceCode()}).
     *
     * @param deviceCode A valid device code (see {@link #generateDeviceCode()}).
     */
    public Response<AccessToken> exchangeDeviceCodeForAccessToken(String deviceCode) throws IOException {
        if (clientSecret == null) {
            throw new IllegalStateException("clientSecret not provided");
        }

        DeviceCodeAccessTokenRequest request = new DeviceCodeAccessTokenRequest();
        request.client_id = apiKey;
        request.client_secret = clientSecret;
        request.code = deviceCode;
        return authentication().exchangeDeviceCodeForAccessToken(request).execute();
    }

    /**
     * Request an access token from Trakt.
     *
     * <p>Supply the received access token to {@link #accessToken(String)} and store the refresh token to later refresh
     * the access token once it has expired.
     *
     * <p>On failure re-authorization of your app is required (see {@link #buildAuthorizationUrl}).
     *
     * @param authCode A valid authorization code (see {@link #buildAuthorizationUrl(String)}).
     */
    public Response<AccessToken> exchangeCodeForAccessToken(String authCode) throws IOException {
        if (clientSecret == null) {
            throw new IllegalStateException("clientSecret not provided");
        }
        if (redirectUri == null) {
            throw new IllegalStateException("redirectUri not provided");
        }

        return authentication().exchangeCodeForAccessToken(
                new AccessTokenRequest(
                        authCode,
                        apiKey(),
                        clientSecret,
                        redirectUri)
        ).execute();
    }

    /**
     * Request to refresh an expired access token for Trakt. If your app is still authorized, returns a response which
     * includes a new access token.
     *
     * <p>Supply the received access token to {@link #accessToken(String)} and store the refresh token to later refresh
     * the access token once it has expired.
     *
     * <p>On failure re-authorization of your app is required (see {@link #buildAuthorizationUrl}).
     */
    public Response<AccessToken> refreshAccessToken(String refreshToken) throws IOException {
        if (clientSecret == null) {
            throw new IllegalStateException("clientSecret not provided");
        }
        if (redirectUri == null) {
            throw new IllegalStateException("redirectUri not provided");
        }

        return authentication().refreshAccessToken(
                new AccessTokenRefreshRequest(
                        refreshToken,
                        apiKey(),
                        clientSecret,
                        redirectUri
                )
        ).execute();
    }

    /**
     * If the response code is 409 tries to convert the body into a {@link CheckinError}.
     */
    @Nullable
    public CheckinError checkForCheckinError(Response<?> response) {
        if (response.code() != 409) {
            return null; // only code 409 can be a check-in error
        }
        Converter<ResponseBody, CheckinError> errorConverter =
                retrofit().responseBodyConverter(CheckinError.class, new Annotation[0]);
        try {
            //noinspection ConstantConditions never null if unsuccessful
            return errorConverter.convert(response.errorBody());
        } catch (IOException e) {
            return new CheckinError(); // null values
        }
    }

    /**
     * If the response is not successful, tries to parse the error body into a {@link TraktError}.
     */
    @Nullable
    public TraktError checkForTraktError(Response<?> response) {
        if (response.isSuccessful()) {
            return null;
        }
        Converter<ResponseBody, TraktError> errorConverter =
                retrofit().responseBodyConverter(TraktError.class, new Annotation[0]);
        try {
            //noinspection ConstantConditions never null if unsuccessful
            return errorConverter.convert(response.errorBody());
        } catch (IOException ignored) {
            return new TraktError(); // null values
        }
    }

    /**
     * If the {@link Authentication} response is not successful,
     * tries to parse the error body into a {@link TraktOAuthError}.
     */
    @Nullable
    public TraktOAuthError checkForTraktOAuthError(Response<?> response) {
        if (response.isSuccessful()) {
            return null;
        }
        Converter<ResponseBody, TraktOAuthError> errorConverter = retrofit()
                .responseBodyConverter(TraktOAuthError.class, new Annotation[0]);

        if (response.errorBody() != null) {
            try {
                return errorConverter.convert(response.errorBody());
            } catch (IOException ignored) {
            }
        }
        return new TraktOAuthError(); // null values
    }

    public Authentication authentication() {
        return retrofit().create(Authentication.class);
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

    public Notes notes() {
        return retrofit().create(Notes.class);
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
     * helpful if you have an external ID and want to get the Trakt ID and info. This method will search for movies,
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

    public Scrobble scrobble() { return retrofit().create(Scrobble.class); }

    public Users users() {
        return retrofit().create(Users.class);
    }

}
