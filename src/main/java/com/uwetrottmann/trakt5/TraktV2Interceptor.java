package com.uwetrottmann.trakt5;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nullable;
import java.io.IOException;

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
     * If the host matches {@link TraktV2#API_HOST} or {@link TraktV2#API_STAGING_HOST} adds a header for
     * the current {@link TraktV2#API_VERSION}, {@link TraktV2#HEADER_TRAKT_API_KEY} with the given api key,
     * {@link TraktV2#HEADER_CONTENT_TYPE} and if not present an
     * Authorization header using the given access token.
     */
    public static Response handleIntercept(Chain chain, String apiKey,
            @Nullable String accessToken) throws IOException {
        Request request = chain.request();
        if (!(TraktV2.API_HOST.equals(request.url().host()) || TraktV2.API_STAGING_HOST.equals(request.url().host()))) {
            // do not intercept requests for other hosts
            // this allows the interceptor to be used on a shared okhttp client
            return chain.proceed(request);
        }

        return handleInterceptInternal(chain, apiKey, accessToken);
    }

    /**
     *  Version of {@link TraktV2Interceptor#handleIntercept(okhttp3.Interceptor.Chain, java.lang.String, java.lang.String)}
     *  that supports any configured trakt api host.
     */
    public static Response handleIntercept(Chain chain, String apiKey,
            @Nullable String accessToken, @Nullable TraktV2 trakt) throws IOException {
        if (trakt == null) {
            return handleIntercept(chain, apiKey, accessToken);
        }

        Request request = chain.request();
        if (trakt.apiHost().equals(request.url().host())) {
            return handleInterceptInternal(chain, apiKey, accessToken);
        }
        return chain.proceed(request);
    }


    private static Response handleInterceptInternal(Chain chain, String apiKey,
            @Nullable String accessToken) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        // set required content type, api key and request specific API version
        builder.header(TraktV2.HEADER_CONTENT_TYPE, TraktV2.CONTENT_TYPE_JSON);
        builder.header(TraktV2.HEADER_TRAKT_API_KEY, apiKey);
        builder.header(TraktV2.HEADER_TRAKT_API_VERSION, TraktV2.API_VERSION);

        // add authorization header
        if (hasNoAuthorizationHeader(request) && accessTokenIsNotEmpty(accessToken)) {
            builder.header(TraktV2.HEADER_AUTHORIZATION, "Bearer" + " " + accessToken);
        }
        return chain.proceed(builder.build());
    }

    private static boolean hasNoAuthorizationHeader(Request request) {
        return request.header(TraktV2.HEADER_AUTHORIZATION) == null;
    }

    private static boolean accessTokenIsNotEmpty(@Nullable String accessToken) {
        return accessToken != null && accessToken.length() != 0;
    }
}
