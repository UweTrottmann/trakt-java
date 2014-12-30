package com.uwetrottmann.trakt.v2.entities;

import com.uwetrottmann.trakt.v2.enums.Status;
import org.joda.time.DateTime;

import java.util.List;

public class Show extends BaseEntity {

    public int year;
    public ShowIds ids;

    // extended info
    public String overview;
    public DateTime first_aired;
    public Airs airs;
    public Integer runtime;
    public String certification;
    public String network;
    public String country;
    public String trailer;
    public String homepage;
    public Status status;
    public Double rating;
    public String language;
    public List<String> genres;

}
