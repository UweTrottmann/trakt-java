package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;
import com.jakewharton.trakt.enumerations.ListPrivacy;

public class List implements TraktEntity {
    private static final long serialVersionUID = -5768791212077534364L;

    public String name;
    public String slug;
    public String url;
    public String description;
    public ListPrivacy privacy;
    public java.util.List<ListItem> items;

}
