package com.uwetrottmann.trakt.v2.entities;

import com.uwetrottmann.trakt.v2.enums.Rating;
import org.joda.time.DateTime;

public class BaseRatedEntity {

    public DateTime rated_at;
    public Rating rating;

}
