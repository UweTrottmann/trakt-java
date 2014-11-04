package com.uwetrottmann.trakt.v2.entities;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class User {

    public String username;
    @SerializedName("private")
    public Boolean isPrivate;
    public String name;
    public Boolean vip;

    // full
    public DateTime joined_at;
    public String location;
    public String about;
    public String gender;
    public int age;

    // images
    public Images images;

}
