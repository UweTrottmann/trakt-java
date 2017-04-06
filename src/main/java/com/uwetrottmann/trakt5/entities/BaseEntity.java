package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

public abstract class BaseEntity {

    public String title;

    // extended info
    public String overview;
    public Double rating;
    public Integer votes;
    public OffsetDateTime updated_at;
    public List<String> available_translations;

}
