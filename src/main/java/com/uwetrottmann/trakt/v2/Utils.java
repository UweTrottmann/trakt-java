package com.uwetrottmann.trakt.v2;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class Utils {

    public static final int CONNECT_TIMEOUT_MILLIS = 15000; // 15 seconds
    public static final int READ_TIMEOUT_MILLIS = 20000; // 20 seconds

    /**
     * Create an OkHttpClient with sensible timeouts for mobile connections.
     */
    public static OkHttpClient createOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();

        // set timeouts
        okHttpClient.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        return okHttpClient;
    }

}
