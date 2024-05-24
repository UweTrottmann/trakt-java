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

public class ShowIds extends BaseIds {

    public String slug;
    public Integer tvdb;
    public Integer tvrage;

    @Nonnull
    public static ShowIds trakt(int trakt) {
        ShowIds ids = new ShowIds();
        ids.trakt = trakt;
        return ids;
    }

    @Nonnull
    public static ShowIds imdb(String imdb) {
        ShowIds ids = new ShowIds();
        ids.imdb = imdb;
        return ids;
    }

    @Nonnull
    public static ShowIds tmdb(int tmdb) {
        ShowIds ids = new ShowIds();
        ids.tmdb = tmdb;
        return ids;
    }

    @Nonnull
    public static ShowIds slug(String slug) {
        ShowIds ids = new ShowIds();
        ids.slug = slug;
        return ids;
    }

    @Nonnull
    public static ShowIds tvdb(int tvdb) {
        ShowIds ids = new ShowIds();
        ids.tvdb = tvdb;
        return ids;
    }

    @Nonnull
    public static ShowIds tvrage(int tvrage) {
        ShowIds ids = new ShowIds();
        ids.tvrage = tvrage;
        return ids;
    }

}
