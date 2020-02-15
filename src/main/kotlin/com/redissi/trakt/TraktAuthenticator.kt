package com.redissi.trakt

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException

class TraktAuthenticator(val trakt: Trakt) : Authenticator {

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        return handleAuthenticate(response, trakt)
    }

    companion object {
        /**
         * If not doing a trakt [Trakt.API_URL] request tries to refresh the access token with the refresh token.
         *
         * @param response The response passed to [.authenticate].
         * @param trakt The [Trakt] instance to get the API key from and to set the updated JSON web token on.
         * @return A request with updated authorization header or null if no auth is possible.
         */
        @Throws(IOException::class)
        fun handleAuthenticate(response: Response, trakt: Trakt): Request? {
            if (Trakt.getApiHost(trakt.staging) != response.request.url.host) {
                return null // not a trakt API endpoint (possibly trakt OAuth or other API), give up.
            }
            if (responseCount(response) >= 2) {
                return null // failed 2 times, give up.
            }
            val refreshToken = trakt.refreshToken
            if (refreshToken == null || refreshToken.isEmpty()) {
                return null // have no refresh token, give up.
            }
            // try to refresh the access token with the refresh token
            val refreshResponse = trakt.refreshAccessToken(refreshToken)
            val body = refreshResponse.body()
            if (!refreshResponse.isSuccessful || body == null) {
                return null // failed to retrieve a token, give up.
            }
            // store the new tokens
            val accessToken = body.access_token
            trakt.accessToken(accessToken)
            trakt.refreshToken(body.refresh_token)
            // retry request
            return response.request.newBuilder()
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

}