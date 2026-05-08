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

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Extended info options for the watched shows endpoint. Use {@link #of(ExtendedShowsWatched...)}
 * to combine multiple values into a comma-separated parameter.
 */
public class ExtendedShowsWatched implements TraktEnum {

    /**
     * Return show details, like overview and rating.
     *
     * @deprecated Starting 2026-05-30, this will be the default. See the
     * <a href="https://github.com/trakt/trakt-api/discussions/775">Upcoming API Changes: Watched Endpoints Pagination &
     * Extended Defaults</a> discussion for details and updates.
     */
    @Deprecated
    public static final ExtendedShowsWatched FULL = new ExtendedShowsWatched("full");

    /**
     * Exclude watched info for seasons and episodes.
     *
     * @deprecated Starting 2026-05-30, this will be the default. See the
     * <a href="https://github.com/trakt/trakt-api/discussions/775">Upcoming API Changes: Watched Endpoints Pagination &
     * Extended Defaults</a> discussion for details and updates.
     */
    @Deprecated
    public static final ExtendedShowsWatched NOSEASONS = new ExtendedShowsWatched("noseasons");

    /**
     * Include season progress information.
     * <p>
     * Note: until 2026-05-30 this is the default and won't have any effect. See the
     * <a href="https://github.com/trakt/trakt-api/discussions/775">Upcoming API Changes: Watched Endpoints Pagination &
     * Extended Defaults</a> discussion for details and updates.
     */
    public static final ExtendedShowsWatched PROGRESS = new ExtendedShowsWatched("progress");

    /**
     * Combine multiple extended values into a single comma-separated parameter.
     * <p>
     * Example: {@code ExtendedShowsWatched.of(FULL, NOSEASONS)} produces {@code "full,noseasons"}.
     */
    public static ExtendedShowsWatched of(ExtendedShowsWatched... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("At least one value is required.");
        }
        return new ExtendedShowsWatched(
                Arrays.stream(values).map(ExtendedShowsWatched::toString).collect(Collectors.joining(","))
        );
    }

    private final String value;

    private ExtendedShowsWatched(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
