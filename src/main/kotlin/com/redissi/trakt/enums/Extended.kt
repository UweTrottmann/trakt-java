package com.redissi.trakt.enums

/**
 * By default, all methods will return minimal info for movies, shows, episodes, and users. Minimal info is typically
 * all you need to match locally cached items and includes the title, year, and ids. However, you can request different
 * extended levels of information.
 */
enum class Extended(private val value: String) : TraktEnum {
    /** Complete info for an item.  */
    FULL("full"),
    /** Only works with sync watchedShows.  */
    NOSEASONS("noseasons"),
    /** Only works with seasons/summary  */
    EPISODES("episodes"),
    /** Only works with seasons/summary  */
    FULLEPISODES("full,episodes");

    override fun toString(): String {
        return value
    }

}