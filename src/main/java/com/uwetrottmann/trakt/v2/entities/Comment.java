package com.uwetrottmann.trakt.v2.entities;

import java.util.Date;

public class Comment {

    public Integer id;
    public Integer parent_id;
    public Date created_at;
    public String comment;
    public Boolean spoiler;
    public Boolean review;
    public Integer replies;
    public User user;

}
