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

package com.uwetrottmann.trakt5.enums;

import java.util.HashMap;
import java.util.Map;

public enum Resolution implements TraktEnum {

    UHD_4K("uhd_4k"),
    HD_1080P("hd_1080p"),
    HD_1080I("hd_1080i"),
    HD_720P("hd_720p"),
    SD_480P("sd_480p"),
    SD_480I("sd_480i"),
    SD_576P("sd_576p"),
    SD_576I("sd_576i");

    private final String value;

    Resolution(String value) {
        this.value = value;
    }

    private static final Map<String, Resolution> STRING_MAPPING = new HashMap<>();

    static {
        for (Resolution via : Resolution.values()) {
            STRING_MAPPING.put(via.toString(), via);
        }
    }

    public static Resolution fromValue(String value) {
        return STRING_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
