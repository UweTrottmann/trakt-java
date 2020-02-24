package com.redissi.trakt.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ScrobbleProgress constructor(
    override val episode: SyncEpisode? = null,
    override val show: SyncShow? = null,
    override val movie: SyncMovie? = null,
    override val progress: Double,
    @Json(name = "app_version")
    val appVersion: String? = null,
    @Json(name = "app_date")
    val appDate: String? = null
) : GenericProgress() {

    companion object {
        fun create(
            episode: SyncEpisode,
            progress: Double,
            appVersion: String?,
            appDate: String?
        ): ScrobbleProgress {
            return ScrobbleProgress(episode = episode, progress = progress, appVersion = appVersion, appDate = appDate)
        }

        fun create(
            movie: SyncMovie,
            progress: Double,
            appVersion: String?,
            appDate: String?
        ): ScrobbleProgress {
            return ScrobbleProgress(movie = movie, progress = progress, appVersion = appVersion, appDate = appDate)
        }
    }

}