trakt-java
==========

A Java wrapper around the [trakt v2 API][1] using [retrofit][2].

Trakt methods are grouped into service objects which can be centrally
managed by a `TraktV2` instance. It will act as a factory for
all of the services and will automatically initialize them with your
API key (OAuth client id) and optionally a given user access token.

Usage
=====
Add the following dependency to your Gradle project:

```groovy
compile 'com.uwetrottmann:trakt-java:4.2.0'
```

Or for Maven:

```xml
<dependency>
  <groupId>com.uwetrottmann</groupId>
  <artifactId>trakt-java</artifactId>
  <version>4.2.0</version>
</dependency>
```

Dependencies
------------
If you would rather use the [released jar][3], add dependencies as you see fit.
For example for Gradle:

```groovy
compile 'com.squareup.retrofit:retrofit:1.9.0'
compile 'com.squareup.okhttp:okhttp:2.2.0'
compile 'com.squareup.okhttp:okhttp-urlconnection:2.2.0'
compile 'org.apache.oltu.oauth2:org.apache.oltu.oauth2.client:1.0.0'
compile 'joda-time:joda-time:2.7'
```

Or for Maven:

```xml
<dependency>
    <groupId>com.squareup.retrofit</groupId>
    <artifactId>retrofit</artifactId>
    <version>1.9.0</version>
</dependency>
<dependency>
  <groupId>com.squareup.okhttp</groupId>
  <artifactId>okhttp</artifactId>
  <version>2.2.0</version>
</dependency>
<dependency>
  <groupId>com.squareup.okhttp</groupId>
  <artifactId>okhttp-urlconnection</artifactId>
  <version>2.2.0</version>
</dependency>
<dependency>
    <groupId>org.apache.oltu.oauth2</groupId>
    <artifactId>org.apache.oltu.oauth2.client</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>joda-time</groupId>
    <artifactId>joda-time</artifactId>
    <version>2.7</version>
</dependency>
```

Calling methods
-----------------

Calling methods is easy, but make sure to do it on a background thread
(there will be network activity). When using methods that require authentication,
make sure to set a valid [OAuth 2.0][4] access token obtained from trakt.

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

Original Implementation
=======================

* [trakt-java][5] by Jake Wharton

License
=======

    Copyright 2013-2015 Uwe Trottmann

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
 [3]: https://github.com/UweTrottmann/trakt-java/releases
 [4]: https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2
 [5]: https://github.com/JakeWharton/trakt-java/
