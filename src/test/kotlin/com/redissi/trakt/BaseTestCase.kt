package com.redissi.trakt

import com.redissi.trakt.entities.*
import com.redissi.trakt.enums.Type
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.amshove.kluent.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.BeforeClass
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

abstract class BaseTestCase {
    internal class TestTrakt : Trakt {
        constructor(apiKey: String) : super(apiKey, staging = true)
        constructor(apiKey: String, clientSecret: String?, redirectUri: String?)
                : super(apiKey, clientSecret, redirectUri, true)

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
    }

    protected val trakt: Trakt = BaseTestCase.trakt

    companion object {
        protected val TEST_CLIENT_ID = System.getProperty("TRAKT_CLIENT_ID") ?: fail("No TEST_CLIENT_ID")
        val TEST_ACCESS_TOKEN = System.getProperty("TRAKT_ACCESS_TOKEN") ?: fail("No TEST_CLIENT_ID")

        private val trakt = TestTrakt(TEST_CLIENT_ID)

        const val DEFAULT_PAGE_SIZE = 10

        @BeforeClass
        fun setUpOnce() {
            trakt.accessToken(TEST_ACCESS_TOKEN)
        }

        protected fun <T : BaseRatedEntity> assertRatedEntities(ratedEntities: List<T>) {
            for (ratedEntity in ratedEntities) {
                ratedEntity.ratedAt.shouldNotBeNull()
                ratedEntity.rating.shouldNotBeNull()
            }
        }

        protected fun assertSyncMovies(baseMovies: List<BaseMovie>, type: String?) {
            for (baseMovie in baseMovies) {
                baseMovie.movie.shouldNotBeNull()
                when (type) {
                    "collection" -> baseMovie.collectedAt.shouldNotBeNull()
                    "watched" -> {
                        baseMovie.plays.shouldBePositive()
                        baseMovie.lastWatchedAt.shouldNotBeNull()
                        baseMovie.lastUpdatedAt.shouldNotBeNull()
                    }
                    "watchlist" -> baseMovie.listedAt.shouldNotBeNull()
                }
            }
        }

        protected fun assertSyncShows(baseShows: List<BaseShow>, type: String) {
            for (baseShow in baseShows) {
                baseShow.show.shouldNotBeNull()
                if ("collection" == type) {
                    baseShow.lastCollectedAt.shouldNotBeNull()
                } else if ("watched" == type) {
                    baseShow.plays.shouldNotBeNull()
                            .shouldBePositive()
                    baseShow.lastWatchedAt.shouldNotBeNull()
                    baseShow.lastUpdatedAt.shouldNotBeNull()
                }
                baseShow.seasons.shouldNotBeNull()
                for (season in baseShow.seasons!!) {
                    season.number.shouldNotBeNull()
                            .shouldBeGreaterOrEqualTo(0)
                    season.episodes.shouldNotBeNull()
                    for (episode in season.episodes!!) {
                        episode.number.shouldNotBeNull()
                                .shouldBeGreaterOrEqualTo(0)
                        if ("collection" == type) {
                            episode.collectedAt.shouldNotBeNull()
                        } else if ("watched" == type) {
                            episode.plays.shouldNotBeNull()
                                    .shouldBePositive()
                            episode.lastWatchedAt.shouldNotBeNull()
                        }
                    }
                }
            }
        }
    }
}