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

import org.threeten.bp.OffsetDateTime;

public class HistoryEntry {

    public Long id;
    /**
     * Warning: Trakt is planning to only store and return minute-precision timestamps for watched_at. So seconds and
     * nanoseconds will always be zero.
     */
    public OffsetDateTime watched_at;
    public String action;
    public String type;

    public Episode episode;
    public Show show;

    public Movie movie;

}
