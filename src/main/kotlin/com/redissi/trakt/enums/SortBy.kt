package com.redissi.trakt.enums

import java.util.*

enum class SortBy(private val value: String) : TraktEnum {
    RANK("rank"), ADDED("added"), TITLE("title"), RELEASED("released"), RUNTIME("runtime"), POPULARITY("popularity"), PERCENTAGE(
        "percentage"
    ),
    VOTES("votes"), MY_RATING("my_rating"), RANDOM("random");

    companion object {
        private val STRING_MAPPING: MutableMap<String, SortBy> =
            HashMap()

        fun fromValue(value: String): SortBy? {
            return STRING_MAPPING[value.toUpperCase(Locale.ROOT)]
        }

        init {
            for (via in values()) {
                STRING_MAPPING[via.toString().toUpperCase(Locale.ROOT)] = via
            }
        }
    }

    override fun toString(): String {
        return value
    }

}