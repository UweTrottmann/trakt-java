package com.uwetrottmann.trakt5.entities;

import javax.annotation.Nonnull;

public class SyncPerson {

    public PersonIds ids;
    public String name;

    @Nonnull
    public SyncPerson id(PersonIds id) {
        this.ids = id;
        return this;
    }

    @Nonnull
    public SyncPerson name(String name) {
        this.name = name;
        return this;
    }

}
