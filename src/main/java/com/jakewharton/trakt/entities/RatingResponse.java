package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.Rating;
import com.jakewharton.trakt.enumerations.RatingType;

public class RatingResponse extends Response implements TraktEntity {
    private static final long serialVersionUID = 8424378149600617021L;

    public RatingType type;
    public Rating rating;
    public Ratings ratings;
    public Boolean facebook;
    public Boolean twitter;
    public Boolean tumblr;

}
