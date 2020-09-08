package com.uwetrottmann.trakt5.enums;

import com.google.gson.annotations.SerializedName;

public enum SortBy {
    @SerializedName("rank")
    RANK,

    @SerializedName("added")
    ADDED,

    @SerializedName("title")
    TITLE,

    @SerializedName("released")
    RELEASED,

    @SerializedName("runtime")
    RUNTIME,

    @SerializedName("popularity")
    POPULARITY,

    @SerializedName("percentage")
    PERCENTAGE,

    @SerializedName("votes")
    VOTES,

    @SerializedName("my_rating")
    MY_RATING,

    @SerializedName("random")
    RANDOM

}
