package com.redissi.trakt

import com.redissi.trakt.entities.Credits
import com.redissi.trakt.enums.Type
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldNotBeNull

interface TestCast {

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

}