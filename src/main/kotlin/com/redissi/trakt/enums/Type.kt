package com.redissi.trakt.enums

import com.uwetrottmann.trakt5.enums.TraktEnum

enum class Type(private val value: String) : TraktEnum {
    MOVIE("movie"),
    SHOW("show"),
    EPISODE("episode"),
    PERSON("person"),
    LIST("list");

    override fun toString(): String {
        return value
    }

}