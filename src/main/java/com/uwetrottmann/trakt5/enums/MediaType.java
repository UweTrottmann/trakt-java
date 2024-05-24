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

public enum MediaType implements TraktEnum {

    DIGITAL("digital"),
    BLURAY("bluray"),
    HDDVD("hddvd"),
    DVD("dvd"),
    VCD("vcd"),
    VHS("vhs"),
    BETAMAX("betamax"),
    LASERDISC("laserdisc");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    private static final Map<String, MediaType> STRING_MAPPING = new HashMap<>();

    static {
        for (MediaType via : MediaType.values()) {
            STRING_MAPPING.put(via.toString(), via);
        }
    }

    public static MediaType fromValue(String value) {
        return STRING_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
