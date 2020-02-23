package com.redissi.trakt

import kotlinx.coroutines.*
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TraktAuthenticator(val trakt: Trakt) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return handleAuthenticate(response, trakt)
    }

    /**
     * If not doing a trakt [Trakt.getApiUrl] request tries to refresh the access token with the refresh token.
     *
     * @param response The response passed to [.authenticate].
     * @param trakt The [Trakt] instance to get the API key from and to set the updated JSON web token on.
     * @return A request with updated authorization header or null if no auth is possible.
     */
    private fun handleAuthenticate(response: Response, trakt: Trakt): Request? = runBlocking {
        if (Trakt.getApiHost(trakt.staging) != response.request.url.host) {
            return@runBlocking null // not a trakt API endpoint (possibly trakt OAuth or other API), give up.
        }
        if (responseCount(response) >= 2) {
            return@runBlocking null // failed 2 times, give up.
        }
        val refreshToken = trakt.refreshToken
        if (refreshToken == null || refreshToken.isEmpty()) {
            return@runBlocking null // have no refresh token, give up.
        }
        // try to refresh the access token with the refresh token
        val refreshResponse = trakt.refreshAccessToken(refreshToken)
        val body = refreshResponse.body()

        if (!refreshResponse.isSuccessful || body == null) {
            return@runBlocking null // failed to retrieve a token, give up.
        }
        // store the new tokens
        val accessToken = body.accessToken
        trakt.accessToken(accessToken)
        trakt.refreshToken(body.refreshToken)
        // retry request
        return@runBlocking response.request.newBuilder()
            .header(Trakt.HEADER_AUTHORIZATION, "Bearer $accessToken")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var res = response
        var result = 1
        var priorResponse = res.priorResponse
        while (priorResponse != null) {
            res = priorResponse
            priorResponse = res.priorResponse
            result++
        }
        return result
    }

}