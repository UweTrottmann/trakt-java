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

public class UserSlug {

    /**
     * Special user slug for the current user (determined by auth data).
     */
    public static final UserSlug ME = new UserSlug("me");

    private final String userSlug;

    /**
     * User slug to pass to the API, performs some simple null and empty checks.
     *
     * @param userSlug A user slug as returned by the Trakt API: {@link User.UserIds}.
     * @see #ME
     */
    public UserSlug(String userSlug) {
        if (userSlug == null) {
            throw new IllegalArgumentException("Trakt user slug can not be null.");
        }
        userSlug = userSlug.trim();
        if (userSlug.length() == 0) {
            throw new IllegalArgumentException("Trakt user slug can not be empty.");
        }
        this.userSlug = userSlug;
    }

    /**
     * Encodes the username returned from Trakt so it is API compatible (currently replaces "." and spaces with "-").
     */
    @Nonnull
    public static UserSlug fromUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Trakt username can not be null.");
        }
        // Trakt encodes some special chars in usernames
        // - points "." as a dash "-"
        // - spaces " " as a dash "-"
        // - multiple dashes are reduced to one
        String slug = username.replace(".", "-").replace(" ", "-").replaceAll("(-)+", "-");
        return new UserSlug(slug);
    }

    @Override
    public String toString() {
        return userSlug;
    }
}
