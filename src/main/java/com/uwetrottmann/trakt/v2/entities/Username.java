package com.uwetrottmann.trakt.v2.entities;

public class Username {

    /**
     * Special user name for the current user (determined by auth data).
     */
    public static final Username ME = new Username("me");

    private String encodedUsername;

    /**
     * Encodes the username returned from trakt so it is API compatible (currently replaces "." with "-").
     *
     * @param username A username as returned by the trakt API.
     * @see #ME
     */
    public Username(String username) {
        if (username == null || username.length() == 0) {
            throw new IllegalArgumentException("trakt username can not be empty.");
        }
        this.encodedUsername = username.replace(".", "-");
    }

    @Override
    public String toString() {
        return encodedUsername;
    }
}
