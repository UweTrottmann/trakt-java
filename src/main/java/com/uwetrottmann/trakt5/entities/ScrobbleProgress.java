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

public class ScrobbleProgress extends GenericProgress {

    public String app_version;
    public String app_date;

    private ScrobbleProgress(String app_version, String app_date) {
        this.app_version = app_version;
        this.app_date = app_date;
    }

    public ScrobbleProgress(SyncEpisode episode, double progress, String app_version, String app_date) {
        this(app_version, app_date);
        this.episode = episode;
        this.progress = progress;
    }

    public ScrobbleProgress(SyncMovie movie, double progress, String app_version, String app_date) {
        this(app_version, app_date);
        this.movie = movie;
        this.progress = progress;
    }

}
