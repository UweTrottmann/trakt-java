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

public class LastActivities {

    public OffsetDateTime all;
    public LastActivityMore movies;
    public LastActivityMore episodes;
    public LastActivity shows;
    public LastActivity seasons;
    public LastActivityComments comments;
    public ListsLastActivity lists;
    public LastActivityUpdated watchlist;
    public LastActivityUpdated favorites;
    public LastActivityAccount account;
    public LastActivityUpdated saved_filters;
    public LastActivityUpdated notes;

}
