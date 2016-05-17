trakt-java
==========

A Java wrapper around the [trakt v2 API][1] using [retrofit][2].

Pull requests are welcome.

Trakt methods are grouped into service objects which can be centrally
managed by a `TraktV2` instance. It will act as a factory for
all of the services and will automatically initialize them with your
API key (OAuth client id) and optionally a given user access token.

## Usage

<a href="https://search.maven.org/#search%7Cga%7C1%7Ctrakt-java"><img src="https://img.shields.io/maven-central/v/com.uwetrottmann/trakt-java.svg?style=flat-square"></a>

Add the following dependency to your Gradle project:

```groovy
compile 'com.uwetrottmann:trakt-java:4.6.1'
```

Or for Maven:

```xml
<dependency>
  <groupId>com.uwetrottmann</groupId>
  <artifactId>trakt-java</artifactId>
  <version>4.6.1</version>
</dependency>
```

# Calling endpoints

Calling endpoints is easy, but make sure to do it on a background thread
(there will be network activity). When using endpoints that require authentication,
make sure to set a valid [OAuth 2.0][3] access token obtained from trakt.

`TraktV2` provides some helper methods to handle the OAuth 2.0 flow.

You also might want to look at how [retrofit][2] works and can be customized.

```java
// Setup service manager
TraktV2 trakt = new TraktV2();
trakt.setApiKey("api_key");
// trakt.setAccessToken("oauth_access_token"); // optional, see docs if required

// Create service instance
Shows traktShows = trakt.shows();

try {
    // Get trending shows
    List<TrendingShow> shows = traktShows.trending(1, 10, Extended.FULLIMAGES);
    for (TrendingShow trending : shows) {
        System.out.println("Title: " + trending.show.title);
    }
} catch (RetrofitError e) {
    // TODO: handle
}
```

See test cases in `src/test/` for more examples.

# License

    Copyright 2012 Uwe Trottmann
    Copyright 2011 Jake Wharton

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: http://docs.trakt.apiary.io/
 [2]: http://square.github.io/retrofit/
 [3]: https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2
