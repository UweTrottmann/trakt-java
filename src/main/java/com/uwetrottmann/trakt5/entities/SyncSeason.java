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

public class SyncSeason {

    public Integer number;
    public List<SyncEpisode> episodes;

    public OffsetDateTime collected_at;
    /**
     * Warning: Trakt only stores and returns minute-precision timestamps for the watched at time. So seconds and
     * nanoseconds will always be zero. Using {@code .truncatedTo(ChronoUnit.MINUTES)} can be helpful.
     */
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;

    @Nonnull
    public SyncSeason number(int number) {
        this.number = number;
        return this;
    }

    @Nonnull
    public SyncSeason episodes(List<SyncEpisode> episodes) {
        this.episodes = episodes;
        return this;
    }

    @Nonnull
    public SyncSeason episodes(SyncEpisode episode) {
        ArrayList<SyncEpisode> list = new ArrayList<>(1);
        list.add(episode);
        return episodes(list);
    }

    @Nonnull
    public SyncSeason collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    /**
     * Warning: Trakt only stores and returns minute-precision timestamps for the watched at time. So seconds and
     * nanoseconds will always be zero. Using {@code .truncatedTo(ChronoUnit.MINUTES)} can be helpful.
     */
    @Nonnull
    public SyncSeason watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    @Nonnull
    public SyncSeason ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    @Nonnull
    public SyncSeason rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
