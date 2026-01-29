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

import com.uwetrottmann.trakt5.enums.Rating;
import org.threeten.bp.OffsetDateTime;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SyncShow {

    public ShowIds ids;
    public List<SyncSeason> seasons;

    public OffsetDateTime collected_at;
    /**
     * Warning: Trakt is planning to only store and return minute-precision timestamps for watched_at. So seconds and
     * nanoseconds will always be zero. Using {@code .truncatedTo(ChronoUnit.MINUTES)} can be helpful.
     */
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;

    @Nonnull
    public SyncShow id(ShowIds id) {
        this.ids = id;
        return this;
    }

    @Nonnull
    public SyncShow seasons(List<SyncSeason> seasons) {
        this.seasons = seasons;
        return this;
    }

    @Nonnull
    public SyncShow seasons(SyncSeason season) {
        ArrayList<SyncSeason> list = new ArrayList<>(1);
        list.add(season);
        return seasons(list);
    }

    @Nonnull
    public SyncShow collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    @Nonnull
    public SyncShow watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    @Nonnull
    public SyncShow ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    @Nonnull
    public SyncShow rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
