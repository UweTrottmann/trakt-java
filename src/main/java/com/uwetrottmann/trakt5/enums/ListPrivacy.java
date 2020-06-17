package com.uwetrottmann.trakt5.enums;

import com.google.gson.annotations.SerializedName;

public enum ListPrivacy {

    @SerializedName("private")
    PRIVATE,

    @SerializedName("friends")
    FRIENDS,

    @SerializedName("public")
    PUBLIC
}
