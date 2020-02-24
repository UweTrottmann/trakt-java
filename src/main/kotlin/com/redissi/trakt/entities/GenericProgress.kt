package com.redissi.trakt.entities

abstract class GenericProgress {
    abstract val episode: SyncEpisode?
    abstract val show: SyncShow?
    abstract val movie: SyncMovie?
    abstract val progress: Double?
}