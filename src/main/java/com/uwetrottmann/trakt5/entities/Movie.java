package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.LocalDate;

import java.util.List;

public class Movie extends BaseEntity {

    public Integer year;
    public MovieIds ids;

    // extended info
    public String certification;
    public String tagline;
    /** Date in UTC time. */
    public LocalDate released;
    public Integer runtime;
    public String trailer;
    public String homepage;
    public String language;
    public List<String> genres;

}
