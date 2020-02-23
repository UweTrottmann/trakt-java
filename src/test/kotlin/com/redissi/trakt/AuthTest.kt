package com.redissi.trakt

import com.redissi.trakt.entities.AccessToken
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotBeNullOrEmpty
import org.amshove.kluent.shouldNotContain
import org.amshove.kluent.shouldStartWith
import org.assertj.core.api.Assertions
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.math.BigInteger
import java.security.SecureRandom

/**
 * This test should NOT be run with the regular test suite. It requires a valid, temporary (!) auth code to be set.
 */
class AuthTest : BaseTestCase(), TestResponse {

    override val trakt: Trakt = AuthTest.trakt

    @Test
    fun `get authorization`() {
        val sampleState = BigInteger(130, SecureRandom()).toString(32)
        val authUrl = trakt.buildAuthorizationUrl(sampleState)
        authUrl.shouldNotBeNullOrEmpty()
        authUrl.shouldStartWith(Trakt.getOauth2AuthorizationUrl(trakt.staging))
        // trakt does not support scopes, so don't send one (server sets default scope)
        authUrl.shouldNotContain("scope")
        println("Get an auth code at the following URI: $authUrl")
    }

    @Test
    @Throws(IOException::class)
    fun `get access token`() = runBlocking {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_AUTH_CODE.isEmpty()) {
            print("Skipping get access token test, no valid auth data")
            return@runBlocking
        }
        val response = trakt.exchangeCodeForAccessToken(TEST_AUTH_CODE)
        assertAccessTokenResponse(response)
    }

    @Test
    @Throws(IOException::class)
    fun `refresh access token`() = runBlocking {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_REFRESH_TOKEN.isEmpty()) {
            print("Skipping refresh access token test, no valid auth data")
            return@runBlocking
        }
        trakt.refreshToken = TEST_REFRESH_TOKEN
        val response = trakt.refreshAccessToken(trakt.refreshToken)
        assertAccessTokenResponse(response)
    }

    private fun assertAccessTokenResponse(response: Response<AccessToken>) {
        assertSuccessfulResponse(response, trakt)
        val body = response.body()
        body.shouldNotBeNull()
        body.accessToken.shouldNotBeNullOrEmpty()
        body.refreshToken.shouldNotBeNullOrEmpty()
        println("Retrieved access token: " + body.accessToken)
        println("Retrieved refresh token: " + body.refreshToken)
        println("Retrieved scope: " + body.scope)
        println("Retrieved expires in: " + body.expiresIn + " seconds")
    }

    companion object {
        private val TEST_CLIENT_SECRET = System.getProperty("TRAKT_CLIENT_SECRET") ?: Assertions.fail("No TRAKT_CLIENT_SECRET")
        private val TEST_AUTH_CODE = System.getProperty("TRAKT_AUTH_CODE")
        private val TEST_REFRESH_TOKEN = System.getProperty("TRAKT_REFRESH_TOKEN")
        private const val TEST_REDIRECT_URI = "http://localhost"
        private val trakt: Trakt = TestTrakt(
            TEST_CLIENT_ID,
            TEST_CLIENT_SECRET,
            TEST_REDIRECT_URI
        )
    }
}