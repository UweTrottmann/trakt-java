package com.redissi.trakt.internal

import com.redissi.trakt.enums.Rating
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal class RatingAdapter {

    @ToJson
    fun toJson(rating: Rating): String {
        return rating.toString()
    }

    @FromJson
    fun fromJson(rating: Int): Rating {
        return Rating.fromValue(rating)
    }

}