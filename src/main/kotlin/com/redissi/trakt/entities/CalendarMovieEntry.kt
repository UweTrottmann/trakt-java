package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
class CalendarMovieEntry(
    /** Date in UTC time.  */
    val released: LocalDate? = null,
    val movie: Movie? = null
)