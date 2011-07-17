Change Log
==========

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
