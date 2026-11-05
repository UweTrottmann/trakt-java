/*
 * Copyright 2026 Uwe Trottmann
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

import javax.annotation.Nonnull;

/**
 * Sort order for comments.
 * <p>
 * Use the constants for values supported by this library, or {@link #of(String)} to pass a value that is not (yet)
 * supported.
 */
public final class CommentsSortBy implements TraktEnum {

    public static final CommentsSortBy NEWEST = new CommentsSortBy("newest");
    public static final CommentsSortBy OLDEST = new CommentsSortBy("oldest");
    public static final CommentsSortBy LIKES = new CommentsSortBy("likes");
    public static final CommentsSortBy REPLIES = new CommentsSortBy("replies");
    public static final CommentsSortBy HIGHEST = new CommentsSortBy("highest");
    public static final CommentsSortBy LOWEST = new CommentsSortBy("lowest");
    public static final CommentsSortBy PLAYS = new CommentsSortBy("plays");

    private final String value;

    private CommentsSortBy(String value) {
        this.value = value;
    }

    /**
     * Creates a sort value that is not provided as a constant, for example one recently added to the Trakt API.
     *
     * @param value The sort value as expected by the Trakt API, leading and trailing whitespace is removed.
     * @throws IllegalArgumentException If the value is null or empty.
     */
    @Nonnull
    public static CommentsSortBy of(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Comment sort value can not be null.");
        }
        value = value.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Comment sort value can not be empty.");
        }
        return new CommentsSortBy(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentsSortBy)) return false;
        return value.equals(((CommentsSortBy) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns the value sent to the API, used by Retrofit to build the request.
     */
    @Override
    public String toString() {
        return value;
    }

}
