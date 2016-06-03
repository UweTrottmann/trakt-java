package com.uwetrottmann.trakt5;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class Utils {

    public static final int CONNECT_TIMEOUT_MILLIS = 15000; // 15 seconds
    public static final int READ_TIMEOUT_MILLIS = 20000; // 20 seconds

    /**
     * Create an OkHttpClient with sensible timeouts for mobile connections.
     */
    public static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // set timeouts
        builder.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        builder.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        return builder.build();
    }

}
