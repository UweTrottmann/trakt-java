package com.redissi.trakt.internal

import com.redissi.trakt.enums.SortHow
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal class SortHowAdapter {

    @ToJson
    fun toJson(sortHow: SortHow): String {
        return sortHow.toString()
    }

    @FromJson
    fun fromJson(sortHow: String): SortHow? {
        return SortHow.fromValue(sortHow)
    }

}