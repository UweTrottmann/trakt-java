package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.enumerations.ListPrivacy;

public class ListResponse extends Response {

    private static final long serialVersionUID = 5368378936105337182L;

    public String name;

    public String slug;

    public ListPrivacy privacy;

    public boolean show_numbers;

    public boolean allow_shouts;

}
