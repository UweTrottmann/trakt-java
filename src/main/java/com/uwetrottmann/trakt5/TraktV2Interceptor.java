/*
 * Copyright 2016-2024 Uwe Trottmann
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

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Adds API key and version headers and if available an authorization header. As it may retry requests (on HTTP 429
 * responses), ensure this is added as an application interceptor (never a network interceptor), otherwise caching will
 * be broken and requests will fail. See {@link #handleIntercept(Chain, String, String)}.
 */
public class TraktV2Interceptor implements Interceptor {

    private final TraktV2 trakt;

    public TraktV2Interceptor(TraktV2 trakt) {
        this.trakt = trakt;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return handleIntercept(chain, trakt.apiKey(), trakt.accessToken());
    }

    /**
     * If the host matches {@link TraktV2#API_HOST} or {@link TraktV2#API_STAGING_HOST} adds a header for the current
     * {@link TraktV2#API_VERSION}, {@link TraktV2#HEADER_TRAKT_API_KEY} with the given api key,
     * {@link TraktV2#HEADER_CONTENT_TYPE} and if not present an Authorization header using the given access token.
     * <p>
     * If a request fails due to HTTP 429 Too Many Requests, will retry the request after the time in seconds given by
     * the Retry-After response header.
     */
    public static Response handleIntercept(Chain chain, String apiKey,
            @Nullable String accessToken) throws IOException {
        Request request = chain.request();
        if (!(TraktV2.API_HOST.equals(request.url().host()) || TraktV2.API_STAGING_HOST.equals(request.url().host()))) {
            // do not intercept requests for other hosts
            // this allows the interceptor to be used on a shared okhttp client
            return chain.proceed(request);
        }

        Request.Builder builder = request.newBuilder();

        // set required content type, api key and request specific API version
        builder.header(TraktV2.HEADER_CONTENT_TYPE, TraktV2.CONTENT_TYPE_JSON);
        builder.header(TraktV2.HEADER_TRAKT_API_KEY, apiKey);
        builder.header(TraktV2.HEADER_TRAKT_API_VERSION, TraktV2.API_VERSION);

        // add authorization header
        if (hasNoAuthorizationHeader(request) && accessTokenIsNotEmpty(accessToken)) {
            builder.header(TraktV2.HEADER_AUTHORIZATION, "Bearer" + " " + accessToken);
        }

        // Try the request.
        Response response = chain.proceed(builder.build());

        // Handle Trakt rate limit errors https://trakt.docs.apiary.io/#introduction/rate-limiting
        if (response.code() == 429 /* Too Many Requests */) {
            // Re-try if the server indicates we should.
            String retryHeader = response.header("Retry-After");
            if (retryHeader != null) {
                try {
                    int retry = Integer.parseInt(retryHeader);
                    // Wait a little longer than the server indicates (+ half second).
                    Thread.sleep((int) ((retry + 0.5) * 1000));

                    // Close body of unsuccessful response.
                    if (response.body() != null) {
                        response.body().close();
                    }

                    // Try again.
                    // Is fine, because unlike a network interceptor, an application interceptor can re-try requests.
                    return handleIntercept(chain, apiKey, accessToken);
                } catch (NumberFormatException | InterruptedException ignored) {
                    // No valid Retry-After header or timed out, return failed response.
                }
            }
        }

        return response;
    }

    private static boolean hasNoAuthorizationHeader(Request request) {
        return request.header(TraktV2.HEADER_AUTHORIZATION) == null;
    }

    private static boolean accessTokenIsNotEmpty(@Nullable String accessToken) {
        return accessToken != null && accessToken.length() != 0;
    }
}
