package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestCast
import com.redissi.trakt.TestCrew
import com.redissi.trakt.enums.Extended
import com.redissi.trakt.enums.Type
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotBeNullOrEmpty
import org.junit.Test
import java.io.IOException

class PeopleTest : BaseTestCase(), TestCast, TestCrew {
    @Test
    @Throws(IOException::class)
    fun `get person's summary`() = runBlocking<Unit> {
        val person = trakt.people().summary(TEST_PERSON_SLUG, Extended.FULL)
        person.shouldNotBeNull()
        person.name.shouldNotBeNullOrEmpty()
        person.ids.shouldNotBeNull()
        person.ids!!.trakt.shouldNotBeNull()
        person.ids!!.slug.shouldNotBeNull()
    }

    @Test
    @Throws(IOException::class)
    fun `get person's movie credits`() = runBlocking {
        val credits = trakt.people().movieCredits(TEST_PERSON_SLUG)
        credits.shouldNotBeNull()
        assertCast(credits, Type.MOVIE)
        assertCrew(credits, Type.MOVIE)
    }

    @Test
    @Throws(IOException::class)
    fun `get person's show credits`() = runBlocking {
        val credits = trakt.people().showCredits(TEST_PERSON_SLUG)
        credits.shouldNotBeNull()
        assertCast(credits, Type.SHOW)
        assertCrew(credits, Type.SHOW)
    }

    companion object {
        private const val TEST_PERSON_SLUG = "bryan-cranston"
    }
}