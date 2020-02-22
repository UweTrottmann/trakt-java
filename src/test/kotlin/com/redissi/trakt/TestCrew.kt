package com.redissi.trakt

import com.redissi.trakt.entities.Credits
import com.redissi.trakt.entities.CrewMember
import com.redissi.trakt.enums.Type
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldNotBeNull

interface TestCrew {

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

}