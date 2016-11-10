trakt-java
==========

A Java wrapper around the [trakt v2 API][1] using [retrofit 2][2].

Pull requests are welcome.

Trakt methods are grouped into service objects which can be centrally
managed by a `TraktV2` instance. It will act as a factory for
all of the services and will automatically initialize them with your
API key (OAuth client id) and optionally a given user access token.

## Usage

<a href="https://search.maven.org/#search%7Cga%7C1%7Ctrakt-java"><img src="https://img.shields.io/maven-central/v/com.uwetrottmann.trakt5/trakt-java.svg?style=flat-square"></a>

Add the following dependency to your Gradle project:

```groovy
compile 'com.uwetrottmann.trakt5:trakt-java:5.4.0'
```

Or for Maven:

```xml
<dependency>
  <groupId>com.uwetrottmann.trakt5</groupId>
  <artifactId>trakt-java</artifactId>
  <version>5.4.0</version>
</dependency>
```

Use like any other [retrofit2][2] based service. You only need to supply your [OAuth 2.0][3] credentials and optional user
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
} catch (IOException e) {
    // could not connect to trakt 
}
```

See test cases in `src/test/` for more examples.

## Use Proguard!
You likely will not use every method in this library, so it is probably useful to strip unused ones with Proguard.
Just apply the [Proguard rules for retrofit][4].

# License

Created by [Uwe Trottmann](http://uwetrottmann.com/contact).
Except where noted otherwise, released into the [public domain](UNLICENSE).
Do not just copy, make it better.


 [1]: http://docs.trakt.apiary.io/
 [2]: http://square.github.io/retrofit/
 [3]: https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2
 [4]: http://square.github.io/retrofit/#download
