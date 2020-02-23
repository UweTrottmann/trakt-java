package com.redissi.trakt.entities

import com.squareup.moshi.Json

abstract class BaseCheckin {
    /** Control sharing to any connected social networks.  */
    abstract val sharing: ShareSettings?
    /** Message used for sharing. If not sent, it will use the watching string in the user settings.  */
    abstract val message: String?
    /** Foursquare venue ID.  */
    @Json(name = "venue_id")
    abstract val venueId: String?
    /** Foursquare venue name.  */
    @Json(name = "venue_name")
    abstract val venueName: String?
    @Json(name = "app_version")
    abstract val appVersion: String?
    /** Build date of the app.  */
    @Json(name = "app_date")
    abstract val appDate: String?
}