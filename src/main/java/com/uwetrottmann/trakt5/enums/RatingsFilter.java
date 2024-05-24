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

public enum RatingsFilter implements TraktEnum {
    ALL(""),
    WEAKSAUCE("/1"),
    TERRIBLE("/2"),
    BAD("/3"),
    POOR("/4"),
    MEH("/5"),
    FAIR("/6"),
    GOOD("/7"),
    GREAT("/8"),
    SUPERB("/9"),
    TOTALLYNINJA("/10");

    private final String value;

    RatingsFilter(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
