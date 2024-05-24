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

public class PersonIds extends BaseIds {

    public String slug;
    /**
     * @deprecated This appears to be no longer supported.
     */
    @Deprecated
    public String tvrage;

    @Nonnull
    public static PersonIds trakt(int trakt) {
        PersonIds ids = new PersonIds();
        ids.trakt = trakt;
        return ids;
    }

    @Nonnull
    public static PersonIds imdb(String imdb) {
        PersonIds ids = new PersonIds();
        ids.imdb = imdb;
        return ids;
    }

    @Nonnull
    public static PersonIds tmdb(int tmdb) {
        PersonIds ids = new PersonIds();
        ids.tmdb = tmdb;
        return ids;
    }

    @Nonnull
    public static PersonIds slug(String slug) {
        PersonIds ids = new PersonIds();
        ids.slug = slug;
        return ids;
    }
}
