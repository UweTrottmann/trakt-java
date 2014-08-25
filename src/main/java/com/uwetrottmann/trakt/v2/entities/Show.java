package com.uwetrottmann.trakt.v2.entities;

import java.util.Date;

public class Show extends BaseEntity {

    public int year;
    public ShowIds ids;

    // extended info
    public String overview;
    public Date first_aired;
    public String air_day;
    public Date air_time;
    public Integer runtime;
    public String certification;
    public String network;
    public String country;
    public String language;

}
