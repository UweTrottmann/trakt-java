package com.uwetrottmann.trakt.v2.entities;

public abstract class BaseCheckin {

    /** Control sharing to any connected social networks. */
    public ShareSettings sharing;
    /** Message used for sharing. If not sent, it will use the watching string in the user settings. */
    public String message;
    /** Foursquare venue ID. */
    public String venue_id;
    /** Foursquare venue name. */
    public String venue_name;
    public String app_version;
    /** Build date of the app. */
    public String app_date;

}
