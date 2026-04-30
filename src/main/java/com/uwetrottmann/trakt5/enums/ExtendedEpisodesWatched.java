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

package com.uwetrottmann.trakt5.enums;

/**
 * Extended info options for the watched episodes endpoint.
 */
public class ExtendedEpisodesWatched implements TraktEnum {

    /**
     * Return details, like overview and rating.
     *
     * @deprecated Starting 2026-05-30, this will be the default. See the
     * <a href="https://github.com/trakt/trakt-api/discussions/775">Upcoming API Changes: Watched Endpoints Pagination &
     * Extended Defaults</a> discussion for details and updates.
     */
    public static final ExtendedEpisodesWatched FULL = new ExtendedEpisodesWatched("full");

    private final String value;

    private ExtendedEpisodesWatched(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
