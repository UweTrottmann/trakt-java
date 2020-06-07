package com.uwetrottmann.trakt5.entities;

import com.google.gson.annotations.SerializedName;
import com.uwetrottmann.trakt5.enums.Audio;
import com.uwetrottmann.trakt5.enums.AudioChannels;
import com.uwetrottmann.trakt5.enums.Hdr;
import com.uwetrottmann.trakt5.enums.MediaType;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.Resolution;
import org.threeten.bp.OffsetDateTime;

public class SyncMovie {

    public MovieIds ids;

    public OffsetDateTime collected_at;
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;
    public MediaType media_type;
    public Resolution resolution;
    public Hdr hdr;
    public Audio audio;
    public AudioChannels audio_channels;
    @SerializedName("3d")
    public Boolean is3d;

    public SyncMovie id(MovieIds id) {
        this.ids = id;
        return this;
    }

    public SyncMovie collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncMovie watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncMovie ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncMovie rating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public SyncMovie mediaType(MediaType media_type){
        this.media_type = media_type;
        return this;
    }

    public SyncMovie resolution(Resolution resolution){
        this.resolution = resolution;
        return this;
    }

    public SyncMovie hdr(Hdr hdr){
        this.hdr = hdr;
        return this;
    }

    public SyncMovie audio(Audio audio){
        this.audio = audio;
        return this;
    }

    public SyncMovie audioChannels(AudioChannels audio_channels){
        this.audio_channels = audio_channels;
        return this;
    }

    public SyncMovie is3d(Boolean is3d){
        this.is3d = is3d;
        return this;
    }

}
