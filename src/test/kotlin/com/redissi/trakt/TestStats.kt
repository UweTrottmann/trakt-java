package com.redissi.trakt

import com.redissi.trakt.entities.Stats
import org.amshove.kluent.shouldBeGreaterOrEqualTo
import org.amshove.kluent.shouldNotBeNull

interface TestStats {

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

}