package com.redissi.trakt.enums

import java.util.*

enum class SortHow(private val value: String) : TraktEnum {
    ASC("asc"), DESC("desc");

    companion object {
        private val STRING_MAPPING: MutableMap<String, SortHow> =
            HashMap()

        fun fromValue(value: String): SortHow? {
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