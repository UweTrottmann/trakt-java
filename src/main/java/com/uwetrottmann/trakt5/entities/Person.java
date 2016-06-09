package com.uwetrottmann.trakt5.entities;

import org.joda.time.DateTime;

public class Person {

    public String name;
    public PersonIds ids;

    // extended info
    public Images images;
    public String biography;
    public DateTime birthday;
    public DateTime death;
    public String birthplace;
    public String homepage;

}
