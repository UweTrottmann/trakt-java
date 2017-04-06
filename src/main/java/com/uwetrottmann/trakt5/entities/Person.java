package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class Person {

    public String name;
    public PersonIds ids;

    // extended info
    public String biography;
    public OffsetDateTime birthday;
    public OffsetDateTime death;
    public String birthplace;
    public String homepage;

}
