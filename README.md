# trakt-java

**[Pull requests](CONTRIBUTING.md) (e.g. support for more API endpoints, bug fixes) are welcome!**

An (unofficial) Java library to use the [Trakt v2 API](https://trakt.docs.apiary.io/) built with [retrofit 2](https://square.github.io/retrofit/).

Trakt API methods are grouped into [service objects](src/main/java/com/uwetrottmann/trakt5/services) 
which can be centrally managed by a [TraktV2](src/main/java/com/uwetrottmann/trakt5/TraktV2.java) instance.
It will act as a factory for all the services and will automatically initialize them with the given
API key (OAuth client id) and optionally a given user access token.

## Usage

<a href="https://central.sonatype.com/search?q=g:com.uwetrottmann.trakt5">Available on Maven Central</a>

[Change Log](CHANGELOG.md)

Add the following dependency to your Gradle project:

```kotlin
implementation("com.uwetrottmann.trakt5:trakt-java:6.15.0")
```

Or for Maven:

```xml
<dependency>
  <groupId>com.uwetrottmann.trakt5</groupId>
  <artifactId>trakt-java</artifactId>
  <version>6.15.0</version>
</dependency>
```

### Android

This library ships Java 8 bytecode. This requires Android Gradle Plugin 3.2.x or newer.

This library depends on [threetenbp](https://github.com/ThreeTen/threetenbp). To avoid 
[issues on Android](https://github.com/JakeWharton/ThreeTenABP#why-not-use-threetenbp) exclude the 
dependency and include [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP) instead:

```kotlin
implementation("com.uwetrottmann.trakt5:trakt-java:<latest-version>") {
  exclude(group = "org.threeten", module = "threetenbp") // using ThreeTenABP instead
}
implementation("com.jakewharton.threetenabp:threetenabp:<latest-version>")
```

### Example

Use like any other retrofit2 based service. At least a Trakt API key, which for Trakt is currently the same as the 
[OAuth 2.0](https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2) client ID,
has to be supplied. Optionally, a user OAuth `accessToken(token)` can be supplied.

See the [TraktV2](src/main/java/com/uwetrottmann/trakt5/TraktV2.java) class for some helper methods to handle the OAuth flow.

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
        if (TraktV2.isUnauthorized(response)) {
            // authorization required, supply a valid OAuth access token
        } else {
            // the request failed for some other reason
        }
    }
} catch (Exception e) {
    // see execute() API docs 
}
```

**For Kotlin**, to make a `Call` suspend use retrofit's `awaitResponse()` instead of `execute()`:

```kotlin
try {
    val response = traktShows.trending(1, null, Extended.FULL).awaitResponse()
    if (response.isSuccessful) {
        val shows = response.body()
    }
} catch (e: Exception) {
    // see execute() API docs
}
```

See test cases in `src/test/` for more examples and the [retrofit website](https://square.github.io/retrofit/) for configuration options.

## Proguard / R8

It is likely not every method in this library is used, so it is probably useful to strip unused ones with Proguard.
Apply the [Proguard rules for retrofit](https://square.github.io/retrofit/#download).

The specific rules for this library are [already bundled](src/main/resources/META-INF/proguard/trakt-java.pro) into the
release which can be interpreted by R8 automatically, ProGuard users must manually add the rules.

## License

This work by [Uwe Trottmann](https://uwetrottmann.com) is licensed under the [Apache License 2.0](LICENSE.txt).

[Contributors](https://github.com/UweTrottmann/trakt-java/graphs/contributors) and changes are tracked by Git.

Do not just copy, make it better.
