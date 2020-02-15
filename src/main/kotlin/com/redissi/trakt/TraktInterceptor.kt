package com.redissi.trakt

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TraktInterceptor(private val trakt: Trakt) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return handleIntercept(chain, trakt.apiKey, trakt.accessToken, trakt.staging)
    }

    companion object {
        /**
         * If the host matches [Trakt.getApiHost] adds a header for the current [Trakt.API_VERSION],
         * [Trakt.HEADER_TRAKT_API_KEY] with the given api key, [Trakt.HEADER_CONTENT_TYPE] and if not present an
         * Authorization header using the given access token.
         */
        @Throws(IOException::class)
        fun handleIntercept(
                chain: Interceptor.Chain,
                apiKey: String?,
                accessToken: String?,
                staging: Boolean = false
        ): Response {
            val request = chain.request()
            if (Trakt.getApiHost(staging) != request.url.host) {
                // do not intercept requests for other hosts
                // this allows the interceptor to be used on a shared okhttp client
                return chain.proceed(request)
            }
            val builder = request.newBuilder()
            // set required content type, api key and request specific API version
            builder.header(Trakt.HEADER_CONTENT_TYPE, Trakt.CONTENT_TYPE_JSON)
            builder.header(Trakt.HEADER_TRAKT_API_KEY, apiKey!!)
            builder.header(Trakt.HEADER_TRAKT_API_VERSION, Trakt.API_VERSION)
            // add authorization header
            if (hasNoAuthorizationHeader(request) && accessTokenIsNotEmpty(accessToken)) {
                builder.header(Trakt.HEADER_AUTHORIZATION, "Bearer $accessToken")
            }
            return chain.proceed(builder.build())
        }

        private fun hasNoAuthorizationHeader(request: Request): Boolean {
            return request.header(Trakt.HEADER_AUTHORIZATION) == null
        }

        private fun accessTokenIsNotEmpty(accessToken: String?): Boolean {
            return accessToken != null && accessToken.isNotEmpty()
        }
    }

}