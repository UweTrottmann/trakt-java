package com.redissi.trakt.internal

import com.redissi.trakt.enums.ListPrivacy
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal class ListPrivacyAdapter {

    @ToJson
    fun toJson(listPrivacy: ListPrivacy): String {
        return listPrivacy.toString()
    }

    @FromJson
    fun fromJson(listPrivacy: String): ListPrivacy? {
        return ListPrivacy.fromValue(listPrivacy)
    }

}