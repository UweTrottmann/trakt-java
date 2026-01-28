# Changelog

## Next release

* Add paginated variants of `Sync.collectionMovies()`, `Sync.collectionShows()`, `Users.collectionMovies()` 
  and `Users.collectionShows()` with required `page` and `limit` parameters. The original methods without pagination 
  parameters are deprecated.

## 6.16.0 - 2024-11-07

* `TraktV2`: add `isUnauthorized(response)`, `isAccountLocked(response)` and `isNotVip(response)` helper methods.
* `TraktV2`: add `getPageCount(response)` and `getItemCount(response)` helper methods.

## 6.15.0 - 2024-08-30

* `TraktV2.notes()`: support to add, get, update and delete a note. [#152](https://github.com/UweTrottmann/trakt-java/pull/152)
* `TraktV2.users().notes(...)`: Support to get all notes for a user. [#152](https://github.com/UweTrottmann/trakt-java/pull/152)
* Add `Sync.removePlayback(id)`. [#151](https://github.com/UweTrottmann/trakt-java/pull/151)

## 6.14.0 - 2024-07-12

* LastActivities: Add missing fields. Thanks @ZelKami! [#148](https://github.com/UweTrottmann/trakt-java/pull/148)

## 6.13.0 - 2024-05-24

* Deprecated TV rage ID, it appears to be no longer used.
* Add variants of the comments methods that accept a `Cache-Control` header.
  This can be used to skip the local cache and get the latest comments from the server.
* Update retrofit [2.9.0 -> 2.11.0].
* Update okhttp [4.10.0 -> 4.12.0].
* Update gson [2.9.1 -> 2.11.0].
* Update threetenbp [1.6.1 -> 1.6.9].

## 6.12.0 - 2024-01-25

* Add method to reorder lists. Thanks @ZelKami! [#131](https://github.com/UweTrottmann/trakt-java/pull/131)
* Add limits to user settings.

## 6.11.2 - 2022-09-01

* Update bundled R8 rules to remove unused entity classes.

## 6.11.1 - 2022-08-25

* Add explicit Gson dependency [2.9.1]: transient dependency on 2.8.5 has a [security vulnerability](https://github.com/google/gson/pull/1991)
  and this library uses the Gson API explicitly.

## 6.11.0 - 2022-08-25

* Bundle R8 rules into library. Thanks @Goooler! [#127](https://github.com/UweTrottmann/trakt-java/pull/127)
* Update threetenbp [1.5.1 -> 1.6.1].
* Add explicit OkHttp dependency [4.10.0].

## 6.10.0 - 2021-08-05

* Change sync ratings endpoints to add pagination. Thanks @defhead!
* Update retrofit to 2.9.0, requires Java 8/Android 5. But most should be using that version anyhow (including latest okhttp).
* Update threetenbp [1.5.0 -> 1.5.1].

## 6.9.0 - 2021-02-29

* Add support for staging environment. See the new constructors for `SgTraktV2`. Thanks @srggsf!
* Annotate builder methods that return non-null.

## 6.8.0 - 2020-10-29

* Handle new HTTP 429 Too Many Requests response from Trakt. Note that if you use `TraktV2Interceptor` or its methods
  directly, it now is a regular interceptor (previously network interceptor) as it may retry requests. Details at
  https://trakt.docs.apiary.io/#introduction/rate-limiting.
* Add more `Audio` types. Via [#121](https://github.com/UweTrottmann/trakt-java/pull/121) from @riksmith, thanks!
* Update threetenbp [1.4.1 -> 1.5.0].

## 6.7.0 - 2020-09-11

* Switch `Authentication` methods to use API instead of website. This should make errors less likely.

## 6.6.0 - 2020-06-17

* Fix `TraktList` enum serialization (`ListPrivacy`, `SortHow` and `SortBy`).
* Added metadata to episodes and movies. Thanks @mlaggner!

## 6.5.0 - 2020-03-05

* Add support for [Trakt device authentication](https://trakt.docs.apiary.io/#reference/authentication-devices). [#112](https://github.com/UweTrottmann/trakt-java/pull/112) Thanks @samicemalone!
* Update progress endpoints to provide the last episode watched/collected. [#111](https://github.com/UweTrottmann/trakt-java/pull/111) Thanks @samicemalone!
* Update retrofit [2.6.1 -> 2.6.4]. Note: if your project is Java 8+ or Android 5+ manually depend on retrofit 2.7.x.
* Update threetenbp [1.4.0 -> 1.4.1].

## 6.4.0 - 2019-09-12

* Support scrobble methods and getting playback progress. Thanks @courville!

## 6.3.0 - 2019-08-22

* In `Recommendations` `movies` and `shows` now have a page and limit parameter. Thanks @chrisbanes!
* Update retrofit [2.5.0 -> 2.6.1].

## 6.2.0 - 2019-06-05

* Add `last_updated_at` returned by `sync().watchedMovies()` and `sync().watchedShows()`. Thanks @chrisbanes!
  https://github.com/UweTrottmann/trakt-java/pull/106
* Update threetenbp [1.3.7 -> 1.4.0].

## 6.1.0 - 2019-02-07

* Add `updated_at`, `likes` and `user_rating` to `Comment`. Thanks @MMauro94!
  https://github.com/UweTrottmann/trakt-java/pull/105
* For the `com.uwetrottmann.trakt5.entities` package return values and fields are now annotated nullable.

## 6.0.0 - 2018-11-28

* Produce Java 8 bytecode. For Android this requires Android Gradle Plugin 3.2.x or newer.
* For the root package (this specifically excludes entity classes) return values and fields are now non-null unless 
  otherwise annotated.
* `TraktV2.refreshAccessToken(refreshToken)` requires explicitly passing the refresh token.
* `buildAuthorizationUrl()`, `exchangeCodeForAccessToken()` and `refreshAccessToken()` now check for required values.

## 5.11.0 - 2018-11-23

* Support reordering list items with `Users.reorderListItems()`. Thanks @MMauro94!
* Support adding/removing people from lists with `SyncItems.people()`. Thanks @MMauro94!
* Add `sort_by`, `sort_how`, `created_at`, `user` to `TraktList`. Thanks @MMauro94!
* Add `id`, `rank` and `type` to `ListEntry`. Thanks @MMauro94!
* Add list activity to `LastActivities` response. Thanks @MMauro94!
* Update retrofit to 2.5.0.
* Update threetenbp to 1.3.7. Android projects should update ThreeTenABP to 1.1.1.

## 5.10.2 - 2018-09-20

* Change `SyncErrors.ids` from `List<Integer>` to `List<Long>`. Thanks @MMarco94!

## 5.10.1 - 2018-09-12

* Add `first_aired` to `Season`. Thanks @Larsg310!

## 5.10.0 - 2018-08-08

* Add `runtime` and `comment_count` to `Episode`. Thanks @KilianB!
* Update retrofit to 2.4.0.
* Update threetenbp to 1.3.6. Android projects should update ThreeTenABP to 1.1.0.

## 5.9.0 - 2018-06-21

* `SyncItems` uses `long` instead of `int` for history entry IDs. Thanks @chrisbanes!

## 5.8.1 - 2018-04-06

* Add `title` and `network` to `Season`. Thanks @chrisbanes!

## 5.8.0 - 2018-01-18

* Add related shows endpoint. Thanks @chrisbanes!

## 5.7.3 - 2017-09-21

* Support parsing generic errors of unsuccessful responses with `TraktV2.checkForTraktError(response)`.
* `TraktV2.checkForCheckinError(response)` now returns error instance with null value instead of throwing.

## 5.7.2 - 2017-09-20

* Properly serialize `OffsetDateTime`, fixes issues with `watched_at` and `collected_at` properties.
* Update retrofit to 2.3.0.

## 5.7.1 - 2017-04-12

* Hard-code UTF-8 charset to support all Android versions. `java.nio.charset.StandardCharsets` is only available on Android API 19+.

## 5.7.0 - 2017-04-06

* Use [ThreeTen](https://github.com/ThreeTen/threetenbp) to replace broken date parsing: `java.util.Date` replaced with `OffsetDateTime` and `LocalDate`.
* Note: to avoid [issues on Android](https://github.com/JakeWharton/ThreeTenABP#why-not-use-threetenbp) exclude the 
  ThreeTen dependency and include [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP) instead:

    ```groovy
    compile ('com.uwetrottmann.trakt5:trakt-java:<latest-version>') {
      exclude group: 'org.threeten', module: 'threetenbp'
    }
    compile 'com.jakewharton.threetenabp:threetenabp:<latest-version>'
    ```

## 5.6.0 - 2017-03-30

* Drop `Extended.DEFAULT_MIN` flag, pass `null` instead.
* Drop joda-time dependency: switched from `DateTime` to `java.util.Date`.
* Added new `TraktDate` to wrap `Date` for methods.
* Drop oltu dependency: build authorization URL ourselves.

## 5.5.1 - 2017-01-12

* Add `Extended` flag to new search methods.

## 5.5.0 - 2017-01-12

* Update to new search methods [#89](https://github.com/UweTrottmann/trakt-java/issues/89).
* Update joda-time to 2.9.7.

## 5.4.0 - 2016-11-10

* Remove `Username` in favor of `UserSlug`. The user slug is returned in the new `ids.slug` field of `User` . It is
  known to be URL safe. If you still only have a username you can use `UserSlug.fromUsername()` which will do its best
  to encode for trakt.

## 5.3.0 - 2016-11-09

* Removed images, except for user avatars, as trakt no longer returns them.
* Change host to api.trakt.tv (from api-v2launch.trakt.tv).
* Add `episodes` to `Season`, can be requested with season summary by passing `Extended.EPISODES`. Thanks @mlaggner!
* Add `ids` to `User`.
* Update joda-time to 2.9.5.

## 5.2.1 - 2016-08-18

* `HistoryEntry` ids can be `Long` (64 bit numbers). Thanks @mattkranzler5!

## 5.2.0 - 2016-07-31

* Add optional start and end date parameters to history methods. Thanks @mattkranzler5!

## 5.1.0 - 2016-07-14

* Added `stats` for movies, shows, seasons and episodes.
* Update retrofit to 2.1.0.

## 5.0.0 - 2016-06-09

* Update to `retrofit2`. Read about the most [notable changes and benefits](https://github.com/square/retrofit/blob/master/CHANGELOG.md#version-200-2016-03-11).
  You will have to make changes to your app, see the [README](https://github.com/UweTrottmann/trakt-java/blob/master/README.md#usage)
  for an example of the new code flow.
* Package name changed to `com.uwetrottmann.trakt5`. So you can keep using the old version while updating your code.
* Removed `OAuthUnauthorizedException` and `CheckinInProgressException`. You now have to handle authentication errors
  (check for `response.code() == 401`) and check-in errors (`TraktV2` now has `checkForCheckinError()`) yourself.
* Better integrated support for handling OAuth, removing the need for a dedicated HTTP client (removed `TraktHttpClient`).
* Better support for customizing `TraktV2`. You can now override `retrofit()`, `retrofitBuilder()`, `okHttpClient()` or 
  `setOkHttpClientDefaults()`.
* Add `noseasons` extended flag.
* Removed debug logging. You can easily add your own `HttpLoggingInterceptor` by overriding the new
  `setOkHttpClientDefaults()` in `TraktV2`. See `BaseTestCase` for an example.
* Update joda-time to 2.9.4.

## 4.6.2 - 2016-05-27

* Add `getOkHttpClient()` to `TraktV2` to allow supplying your own OkHttp client instance more easily. 

## 4.6.1 - 2016-05-06

* Fix `dismissMovie` and `dismissShow` endpoints ([#77](https://github.com/UweTrottmann/trakt-java/pull/77)). Thanks @yacsrk!
* Add tests for untested endpoints.

## 4.6.0 - 2016-05-06

* As a stop-gap solution use OkHttp 3 adapter with retrofit, update to OkHttp 3. Moving to retrofit2 soon.
* Drop user name from auth code request creator.

## 4.5.4 - 2016-01-22

* Revert oltu.oauth2.client to 1.0.0.

## 4.5.3 - 2016-01-22

* Update okhttp to 2.6.0.
* Update oltu.oauth2.client to 1.0.1.

## 4.5.2 - 2015-11-25

* Also trim leading and trailing white spaces of usernames.

## 4.5.1 - 2015-11-25

* Fix `/user` methods failing for usernames with spaces in them.
* Support for `shows` and `seasons` in `HistoryType`. Thanks @hrk!
* Update okhttp to 2.6.0.
* Update joda-time to 2.9.1.

## 4.5.0 - 2015-08-26

* Fix `/user` methods failing for usernames with periods in them. Usernames now have to be passed using
   `new Username("someuser")`, or for the currently authenticated user `Username.ME`. [#70](https://github.com/UweTrottmann/trakt-java/pull/70)
* Update okhttp to 2.5.0.

## 4.4.0 - 2015-08-17

* Allow checking in with show, season and episode without needing EpisodeIds. Thanks @samicemalone! [#62](https://github.com/UweTrottmann/trakt-java/pull/62)
* Added `last_watched_at` to BaseEpisode. Thanks @mlaggner! [#63](https://github.com/UweTrottmann/trakt-java/pull/63)
* Support new calendar methods. [#64](https://github.com/UweTrottmann/trakt-java/pull/64)
* Add shows collected progress method. Update watched progress with new params, properties. [#65](https://github.com/UweTrottmann/trakt-java/pull/65)
* Add watchlist methods to Users service. [#66](https://github.com/UweTrottmann/trakt-java/pull/66)
* Support new `/users/:username/history` filters. Added `history` method, removed `historyEpisodes` and `historyMovies`. [#67](https://github.com/UweTrottmann/trakt-java/pull/67)
* Support removing history items. [#68](https://github.com/UweTrottmann/trakt-java/pull/68)

## 4.3.1 - 2015-03-10

* Add `comment_count` to lists.
* Add `aired_episodes` to season.
* Add year parameter to search.
* Allow `null` year for show.
* Fix some tests.

## 4.3.0 - 2015-02-13

* Add helper methods to `TraktV2` to support trakt refresh tokens. Note: trakt access tokens expire after 90 days. Now
   that refresh tokens are supplied together with your access token, you can use them to refresh the access token
   without asking the user for authorization (if the user has not revoked authorization).
* Add missing properties to `LastActivities`, `Movie`, `Season`, `SeasonIds` and `Episode`. Thanks @florianmski!
* Support `/sync/watchlist/seasons`. Thanks @florianmski!

## 4.2.1 - 2015-02-05

* Switch to [new API endpoint](http://docs.trakt.apiary.io/#introduction/api-url) `https://api-v2launch.trakt.tv` from `https://api.trakt.tv`
* For `BaseShow`, `collected_at` is now `last_collected_at`, added `last_watched_at`.
* Tests allow empty cast character name.

## 4.2.0 - 2015-02-04

* Add [users/follow](http://docs.trakt.apiary.io/#reference/users/follow/follow-this-user),
   [users/unfollow](http://docs.trakt.apiary.io/#reference/users/follow/unfollow-this-user),
   [users/followers](http://docs.trakt.apiary.io/#reference/users/followers/get-followers),
   [users/followed](http://docs.trakt.apiary.io/#reference/users/following/get-following),
   [users/friends](http://docs.trakt.apiary.io/#reference/users/friends/get-friends).
* Use [new retrofit parameterized path and query annotations](https://github.com/UweTrottmann/trakt-java/issues/40).
* Update dependencies.

## 4.1.0 - 2015-01-13

* Add `last_watched_at` property to `BaseMovie` (e.g. for getting watched movies).
* Added [show watched progress](http://docs.trakt.apiary.io/#reference/shows/watched-progress/get-show-watched-progress) to the Shows service. Thanks @oprisnik!

## 4.0.3 - 2015-01-04

* Only throw `CheckinInProgressException` if using a `checkin` method.

## 4.0.2 - 2015-01-03

* Fix crash if check-in blocked error has broken body.

## 4.0.1 - 2015-01-03

* Use production URL for `TraktLink`.
* Add `type` property to `SearchResult`.

## 4.0.0 - 2014-12-30

* Support [the v2 API](http://docs.trakt.apiary.io/) (`TraktV2`).
* Removed the v1 API (`Trakt`). For easy upgrading, use trakt-java 3.4.0 for development. Then switch to 4.0.0 once finished.

## 3.4.0 - 2014-12-30

* Support the [trakt v2 API](http://docs.trakt.apiary.io/).
* This version includes the new v2 API (`TraktV2`) aside the old v1 API (`Trakt`), but marked deprecated. Use this for upgrading your code.

## 3.3.1 - 2014-08-12

* Fix `show/summary` not returning user data with default extended flag
   (URL had trailing `/`, causing redirect, stripping auth header).
* Remove trailing `/` on all other methods using either `Extended` or `Extended2` flag.

## 3.3.0 - 2014-08-12

* Easier customization of `RestAdapter`: set your own HTTP client or executor by overriding `newRestAdapterBuilder()`.
* Add `okhttp` and `okhttp-urlconnection` 2.0.0 as optional dependencies.
* Require Java 1.7.

## 3.2.1 - 2014-08-10

* Use HTTPS.
* Use okhttp 1.6.0.
* Update to [retrofit][1] 1.6.1.

## 3.2.0 - 2014-04-26

* Added `activity/friends`.
* Update to [retrofit][1] 1.5.0.

## 3.1.0 - 2014-03-15

* Added `user/progress/watched` and `user/progress/collected` endpoints. Thanks @samicemalone!
* Added `search/movies`. Thanks @porzione!
* Change fest dependency scope to `test`.

## 3.0.0 - 2014-02-13

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

## 2.0.1 - 2013-11-03

* Only create new `RestAdapter` instance when changing auth, API key or debug flag.
* Changed tests to use annotations, only setup trakt once per class.

## 2.0.0 - 2013-10-31

* More or less complete rewrite for retrofit backend. Not all previously supported endpoints are available yet (pull request to add new ones, it's easy!), but there are also new ones previously unsupported.
* `ServiceManager` is now just `Trakt`, endpoints do not require you to call `.fire()` anymore either.
* For now this is NOT published to a Maven repository, let me know if there is demand.
* Start testing with FEST.
* Use [retrofit][1] 1.2.2.

## 1.3.0 - 2011-12-13

* Added list and activity service.
* Movie and show services now have `checkin` and `cancelchecking` methods.
* All "getter" methods have been deprecated and instead the instance
   properties should be used directly.
* Transitioned methods which returned `MediaEntity` to use the more userful
   `ActivityInfo` type.

## 1.2.1 - 2011-09-15

* Add `dismissShow` and `dismissMovie` methods to the recommendations
   service.

## 1.2.0 - 2011-09-07

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

## 1.1.0 - 2011-07-17

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

## 1.0.0 - 2011-07-01

Initial version.

[1]: https://github.com/square/retrofit/blob/master/CHANGELOG.md