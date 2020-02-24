package com.redissi.trakt

import com.redissi.trakt.entities.BaseMovie
import com.redissi.trakt.entities.BaseShow
import org.amshove.kluent.shouldBeGreaterOrEqualTo
import org.amshove.kluent.shouldBePositive
import org.amshove.kluent.shouldNotBeNull

interface TestSync {

    fun assertSyncMovies(baseMovies: List<BaseMovie>, type: String?) {
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

    fun assertSyncShows(baseShows: List<BaseShow>, type: String) {
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