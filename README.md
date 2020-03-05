trakt-java
==========

A Java wrapper around the [Trakt v2 API](http://docs.trakt.apiary.io/) using [retrofit 2](https://square.github.io/retrofit/).

Pull requests are welcome.

Trakt methods are grouped into service objects which can be centrally
managed by a `TraktV2` instance. It will act as a factory for
all of the services and will automatically initialize them with your
API key (OAuth client id) and optionally a given user access token.

## Usage

<a href="https://search.maven.org/search?q=g:com.uwetrottmann.trakt5">Available on Maven Central</a>

Add the following dependency to your Gradle project:

```groovy
implementation 'com.uwetrottmann.trakt5:trakt-java:6.4.0'
```

Or for Maven:

```xml
<dependency>
  <groupId>com.uwetrottmann.trakt5</groupId>
  <artifactId>trakt-java</artifactId>
  <version>6.4.0</version>
</dependency>
```

### Android
This library ships Java 8 bytecode. This requires Android Gradle Plugin 3.2.x or newer.

This library depends on [threetenbp](https://github.com/ThreeTen/threetenbp). To avoid 
[issues on Android](https://github.com/JakeWharton/ThreeTenABP#why-not-use-threetenbp) you should exclude this 
dependency and include [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP) instead:

```groovy
implementation ('com.uwetrottmann.trakt5:trakt-java:<latest-version>') {
  exclude group: 'org.threeten', module: 'threetenbp'
}
implementation 'com.jakewharton.threetenabp:threetenabp:<latest-version>'
```

### Example

Use like any other retrofit2 based service. You only need to supply your 
[OAuth 2.0](https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2) credentials and optional user
OAuth access token obtained from trakt.

`TraktV2` provides some helper methods to handle the OAuth 2.0 flow.

```java
TraktV2 trakt = new TraktV2("api_key");
Shows traktShows = trakt.shows();
try {
    // Get trending shows
    Response<List<TrendingShow>> response = traktShows.trending(1, null, Extended.FULL).execute();
    if (response.isSuccessful()) {
        List<TrendingShow> shows = response.body();
        for (TrendingShow trending : shows) {
            System.out.println("Title: " + trending.show.title);
        }
    } else {
        if (response.code() == 401) {
            // authorization required, supply a valid OAuth access token
        } else {
            // the request failed for some other reason
        }
    }
} catch (Exception e) {
    // see execute() javadoc 
}
```

See test cases in `src/test/` for more examples and the [retrofit website](https://square.github.io/retrofit/) for configuration options.

## Use Proguard, R8!
It is likely not every method in this library is used, so it is probably useful to strip unused ones with Proguard.
Apply the [Proguard rules for retrofit](https://square.github.io/retrofit/#download).

Due to R8 being very eager in stripping unused fields even if they are set by a constructor, 
prevent entities from getting optimized. Obviously they also should not be obfuscated.
```proguard
-keep class com.uwetrottmann.trakt5.entities.** { *; }
-keep class com.uwetrottmann.trakt5.enums.** { *; }
```

## License
Created by [Uwe Trottmann](https://uwetrottmann.com).

See full [list of contributors](https://github.com/UweTrottmann/trakt-java/graphs/contributors).

Except where noted otherwise, released into the [public domain](UNLICENSE).
Do not just copy, make it better.
