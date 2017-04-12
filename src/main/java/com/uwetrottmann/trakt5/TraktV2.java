package com.uwetrottmann.trakt5;

import com.uwetrottmann.trakt5.entities.AccessToken;
import com.uwetrottmann.trakt5.entities.CheckinError;
import com.uwetrottmann.trakt5.services.Authentication;
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
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;

/**
 * Helper class for easy usage of the trakt v2 API using retrofit.
 */
public class TraktV2 {

    /**
     * trakt API v2 URL.
     */
    public static final String API_HOST = "api.trakt.tv";
    public static final String API_URL = "https://" + API_HOST + "/";
    public static final String API_VERSION = "2";

    public static final String SITE_URL = "https://trakt.tv";
    public static final String OAUTH2_AUTHORIZATION_URL = SITE_URL + "/oauth/authorize";
    public static final String OAUTH2_TOKEN_URL = SITE_URL + "/oauth/token";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_TRAKT_API_VERSION = "trakt-api-version";
    public static final String HEADER_TRAKT_API_KEY = "trakt-api-key";

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private String apiKey;
    private String clientSecret;
    private String redirectUri;
    private String accessToken;
    private String refreshToken;

    /**
     * Get a new API manager instance.
     *
     * @param apiKey The API key obtained from trakt, currently equal to the OAuth client id.
     */
    public TraktV2(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Get a new API manager instance capable of calling OAuth2 protected endpoints.
     *
     * @param apiKey The API key obtained from trakt, currently equal to the OAuth client id.
     * @param clientSecret The client secret obtained from trakt.
     * @param redirectUri The redirect URI to use for OAuth2 token requests.
     */
    public TraktV2(String apiKey, String clientSecret, String redirectUri) {
        this.apiKey = apiKey;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
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
     * @param accessToken A valid access token, obtained via e.g. {@link #exchangeCodeForAccessToken(String)}.
     */
    public TraktV2 accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String refreshToken() {
        return refreshToken;
    }

    /**
     * Sets the OAuth 2.0 refresh token to be used, in case the current access token has expired, to get a new access
     * token.
     */
    public TraktV2 refreshToken(String refreshToken) {
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
     * <p>Send the user to the URL. Once the user authorized your app, the server will redirect
     * to {@code redirectUri} with the authorization code and the sent state in the query parameter {@code code}.
     *
     * <p>Ensure the state matches, then supply the authorization code to {@link #exchangeCodeForAccessToken} to get an
     * access token.
     *
     * @param state State variable to prevent request forgery attacks.
     */
    public String buildAuthorizationUrl(String state) {
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
     * Request an access token from trakt.
     *
     * <p>Supply the received access token to {@link #accessToken(String)} and store the refresh token to later refresh
     * the access token once it has expired.
     *
     * <p>On failure re-authorization of your app is required (see {@link #buildAuthorizationUrl}).
     *
     * @param authCode A valid authorization code (see {@link #buildAuthorizationUrl(String)}).
     */
    public Response<AccessToken> exchangeCodeForAccessToken(String authCode) throws IOException {
        return authentication().exchangeCodeForAccessToken(
                "authorization_code",
                authCode,
                apiKey(),
                clientSecret,
                redirectUri
        ).execute();
    }

    /**
     * Request to refresh an expired access token for trakt. If your app is still authorized, returns a response which
     * includes a new access token.
     *
     * <p>Supply the received access token to {@link #accessToken(String)} and store the refresh token to later refresh
     * the access token once it has expired.
     *
     * <p>On failure re-authorization of your app is required (see {@link #buildAuthorizationUrl}).
     */
    public Response<AccessToken> refreshAccessToken() throws IOException {
        return authentication().refreshAccessToken(
                "refresh_token",
                refreshToken(),
                apiKey(),
                clientSecret,
                redirectUri
        ).execute();
    }

    /**
     * If the response code is 409 tries to convert the body into a {@link CheckinError}, otherwise returns {@code
     * null}.
     *
     * @throws IOException If converting the error to {@link CheckinError} failed.
     */
    public CheckinError checkForCheckinError(Response response) throws IOException {
        if (response.code() != 409) {
            return null; // only code 409 can be a check-in error
        }
        Converter<ResponseBody, CheckinError> errorConverter =
                retrofit.responseBodyConverter(CheckinError.class, new Annotation[0]);
        return errorConverter.convert(response.errorBody());
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
