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
import java.util.ArrayList;
import java.util.List;

public class SyncItems {

    public List<SyncMovie> movies;
    public List<SyncShow> shows;
    public List<SyncEpisode> episodes;
    public List<SyncPerson> people;

    /**
     * Only supported for removing specific history items.
     */
    public List<Long> ids;

    @Nonnull
    public SyncItems movies(SyncMovie movie) {
        ArrayList<SyncMovie> list = new ArrayList<>(1);
        list.add(movie);
        return movies(list);
    }

    @Nonnull
    public SyncItems movies(List<SyncMovie> movies) {
        this.movies = movies;
        return this;
    }

    @Nonnull
    public SyncItems shows(SyncShow show) {
        ArrayList<SyncShow> list = new ArrayList<>(1);
        list.add(show);
        return shows(list);
    }

    @Nonnull
    public SyncItems shows(List<SyncShow> shows) {
        this.shows = shows;
        return this;
    }

    @Nonnull
    public SyncItems episodes(SyncEpisode episode) {
        ArrayList<SyncEpisode> list = new ArrayList<>(1);
        list.add(episode);
        return episodes(list);
    }

    @Nonnull
    public SyncItems episodes(List<SyncEpisode> episodes) {
        this.episodes = episodes;
        return this;
    }

    @Nonnull
    public SyncItems people(SyncPerson person) {
        ArrayList<SyncPerson> list = new ArrayList<>(1);
        list.add(person);
        return people(list);
    }

    @Nonnull
    public SyncItems people(List<SyncPerson> people) {
        this.people = people;
        return this;
    }

    /**
     * @deprecated use {@link #ids(long)} instead
     */
    @Deprecated
    @Nonnull
    public SyncItems ids(int id) {
        return ids((long) id);
    }

    /**
     * History id to be removed.
     */
    @Nonnull
    public SyncItems ids(long id) {
        ArrayList<Long> list = new ArrayList<>(1);
        list.add(id);
        return ids(list);
    }

    /**
     * History ids to be removed.
     */
    @Nonnull
    public SyncItems ids(List<Long> ids) {
        this.ids = ids;
        return this;
    }
}
