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

public class Settings {

    public User user;
    public Account account;
    public Connections connections;
    public SharingText sharing_text;
    public Limits limits;

    public static class Limits {

        public CountAndItemCount list;
        public ItemCount watchlist;
        public ItemCount favorites;
        public ItemCount recommendations;

        public static class ItemCount {
            public Integer item_count;
        }

        public static class CountAndItemCount extends ItemCount {
            public Integer count;
        }

    }
}
