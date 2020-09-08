package com.uwetrottmann.trakt5.entities;

import com.google.gson.annotations.SerializedName;
import com.uwetrottmann.trakt5.enums.Audio;
import com.uwetrottmann.trakt5.enums.AudioChannels;
import com.uwetrottmann.trakt5.enums.Hdr;
import com.uwetrottmann.trakt5.enums.MediaType;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.Resolution;
import org.threeten.bp.OffsetDateTime;

public class SyncEpisode {

    public Integer season;
    public Integer number;
    public EpisodeIds ids;

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

    public SyncEpisode number(int number) {
        this.number = number;
        return this;
    }

    public SyncEpisode season(int season) {
        this.season = season;
        return this;
    }

    public SyncEpisode id(EpisodeIds id) {
        this.ids = id;
        return this;
    }

    public SyncEpisode collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncEpisode watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncEpisode ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncEpisode rating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public SyncEpisode mediaType(MediaType media_type){
        this.media_type = media_type;
        return this;
    }

    public SyncEpisode resolution(Resolution resolution){
        this.resolution = resolution;
        return this;
    }

    public SyncEpisode hdr(Hdr hdr){
        this.hdr = hdr;
        return this;
    }

    public SyncEpisode audio(Audio audio){
        this.audio = audio;
        return this;
    }

    public SyncEpisode audioChannels(AudioChannels audio_channels){
        this.audio_channels = audio_channels;
        return this;
    }

    public SyncEpisode is3d(Boolean is3d){
        this.is3d = is3d;
        return this;
    }

}
