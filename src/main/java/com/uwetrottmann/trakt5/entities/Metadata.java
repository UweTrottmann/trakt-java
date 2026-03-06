/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.entities;

import com.google.gson.annotations.SerializedName;
import com.uwetrottmann.trakt5.enums.Audio;
import com.uwetrottmann.trakt5.enums.AudioChannels;
import com.uwetrottmann.trakt5.enums.Hdr;
import com.uwetrottmann.trakt5.enums.MediaType;
import com.uwetrottmann.trakt5.enums.Resolution;

/**
 * Metadata of a collection item.
 * <p>
 * All but {@link #media_type} and {@link #resolution} are optional values.
 */
public class Metadata {

    public MediaType media_type;
    public Resolution resolution;
    public Hdr hdr;
    public Audio audio;
    public AudioChannels audio_channels;

    /**
     * Since 2026-03 the 3d property appears to be a number for movies.
     * <p>
     * This field is kept to avoid a breaking change, but its value will always be {@code null} for movies.
     *
     * @deprecated Use {@link #is3D()} instead.
     */
    @Deprecated
    public Boolean is3d;

    /**
     * Since 2026-03 appears to be 1 or null for movies. Or true and false for episodes.
     *
     * @see #is3D()
     */
    @SerializedName("3d")
    public Object _is3D;

    public boolean is3D() {
        if (_is3D != null) {
            if (_is3D instanceof Boolean) {
                return (Boolean) _is3D;
            }
            // Numbers are deserialized by default to Double
            if (_is3D instanceof Double) {
                return (Double) _is3D == 1.0;
            }
        }
        return false;
    }

}
