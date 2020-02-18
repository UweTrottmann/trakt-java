package com.redissi.trakt.enums

import java.util.*

enum class Status(private val value: String) : TraktEnum {
    ENDED("ended"),
    RETURNING("returning series"),
    CANCELED("canceled"),
    IN_PRODUCTION("in production");

    companion object {
        private val STRING_MAPPING: MutableMap<String, Status> = HashMap()
        fun fromValue(value: String): Status? {
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