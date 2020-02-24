package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
class PlaybackResponse(
    override val episode: SyncEpisode? = null,
    override val show: SyncShow? = null,
    override val movie: SyncMovie? = null,
    override val progress: Double? = null,
    val id: Long? = null,
    /** Playpack response only.  */
    val paused_at: OffsetDateTime? = null,
    /** Playpack response only.  */
    val type: String? = null,
    /** Scrobble response only.  */
    val action: String? = null,
    /** Scrobble response only.  */
    val sharing: ShareSettings? = null
) : GenericProgress() {

}