package com.redissi.trakt.enums

interface TraktEnum {
    /**
     * Return the value to be used by retrofit when building a request.
     */
    override fun toString(): String
}