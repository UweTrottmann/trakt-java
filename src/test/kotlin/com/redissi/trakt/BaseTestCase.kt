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

    /**
     * Execute call with non-Void response body.
     */
    @Throws(IOException::class)
    fun <T> executeCall(call: Call<T>): T {
        val response = call.execute()
        if (!response.isSuccessful) {
            handleFailedResponse(response) // will throw error
        }
        val body = response.body()
        return body ?: throw IllegalStateException("Body should not be null for successful response")
    }

    /**
     * Execute call with Void response body.
     */
    @Throws(IOException::class)
    fun <T> executeVoidCall(call: Call<T>) {
        val response = call.execute()
        if (!response.isSuccessful) {
            handleFailedResponse(response) // will throw error
        }
    }

    fun assertSuccessfulResponse(response: Response<*>) {
        if (!response.isSuccessful) {
            handleFailedResponse(response)
        }
    }

    private fun handleFailedResponse(response: Response<*>) {
        if (response.code() == 401) {
            fail<Any>("Authorization required, supply a valid OAuth access token: "
                    + response.code() + " " + response.message())
        } else {
            var message = "Request failed: " + response.code() + " " + response.message()
            val error = trakt.checkForTraktError(response)
            if (error != null && error.message != null) {
                message += " message: " + error.message
            }
            fail<Any>(message)
        }
    }

    fun assertRatings(ratings: Ratings) {
        // rating can be null, but we use a show where we can be sure it's rated
        ratings.rating.shouldNotBeNull()
                .shouldBeGreaterOrEqualTo(0.0)
        ratings.votes.shouldNotBeNull()
                .shouldBeGreaterOrEqualTo(0)
        ratings.distribution.shouldNotBeNull()
                .shouldHaveSize(10)
    }

    fun assertStats(stats: Stats) {
        stats.watchers.shouldNotBeNull()
                .shouldBeGreaterOrEqualTo(0)
        stats.plays.shouldNotBeNull()
                .shouldBeGreaterOrEqualTo(0)
        stats.collectors.shouldNotBeNull()
                .shouldBeGreaterOrEqualTo(0)
        stats.comments.shouldNotBeNull()
                .shouldBeGreaterOrEqualTo(0)
        stats.lists.shouldNotBeNull()
                .shouldBeGreaterOrEqualTo(0)
        stats.votes.shouldNotBeNull()
                .shouldBeGreaterOrEqualTo(0)
    }

    fun assertShowStats(stats: Stats) {
        assertStats(stats)
        stats.collectedEpisodes.shouldNotBeNull().shouldBeGreaterOrEqualTo(0)
    }

    fun assertCast(credits: Credits, type: Type) {
        credits.cast.shouldNotBeNull()
        for (castMember in credits.cast!!) {
            castMember.character.shouldNotBeNull()
            when (type) {
                Type.SHOW -> {
                    castMember.movie.shouldBeNull()
                    castMember.show.shouldNotBeNull()
                    castMember.person.shouldBeNull()
                }
                Type.MOVIE -> {
                    castMember.movie.shouldNotBeNull()
                    castMember.show.shouldBeNull()
                    castMember.person.shouldBeNull()
                }
                Type.PERSON -> {
                    castMember.movie.shouldBeNull()
                    castMember.show.shouldBeNull()
                    castMember.person.shouldNotBeNull()
                }
                else -> {}
            }
        }
    }

    fun assertCrew(credits: Credits, type: Type) {
        val crew = credits.crew
        if (crew != null) {
            assertCrewMembers(crew.production, type)
            assertCrewMembers(crew.writing, type)
            assertCrewMembers(crew.directing, type)
            assertCrewMembers(crew.costumeAndMakeUp, type)
            assertCrewMembers(crew.sound, type)
            assertCrewMembers(crew.art, type)
            assertCrewMembers(crew.camera, type)
        }
    }

    fun assertCrewMembers(crew: List<CrewMember>?, type: Type) {
        if (crew == null) {
            return
        }
        for (crewMember in crew) {
            crewMember.job.shouldNotBeNull() // may be empty, so not checking for now
            when (type) {
                Type.SHOW -> {
                    crewMember.movie.shouldBeNull()
                    crewMember.show.shouldNotBeNull()
                    crewMember.person.shouldBeNull()
                }
                Type.MOVIE -> {
                    crewMember.movie.shouldNotBeNull()
                    crewMember.show.shouldBeNull()
                    crewMember.person.shouldBeNull()
                }
                Type.PERSON -> {
                    crewMember.movie.shouldBeNull()
                    crewMember.show.shouldBeNull()
                    crewMember.person.shouldNotBeNull()
                }
                else -> {}
            }
        }
    }

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