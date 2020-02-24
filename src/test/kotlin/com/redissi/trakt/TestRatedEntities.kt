package com.redissi.trakt

import com.redissi.trakt.entities.BaseRatedEntity
import org.amshove.kluent.shouldNotBeNull

interface TestRatedEntities {
    fun <T : BaseRatedEntity> assertRatedEntities(ratedEntities: List<T>) {
        for (ratedEntity in ratedEntities) {
            ratedEntity.ratedAt.shouldNotBeNull()
            ratedEntity.rating.shouldNotBeNull()
        }
    }
}