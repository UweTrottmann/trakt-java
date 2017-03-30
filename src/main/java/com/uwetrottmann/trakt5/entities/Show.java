package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.Status;

import java.util.Date;
import java.util.List;

public class Show extends BaseEntity {

    public Integer year;
    public ShowIds ids;

    // extended info
    public Date first_aired;
    public Airs airs;
    public Integer runtime;
    public String certification;
    public String network;
    public String country;
    public String trailer;
    public String homepage;
    public Status status;
    public String language;
    public List<String> genres;

}
