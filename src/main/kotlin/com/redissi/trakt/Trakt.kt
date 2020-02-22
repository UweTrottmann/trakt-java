package com.redissi.trakt

import com.uwetrottmann.trakt5.entities.AccessToken
import com.uwetrottmann.trakt5.entities.CheckinError
import com.uwetrottmann.trakt5.entities.TraktError
import com.redissi.trakt.services.Episodes
import com.redissi.trakt.services.Movies
import com.redissi.trakt.services.Shows
import com.uwetrottmann.trakt5.services.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


/**
 * Helper class for easy usage of the trakt v2 API using retrofit.
 */
@Suppress("unused")
open class Trakt(
        var apiKey: String,
        val clientSecret: String? = null,
        val redirectUri: String? = null,
        val staging: Boolean = false
) {

    /**
     * Returns the default OkHttp client instance. It is strongly recommended to override this and use your app
     * instance.
     *
     * @see [setOkHttpClientDefaults]
     */
    protected open val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
        setOkHttpClientDefaults(builder)
        builder.build()
    }

    /**
     * Return the [Retrofit] instance. If called for the first time builds the instance.
     */
    protected val retrofit: Retrofit by lazy {
        retrofitBuilder().build()
    }

    var accessToken: String? = null
    var refreshToken: String? = null


    fun apiKey(apiKey: String): Trakt {
        this.apiKey = apiKey
        return this
    }

    /**
     * Sets the OAuth 2.0 access token to be appended to requests.
     *
     *
     *  If set, some methods will return user-specific data.
     *
     * @param accessToken A valid access token, obtained via e.g. [exchangeCodeForAccessToken].
     */
    fun accessToken(accessToken: String?): Trakt {
        this.accessToken = accessToken
        return this
    }

    /**
     * Sets the OAuth 2.0 refresh token to be used, in case the current access token has expired, to get a new access
     * token.
     */
    fun refreshToken(refreshToken: String?): Trakt {
        this.refreshToken = refreshToken
        return this
    }

    /**
     * Creates a [Retrofit.Builder] that sets the base URL, adds a Gson converter and sets [.okHttpClient]
     * as its client.
     *
     * @see [okHttpClient]
     */
    protected open fun retrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(getApiUrl(staging))
                .addConverterFactory(MoshiConverterFactory.create(TraktHelper.moshiBuilder.build()))
                .addConverterFactory(GsonConverterFactory.create(TraktHelper.gsonBuilder.create()))
                .client(okHttpClient)
    }

    /**
     * Adds a network interceptor to add version and auth headers and a regular interceptor to log requests.
     */
    protected open fun setOkHttpClientDefaults(builder: OkHttpClient.Builder) {
        builder.addNetworkInterceptor(TraktInterceptor(this))
        builder.authenticator(TraktAuthenticator(this))
    }

    /**
     * Build an OAuth 2.0 authorization URL to obtain an authorization code.
     *
     *
     * Send the user to the URL. Once the user authorized your app, the server will redirect to `redirectUri`
     * with the authorization code and the sent state in the query parameter `code`.
     *
     *
     * Ensure the state matches, then supply the authorization code to [exchangeCodeForAccessToken] to get an
     * access token.
     *
     * @param state State variable to prevent request forgery attacks.
     */
    fun buildAuthorizationUrl(state: String): String {
        checkNotNull(redirectUri) { "redirectUri not provided" }
        val authUrl = StringBuilder(OAUTH2_AUTHORIZATION_URL)
        authUrl.append("?").append("response_type=code")
        authUrl.append("&").append("redirect_uri=").append(urlEncode(redirectUri))
        authUrl.append("&").append("state=").append(urlEncode(state))
        authUrl.append("&").append("client_id=").append(urlEncode(apiKey))
        return authUrl.toString()
    }

    private fun urlEncode(content: String): String {
        return try { // can not use java.nio.charset.StandardCharsets as on Android only available since API 19
            URLEncoder.encode(content, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw UnsupportedOperationException(e)
        }
    }

    /**
     * Request an access token from trakt.
     *
     *
     * Supply the received access token to [.accessToken] and store the refresh token to later refresh
     * the access token once it has expired.
     *
     *
     * On failure re-authorization of your app is required (see [.buildAuthorizationUrl]).
     *
     * @param authCode A valid authorization code (see [.buildAuthorizationUrl]).
     */
    @Throws(IOException::class)
    fun exchangeCodeForAccessToken(authCode: String?): Response<AccessToken> {
        checkNotNull(clientSecret) { "clientSecret not provided" }
        checkNotNull(redirectUri) { "redirectUri not provided" }
        return authentication().exchangeCodeForAccessToken(
                "authorization_code",
                authCode,
                apiKey,
                clientSecret,
                redirectUri
        ).execute()
    }

    /**
     * Request to refresh an expired access token for trakt. If your app is still authorized, returns a response which
     * includes a new access token.
     *
     *
     * Supply the received access token to [accessToken] and store the refresh token to later refresh
     * the access token once it has expired.
     *
     *
     * On failure re-authorization of your app is required (see [buildAuthorizationUrl]).
     */
    @Throws(IOException::class)
    fun refreshAccessToken(refreshToken: String?): Response<AccessToken> {
        checkNotNull(clientSecret) { "clientSecret not provided" }
        checkNotNull(redirectUri) { "redirectUri not provided" }
        return authentication().refreshAccessToken(
                "refresh_token",
                refreshToken,
                apiKey,
                clientSecret,
                redirectUri
        ).execute()
    }

    /**
     * If the response code is 409 tries to convert the body into a [CheckinError].
     */
    fun checkForCheckinError(response: Response<*>): CheckinError? {
        if (response.code() != 409) {
            return null // only code 409 can be a check-in error
        }
        val errorConverter = retrofit.responseBodyConverter<CheckinError>(CheckinError::class.java, arrayOfNulls(0))
        return try {
            val body = requireNotNull(response.errorBody())
            errorConverter.convert(body)
        } catch (e: IOException) {
            CheckinError() // null values
        }
    }

    /**
     * If the response is not successful, tries to parse the error body into a [TraktError].
     */
    fun checkForTraktError(response: Response<*>): TraktError? {
        if (response.isSuccessful) {
            return null
        }
        val errorConverter = retrofit.responseBodyConverter<TraktError>(TraktError::class.java, arrayOfNulls(0))
        return try {
            val body = requireNotNull(response.errorBody())
            errorConverter.convert(body)
        } catch (ignored: IOException) {
            TraktError() // null values
        }
    }

    fun authentication(): Authentication {
        return retrofit.create(Authentication::class.java)
    }

    /**
     * By default, the calendar will return all shows or movies for the specified time period. If OAuth is sent, the
     * items returned will be limited to what the user has watched, collected, or added to their watchlist. You'll most
     * likely want to send OAuth to make the calendar more relevant to the user.
     */
    fun calendars(): Calendars {
        return retrofit.create(Calendars::class.java)
    }

    /**
     * Checking in is a manual process used by mobile apps. While not as effortless as scrobbling, checkins help fill in
     * the gaps. You might be watching live tv, at a friend's house, or watching a movie in theaters. You can simply
     * checkin from your phone or tablet in those situations.
     */
    fun checkin(): Checkin {
        return retrofit.create(Checkin::class.java)
    }

    /**
     * Comments are attached to any movie, show, season, episode, or list and can be shorter shouts or more in depth
     * reviews. Each comment can have replies and can be voted up or down. These votes are used to determine popular
     * comments.
     */
    fun comments(): Comments {
        return retrofit.create(Comments::class.java)
    }

    /**
     * One or more genres are attached to all movies and shows. Some API methods allow filtering by genre, so it's good
     * to cache this list in your app.
     */
    fun genres(): Genres {
        return retrofit.create(Genres::class.java)
    }

    fun movies(): Movies {
        return retrofit.create(Movies::class.java)
    }

    fun people(): People {
        return retrofit.create(People::class.java)
    }

    /**
     * Recommendations are based on the watched history for a user and their friends. There are other factors that go
     * into the algorithm as well to further personalize what gets recommended.
     */
    fun recommendations(): Recommendations {
        return retrofit.create(Recommendations::class.java)
    }

    /**
     * Searches can use queries or ID lookups. Queries will search fields like the title and description. ID lookups are
     * helpful if you have an external ID and want to get the trakt ID and info. This method will search for movies,
     * shows, episodes, people, users, and lists.
     */
    fun search(): Search {
        return retrofit.create(Search::class.java)
    }

    fun shows(): Shows {
        return retrofit.create(Shows::class.java)
    }

    fun seasons(): Seasons {
        return retrofit.create(Seasons::class.java)
    }

    fun episodes(): Episodes {
        return retrofit.create(Episodes::class.java)
    }

    fun sync(): Sync {
        return retrofit.create(Sync::class.java)
    }

    fun scrobble(): Scrobble {
        return retrofit.create(Scrobble::class.java)
    }

    fun users(): Users {
        return retrofit.create(Users::class.java)
    }

    companion object {
        fun getApiHost(staging: Boolean = false): String {
            return if (staging) "api-staging.trakt.tv" else "api.trakt.tv"
        }

        fun getApiUrl(staging: Boolean = false): String {
            return "https://${getApiHost(staging)}/"
        }

        const val API_VERSION = "2"
        const val SITE_URL = "https://trakt.tv"
        const val OAUTH2_AUTHORIZATION_URL = "$SITE_URL/oauth/authorize"
        const val OAUTH2_TOKEN_URL = "$SITE_URL/oauth/token"
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_JSON = "application/json"
        const val HEADER_TRAKT_API_VERSION = "trakt-api-version"
        const val HEADER_TRAKT_API_KEY = "trakt-api-key"
    }
}
