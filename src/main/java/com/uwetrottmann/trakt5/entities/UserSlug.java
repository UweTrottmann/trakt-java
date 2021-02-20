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
     * @param userSlug A user slug as returned by the trakt API: {@link User.UserIds}.
     * @see #ME
     */
    public UserSlug(String userSlug) {
        if (userSlug == null) {
            throw new IllegalArgumentException("trakt user slug can not be null.");
        }
        userSlug = userSlug.trim();
        if (userSlug.length() == 0) {
            throw new IllegalArgumentException("trakt user slug can not be empty.");
        }
        this.userSlug = userSlug;
    }

    /**
     * Encodes the username returned from trakt so it is API compatible (currently replaces "." and spaces with "-").
     */
    @Nonnull
    public static UserSlug fromUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("trakt username can not be null.");
        }
        // trakt encodes some special chars in usernames
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
