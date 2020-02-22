package com.redissi.trakt

import com.redissi.trakt.entities.Ratings
import org.amshove.kluent.shouldBeGreaterOrEqualTo
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotBeNull

interface TestRatings {

    fun assertRatings(ratings: Ratings) {
        // rating can be null, but we use a show where we can be sure it's rated
        ratings.rating.shouldNotBeNull()
            .shouldBeGreaterOrEqualTo(0.0)
        ratings.votes.shouldNotBeNull()
            .shouldBeGreaterOrEqualTo(0)
        ratings.distribution.shouldNotBeNull()
            .shouldHaveSize(10)
    }

}