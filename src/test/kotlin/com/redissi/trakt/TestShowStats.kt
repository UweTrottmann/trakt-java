package com.redissi.trakt

import com.redissi.trakt.entities.Stats
import org.amshove.kluent.shouldBeGreaterOrEqualTo
import org.amshove.kluent.shouldNotBeNull

interface TestShowStats : TestStats {

    fun assertShowStats(stats: Stats) {
        assertStats(stats)
        stats.collectedEpisodes.shouldNotBeNull().shouldBeGreaterOrEqualTo(0)
    }

}