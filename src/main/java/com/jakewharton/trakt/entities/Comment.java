
package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

import java.util.Calendar;

public class Comment implements TraktEntity {
    private static final long serialVersionUID = -4982983176604728357L;

    public Integer id;
    public Calendar inserted;
    public String text;
    public String text_html;
    public boolean spoiler;
    public String type;
    public Integer likes;
    public Integer replies;
    public UserProfile user;
    public UserRatings user_ratings;

    public static class UserRatings implements TraktEntity {
        private static final long serialVersionUID = 4589215593816892100L;

        public Integer rating_advanced;
    }
}
