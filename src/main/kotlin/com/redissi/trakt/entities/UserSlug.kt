package com.redissi.trakt.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserSlug
/**
 * User slug to pass to the API, performs some simple null and empty checks.
 *
 * @param userSlug A user slug as returned by the trakt API: [User.UserIds].
 * @see .ME
 */
constructor(userSlug: String) {

    val userSlug: String

    override fun toString(): String {
        return userSlug
    }

    companion object {
        /**
         * Special user slug for the current user (determined by auth data).
         */
        @JvmStatic
        val ME = UserSlug("me")

        /**
         * Encodes the username returned from trakt so it is API compatible (currently replaces "." and spaces with "-").
         */
        @JvmStatic
        fun fromUsername(username: String?): UserSlug {
            requireNotNull(username) { "trakt username can not be null." }
            // trakt encodes some special chars in usernames
            // - points "." as a dash "-"
            // - spaces " " as a dash "-"
            // - multiple dashes are reduced to one
            val slug = username.replace(".", "-")
                .replace(" ", "-")
                .replace("(-)+".toRegex(), "-")
            return UserSlug(slug)
        }
    }

    init {
        var userSlug1 = userSlug
        userSlug1 = userSlug1.trim { it <= ' ' }
        require(userSlug1.isNotEmpty()) { "trakt user slug can not be empty." }
        this.userSlug = userSlug1
    }
}