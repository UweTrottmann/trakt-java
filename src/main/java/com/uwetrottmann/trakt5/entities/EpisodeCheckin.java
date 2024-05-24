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

import javax.annotation.Nonnull;

public class EpisodeCheckin extends BaseCheckin {

    public Show show;
    public SyncEpisode episode;

    public static class Builder {

        private Show show;
        private SyncEpisode episode;
        protected ShareSettings sharing;
        protected String message;
        protected String venue_id;
        protected String venue_name;
        protected String app_version;
        protected String app_date;

        public Builder(SyncEpisode episode, String appVersion, String appDate) {
            if (episode == null) {
                throw new IllegalArgumentException("Episode must not be null");
            }
            this.episode = episode;
            this.app_version = appVersion;
            this.app_date = appDate;
        }

        @Nonnull
        public Builder show(Show show) {
            this.show = show;
            return this;
        }

        @Nonnull
        public Builder sharing(ShareSettings shareSettings) {
            this.sharing = shareSettings;
            return this;
        }

        @Nonnull
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        @Nonnull
        public Builder venueId(String venueId) {
            this.venue_id = venueId;
            return this;
        }

        @Nonnull
        public Builder venueName(String venueName) {
            this.venue_name = venueName;
            return this;
        }

        @Nonnull
        public EpisodeCheckin build() {
            EpisodeCheckin checkin = new EpisodeCheckin();
            checkin.show = show;
            checkin.episode = episode;
            checkin.sharing = sharing;
            checkin.message = message;
            checkin.venue_id = venue_id;
            checkin.venue_name = venue_name;
            checkin.app_date = app_date;
            checkin.app_version = app_version;
            return checkin;
        }
    }

}
