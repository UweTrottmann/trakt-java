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
compile 'com.uwetrottmann.trakt5:trakt-java:5.0.0'
```

Or for Maven:

```xml
<dependency>
  <groupId>com.uwetrottmann.trakt5</groupId>
  <artifactId>trakt-java</artifactId>
  <version>5.0.0</version>
</dependency>
```

Use like any other [retrofit2][2] based service. You only need to supply your [OAuth 2.0][3] credentials and optional user
OAuth access token obtained from trakt. For example:

`TraktV2` provides some helper methods to handle the OAuth 2.0 flow.

```java
TraktV2 trakt = new TraktV2("api_key");
Shows traktShows = trakt.shows();
try {
    // Get trending shows
    Response<List<TrendingShow>> response = traktShows.trending(1, null, Extended.FULLIMAGES).execute();
    List<TrendingShow> shows = traktShows.trending(1, 10, Extended.FULLIMAGES).execute();
    if (response.isSuccessful()) {
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
