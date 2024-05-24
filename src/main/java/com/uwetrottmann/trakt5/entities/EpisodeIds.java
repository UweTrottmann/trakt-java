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

public class EpisodeIds extends BaseIds {

    public Integer tvdb;
    /**
     * @deprecated This appears to be no longer supported.
     */
    @Deprecated
    public Integer tvrage;

    @Nonnull
    public static EpisodeIds trakt(int trakt) {
        EpisodeIds ids = new EpisodeIds();
        ids.trakt = trakt;
        return ids;
    }

    @Nonnull
    public static EpisodeIds imdb(String imdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.imdb = imdb;
        return ids;
    }

    @Nonnull
    public static EpisodeIds tmdb(int tmdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.tmdb = tmdb;
        return ids;
    }

    @Nonnull
    public static EpisodeIds tvdb(int tvdb) {
        EpisodeIds ids = new EpisodeIds();
        ids.tvdb = tvdb;
        return ids;
    }

    /**
     * @deprecated This appears to be no longer supported.
     */
    @Deprecated
    @Nonnull
    public static EpisodeIds tvrage(int tvrage) {
        EpisodeIds ids = new EpisodeIds();
        ids.tvrage = tvrage;
        return ids;
    }

}
