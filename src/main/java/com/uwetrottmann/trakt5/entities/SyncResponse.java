package com.uwetrottmann.trakt5.entities;

public class SyncResponse {

    public SyncStats added;
    public SyncStats existing;
    public SyncStats deleted;
    public SyncErrors not_found;

}
