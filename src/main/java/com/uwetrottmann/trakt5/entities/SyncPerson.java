package com.uwetrottmann.trakt5.entities;

public class SyncPerson {

    public PersonIds ids;
    public String name;

    public SyncPerson id(PersonIds id) {
        this.ids = id;
        return this;
    }

    public SyncPerson name(String name) {
        this.name = name;
        return this;
    }

}
