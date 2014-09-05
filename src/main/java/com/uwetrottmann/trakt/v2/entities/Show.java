package com.uwetrottmann.trakt.v2.entities;

import org.joda.time.DateTime;

public class Show extends BaseEntity {

    public int year;
    public ShowIds ids;

    // extended info
    public String overview;
    public DateTime first_aired;
    public String air_day;
    public DateTime air_time;
    public Integer runtime;
    public String certification;
    public String network;
    public String country;
    public String language;

}
