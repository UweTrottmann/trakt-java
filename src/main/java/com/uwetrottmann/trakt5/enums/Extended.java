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

/**
 * By default, all methods will return minimal info for movies, shows, episodes, and users. Minimal info is typically
 * all you need to match locally cached items and includes the title, year, and ids. However, you can request different
 * extended levels of information.
 */
public enum Extended implements TraktEnum {

    /** Complete info for an item. */
    FULL("full"),
    /** Only works with sync watchedShows. */
    NOSEASONS("noseasons"),
    /** Only works with seasons/summary */
    EPISODES("episodes"),
    /** Only works with seasons/summary */
    FULLEPISODES("full,episodes"),
    /** Only work with movies/episodes */
    METADATA("metadata");

    private final String value;

    Extended(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
