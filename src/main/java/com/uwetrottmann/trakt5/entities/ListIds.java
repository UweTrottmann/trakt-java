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

public class ListIds {

    public Integer trakt;
    public String slug;

    @Nonnull
    public static ListIds trakt(int trakt) {
        ListIds ids = new ListIds();
        ids.trakt = trakt;
        return ids;
    }

    @Nonnull
    public static ListIds slug(String slug) {
        ListIds ids = new ListIds();
        ids.slug = slug;
        return ids;
    }

}
