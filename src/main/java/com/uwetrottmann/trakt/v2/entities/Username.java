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
        // trakt encodes some special chars in usernames
        // - points "." as a dash "-"
        // - spaces " " as a dash "-"
        // - multiple dashes are reduced to one
        this.encodedUsername = username.replace(".", "-").replace(" ", "-").replaceAll("(-)+", "-");
    }

    @Override
    public String toString() {
        return encodedUsername;
    }
}
