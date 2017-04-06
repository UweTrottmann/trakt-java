package com.uwetrottmann.trakt5.entities;

import com.google.gson.annotations.SerializedName;
import org.threeten.bp.OffsetDateTime;

public class User {

    public String username;
    @SerializedName("private")
    public Boolean isPrivate;
    public String name;
    /** If a user is a regular VIP. */
    public Boolean vip;
    /** If a user is an execute producer. */
    public Boolean vip_ep;
    public UserIds ids;

    // full
    public OffsetDateTime joined_at;
    public String location;
    public String about;
    public String gender;
    public int age;
    public Images images;

    public static class UserIds {
        public String slug;
    }
}
