/*
 * Copyright © 2026 Uwe Trottmann <uwe@uwetrottmann.com>
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

package com.uwetrottmann.trakt5.services;


import com.uwetrottmann.trakt5.entities.HistoryEntry;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HistoryAssertions {

    public static void assertHistory(List<HistoryEntry> history) {
        for (HistoryEntry entry : history) {
            assertThat(entry.id).isGreaterThan(0);
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isNotEmpty();
            if ("episode".equals(entry.type)) {
                assertThat(entry.episode).isNotNull();
                assertThat(entry.show).isNotNull();
            } else if ("movie".equals(entry.type)) {
                assertThat(entry.movie).isNotNull();
            }
        }
    }

    public static void assertEpisodeHistory(List<HistoryEntry> history) {
        for (HistoryEntry entry : history) {
            assertThat(entry.id).isGreaterThan(0);
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isEqualTo("episode");
            assertThat(entry.episode).isNotNull();
            assertThat(entry.show).isNotNull();
//            System.out.println(
//                    "Episode watched at date: " + entry.watched_at + entry.watched_at.toInstant().toEpochMilli());
        }
    }

    public static void assertMovieHistory(List<HistoryEntry> history) {
        for (HistoryEntry entry : history) {
            assertThat(entry.watched_at).isNotNull();
            assertThat(entry.action).isNotEmpty();
            assertThat(entry.type).isEqualTo("movie");
            assertThat(entry.movie).isNotNull();
        }
    }

}
