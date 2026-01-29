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

import java.util.List;

public class BaseShow {

    public Show show;

    /** collection, watched */
    public List<BaseSeason> seasons;

    /** collection */
    public OffsetDateTime last_collected_at;
    /** watchlist */
    public OffsetDateTime listed_at;
    /** watched */
    public Integer plays;
    /**
     * Warning: Trakt is planning to only store and return minute-precision timestamps for watched_at. So seconds and
     * nanoseconds will always be zero.
     */
    public OffsetDateTime last_watched_at;
    public OffsetDateTime last_updated_at;
    public OffsetDateTime reset_at;
    /** progress */
    public Integer aired;
    public Integer completed;
    public List<Season> hidden_seasons;
    public Episode next_episode;
    public Episode last_episode;

}
