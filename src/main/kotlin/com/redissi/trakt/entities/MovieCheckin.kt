package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieCheckin(
    override val sharing: ShareSettings? = null,
    override val message: String? = null,
    @Json(name = "venue_id")
    override val venueId: String? = null,
    @Json(name = "venue_name")
    override val venueName: String? = null,
    @Json(name = "app_version")
    override val appVersion: String? = null,
    @Json(name = "app_date")
    override val appDate: String? = null,
    val movie: SyncMovie? = null
) : BaseCheckin() {

    class Builder(
        movie: SyncMovie?,
        appVersion: String,
        appDate: String
    ) {
        private val movie: SyncMovie
        private var sharing: ShareSettings? = null
        private var message: String? = null
        private var venueId: String? = null
        private var venueName: String? = null
        private var appVersion: String
        private var appDate: String

        fun sharing(shareSettings: ShareSettings?): Builder {
            sharing = shareSettings
            return this
        }

        fun message(message: String?): Builder {
            this.message = message
            return this
        }

        fun venueId(venueId: String?): Builder {
            this.venueId = venueId
            return this
        }

        fun venueName(venueName: String?): Builder {
            this.venueName = venueName
            return this
        }

        fun build(): MovieCheckin {
            return MovieCheckin(
                movie = movie,
                sharing = sharing,
                message = message,
                venueId = venueId,
                venueName = venueName,
                appDate = appDate,
                appVersion = appVersion
            )
        }

        init {
            requireNotNull(movie) { "Movie must not be null" }
            this.movie = movie
            this.appVersion = appVersion
            this.appDate = appDate
        }
    }
}