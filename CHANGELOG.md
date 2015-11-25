Change Log
==========

4.5.1 *(2015-11-25)*
--------------------

 * Fix `/user` methods failing for usernames with spaces in them.
 * Support for `shows` and `seasons` in `HistoryType`. Thanks @hrk!
 * Update okhttp to 2.6.0.
 * Update joda-time to 2.9.1.

4.5.0 *(2015-08-26)*
--------------------

 * Fix `/user` methods failing for usernames with periods in them. Usernames now have to be passed using
   `new Username("someuser")`, or for the currently authenticated user `Username.ME`. #70
 * Update okhttp to 2.5.0.

4.4.0 *(2015-08-17)*
--------------------

 * Allow checking in with show, season and episode without needing EpisodeIds. Thanks @samicemalone! #62
 * Added `last_watched_at` to BaseEpisode. Thanks @mlaggner! #63
 * Support new calendar methods. #64
 * Add shows collected progress method. Update watched progress with new params, properties. #65
 * Add watchlist methods to Users service. #66
 * Support new `/users/:username/history` filters. Added `history` method, removed `historyEpisodes` and `historyMovies`. #67
 * Support removing history items. #68

4.3.1 *(2015-03-10)*
--------------------

 * Add `comment_count` to lists.
 * Add `aired_episodes` to season.
 * Add year parameter to search.
 * Allow `null` year for show.
 * Fix some tests.

4.3.0 *(2015-02-13)*
--------------------

 * Add helper methods to `TraktV2` to support trakt refresh tokens. Note: trakt access tokens expire after 90 days. Now
   that refresh tokens are supplied together with your access token, you can use them to refresh the access token
   without asking the user for authorization (if the user has not revoked authorization).
 * Add missing properties to `LastActivities`, `Movie`, `Season`, `SeasonIds` and `Episode`. Thanks @florianmski!
 * Support `/sync/watchlist/seasons`. Thanks @florianmski!

4.2.1 *(2015-02-05)*
--------------------

 * Switch to [new API endpoint](http://docs.trakt.apiary.io/#introduction/api-url) `https://api-v2launch.trakt.tv` from `https://api.trakt.tv`
 * For `BaseShow`, `collected_at` is now `last_collected_at`, added `last_watched_at`.
 * Tests allow empty cast character name.

4.2.0 *(2015-02-04)*
--------------------

 * Add [users/follow](http://docs.trakt.apiary.io/#reference/users/follow/follow-this-user),
   [users/unfollow](http://docs.trakt.apiary.io/#reference/users/follow/unfollow-this-user),
   [users/followers](http://docs.trakt.apiary.io/#reference/users/followers/get-followers),
   [users/followed](http://docs.trakt.apiary.io/#reference/users/following/get-following),
   [users/friends](http://docs.trakt.apiary.io/#reference/users/friends/get-friends).
 * Use [new retrofit parameterized path and query annotations](https://github.com/UweTrottmann/trakt-java/issues/40).
 * Update dependencies.

4.1.0 *(2015-01-13)*
--------------------

 * Add `last_watched_at` property to `BaseMovie` (e.g. for getting watched movies).
 * Added [show watched progress](http://docs.trakt.apiary.io/#reference/shows/watched-progress/get-show-watched-progress) to the Shows service. Thanks @oprisnik!

4.0.3 *(2015-01-04)*
--------------------

 * Only throw `CheckinInProgressException` if using a `checkin` method.

4.0.2 *(2015-01-03)*
--------------------

 * Fix crash if check-in blocked error has broken body.

4.0.1 *(2015-01-03)*
--------------------

 * Use production URL for `TraktLink`.
 * Add `type` property to `SearchResult`.

4.0.0 *(2014-12-30)*
--------------------

 * Support [the v2 API](http://docs.trakt.apiary.io/) (`TraktV2`).
 * Removed the v1 API (`Trakt`). For easy upgrading, use trakt-java 3.4.0 for development. Then switch to 4.0.0 once finished.

3.4.0 *(2014-12-30)*
--------------------

 * Support the [trakt v2 API](http://docs.trakt.apiary.io/).
 * This version includes the new v2 API (`TraktV2`) aside the old v1 API (`Trakt`), but marked deprecated. Use this for upgrading your code.

3.3.1 *(2014-08-12)*
--------------------

 * Fix `show/summary` not returning user data with default extended flag
   (URL had trailing `/`, causing redirect, stripping auth header).
 * Remove trailing `/` on all other methods using either `Extended` or `Extended2` flag.

3.3.0 *(2014-08-12)*
--------------------

 * Easier customization of `RestAdapter`: set your own HTTP client or executor by overriding `newRestAdapterBuilder()`.
 * Add `okhttp` and `okhttp-urlconnection` 2.0.0 as optional dependencies.
 * Require Java 1.7.

3.2.1 *(2014-08-10)*
--------------------

 * Use HTTPS.
 * Use okhttp 1.6.0.
 * Update to [retrofit][1] 1.6.1.

3.2.0 *(2014-04-26)*
--------------------

 * Added `activity/friends`.
 * Update to [retrofit][1] 1.5.0.

3.1.0 *(2014-03-15)*
--------------------

 * Added `user/progress/watched` and `user/progress/collected` endpoints. Thanks @samicemalone!
 * Added `search/movies`. Thanks @porzione!
 * Change fest dependency scope to `test`.

3.0.0 *(2014-02-13)*
------------------------
There were some API breaking changes due to the introduction of a new `Extended` enum.
E.g. instead of calling `libraryShowsAllMinimum(username)` call `libraryShowsAll(username, Extended.MIN)`.
 * `ExtendedParam` replaced with `Extended`, adds a `DEFAULT` value.
 * `user/library/shows` endpoints add `Extended` param, `-Minimum` and `-Extended` versions are dropped.
 * Added `user/library/movies/` endpoints.
 * Added `movie/related` endpoint.
 * Added `show/summaries` and `movie/summaries`.
 * Added `user/calendar/shows` endpoint.
 * Add `last_updated` field.
 * Support `MovieComment` with TMDb id.
 * Use [retrofit][1] 1.4.1.


2.0.1 *(2013-11-03)*
------------------------

 * Only create new `RestAdapter` instance when changing auth, API key or debug flag.
 * Changed tests to use annotations, only setup trakt once per class.

2.0.0 *(2013-10-31)*
------------------------

 * More or less complete rewrite for retrofit backend. Not all previously supported endpoints are available yet (pull request to add new ones, it's easy!), but there are also new ones previously unsupported.
 * `ServiceManager` is now just `Trakt`, endpoints do not require you to call `.fire()` anymore either.
 * For now this is NOT published to a Maven repository, let me know if there is demand.
 * Start testing with FEST.
 * Use [retrofit][1] 1.2.2.


1.3.0 *(2011-12-13)*
-------------------

 * Added list and activity service.
 * Movie and show services now have `checkin` and `cancelchecking` methods.
 * All "getter" methods have been deprecated and instead the instance
   properties should be used directly.
 * Transitioned methods which returned `MediaEntity` to use the more userful
   `ActivityInfo` type.


1.2.1 *(2011-09-15)*
--------------------

 * Add `dismissShow` and `dismissMovie` methods to the recommendations
   service.


1.2.0 *(2011-09-07)*
--------------------

 * Add `print()` method which acts like `fire()` except writes relevant info
   to stdout rather than calling the remote API. This can be very useful for
   debugging just what your application is sending.
 * Add `setUseSsl` toggle to `ServiceManager` and the individual services to
   control whether or not the SSL API endpoint is used.
 * Add `TraktException` which is thrown when an exception was returned from
   the trakt servers.
 * Add genre service for listing movie and television genres.
 * Add filtering methods to the recommendations service for start and end year
   as well as genre.


1.1.0 *(2011-07-17)*
--------------------

 * Fix deserialization of fields whose names contained underscores.
 * `getEpisodes()` in `TvShowSeason` is now a complex type that can hold both
   a count, an episode number list, or a list of episode object.
 * Add new properties to `Movie` and `TvShow`.
 * Some dates are now a `java.util.Calendar` where the precision is to the
   seconds. Ones that remain `java.util.Date` should only be used for the date
   they represent, not the time.
 * The search service is now available from `ServiceManager`.
 * New `enum`s for gender, rating type, and day of the week.
 * __Full test suite implemented__.


1.0.0 *(2011-07-01)*
--------------------

Initial version.

[1]: https://github.com/square/retrofit/blob/master/CHANGELOG.md
