Change Log
==========

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
 * Use retrofit 1.2.2.


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
