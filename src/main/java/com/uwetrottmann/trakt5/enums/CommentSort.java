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
public final class CommentSort implements TraktEnum {

    public static final CommentSort NEWEST = new CommentSort("newest");
    public static final CommentSort OLDEST = new CommentSort("oldest");
    public static final CommentSort LIKES = new CommentSort("likes");
    public static final CommentSort REPLIES = new CommentSort("replies");
    public static final CommentSort HIGHEST = new CommentSort("highest");
    public static final CommentSort LOWEST = new CommentSort("lowest");
    public static final CommentSort PLAYS = new CommentSort("plays");

    private final String value;

    private CommentSort(String value) {
        this.value = value;
    }

    /**
     * Creates a sort value that is not provided as a constant, for example one recently added to the Trakt API.
     *
     * @param value The sort value as expected by the Trakt API, leading and trailing whitespace is removed.
     * @throws IllegalArgumentException If the value is null or empty.
     */
    @Nonnull
    public static CommentSort of(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Comment sort value can not be null.");
        }
        value = value.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Comment sort value can not be empty.");
        }
        return new CommentSort(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentSort)) return false;
        return value.equals(((CommentSort) o).value);
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
