package com.uwetrottmann.trakt.v2.entities;

import com.google.gson.annotations.SerializedName;

public class User {

    public String username;
    @SerializedName("private")
    public Boolean isPrivate;
    public String name;
    public Boolean vip;

}
