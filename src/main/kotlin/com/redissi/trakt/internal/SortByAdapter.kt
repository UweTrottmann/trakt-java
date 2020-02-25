package com.redissi.trakt.internal

import com.redissi.trakt.enums.SortBy
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal class SortByAdapter {

    @ToJson
    fun toJson(sortBy: SortBy): String {
        return sortBy.toString()
    }

    @FromJson
    fun fromJson(sortBy: String): SortBy? {
        return SortBy.fromValue(sortBy)
    }

}