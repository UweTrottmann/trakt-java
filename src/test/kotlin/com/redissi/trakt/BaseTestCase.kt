package com.redissi.trakt

import com.redissi.trakt.services.Authentication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.BeforeClass
import retrofit2.create
import kotlin.test.fail

abstract class BaseTestCase {
    internal class TestTrakt : Trakt {
        constructor(apiKey: String) : super(apiKey, staging = true)
        constructor(apiKey: String, clientSecret: String?, redirectUri: String?)
                : super(apiKey, clientSecret, redirectUri, staging = true)

        override fun setOkHttpClientDefaults(builder: OkHttpClient.Builder) {
            super.setOkHttpClientDefaults(builder)
            // add logging standard output is easier to read
            val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    println(message)
                }
            })
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(logging)
        }

        override fun authentication(): Authentication {
            return retrofit.create<StagingAuthentication>()
        }
    }

    protected open val trakt: Trakt = BaseTestCase.trakt

    companion object {
        val TEST_CLIENT_ID = System.getProperty("TRAKT_CLIENT_ID") ?: fail("No TEST_CLIENT_ID")
        val TEST_ACCESS_TOKEN = System.getProperty("TRAKT_ACCESS_TOKEN") ?: fail("No TEST_CLIENT_ID")

        internal val trakt = TestTrakt(TEST_CLIENT_ID)

        const val DEFAULT_PAGE_SIZE = 10

        @BeforeClass
        @JvmStatic
        fun setUpOnce() {
            trakt.accessToken(TEST_ACCESS_TOKEN)
        }
    }
}