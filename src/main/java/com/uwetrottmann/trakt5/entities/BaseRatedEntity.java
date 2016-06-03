package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Rating;
import org.joda.time.DateTime;

public class BaseRatedEntity {

    public DateTime rated_at;
    public Rating rating;

}
