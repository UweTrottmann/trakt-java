package com.redissi.trakt.enums

import java.util.*

enum class ListPrivacy(val value: String) : TraktEnum {
    PRIVATE("private"), FRIENDS("friends"), PUBLIC("public");

    companion object {
        private val STRING_MAPPING: MutableMap<String, ListPrivacy> =
            HashMap()

        fun fromValue(value: String?): ListPrivacy? {
            return STRING_MAPPING[value]
        }

        init {
            for (via in values()) {
                STRING_MAPPING[via.toString()] = via
            }
        }
    }

    override fun toString(): String {
        return value
    }

}