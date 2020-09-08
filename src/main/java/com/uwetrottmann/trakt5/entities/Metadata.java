package com.uwetrottmann.trakt5.entities;

import com.google.gson.annotations.SerializedName;
import com.uwetrottmann.trakt5.enums.Audio;
import com.uwetrottmann.trakt5.enums.AudioChannels;
import com.uwetrottmann.trakt5.enums.Hdr;
import com.uwetrottmann.trakt5.enums.MediaType;
import com.uwetrottmann.trakt5.enums.Resolution;

public class Metadata {

    public MediaType media_type;
    public Resolution resolution;
    public Hdr hdr;
    public Audio audio;
    public AudioChannels audio_channels;
    @SerializedName("3d")
    public Boolean is3d;

}
