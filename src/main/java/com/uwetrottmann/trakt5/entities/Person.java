package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.LocalDate;

public class Person {

    public String name;
    public PersonIds ids;

    // extended info
    public String biography;
    public LocalDate birthday;
    public LocalDate death;
    public String birthplace;
    public String homepage;

}
