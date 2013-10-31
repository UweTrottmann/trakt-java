trakt-java
==========

A Java wrapper around the [trakt API][1] using [retrofit][2].

Remote services are grouped into local service objects which can be centrally
managed by a `Trakt` instance. It will act as a factory for
all of the services and will automatically initialize them with your
credentials and API key.

Usage
=====

Dependencies
------------

The [released jar][4] is built without dependencies, add these yourself as you see fit.
For example in a gradle.build file:
```
compile 'com.squareup.okhttp:okhttp:1.2.1' // not mandatory, but greatly recommended
compile 'com.squareup.retrofit:retrofit:1.2.2'
```

Calling endpoints
-----------------

    Trakt trakt = new Trakt();
    trakt.setAuthentication("username", "sha1_of_password");
    trakt.setApiKey("api_key");
    
    // Get trending shows on trakt
    List<TvShow> shows = getManager().showService().trending();
    for (TvShow show : shows) {
    	System.out.println("Title: " + show.title);
    }
    
    // Post an episode as seen
    Response response = getManager().showService().episodeSeen(new ShowService.Episodes(
        153021 // TVDb id of show, 1 // season, 1 // episode
    ));
    if (response != null && response.status == Status.SUCCESS) {
        System.out.println("Successfully checked into show.");
    }

See test cases in `src/test/` for more examples.

Original Implementation
=======================

* [trakt-java][3] by Jake Wharton - @JakeWharton

License
=======

    Copyright 2013 Uwe Trottmann

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




 [1]: http://trakt.tv/api-docs
 [2]: https://github.com/square/retrofit
 [3]: https://github.com/JakeWharton/trakt-java/
 [4]: https://github.com/UweTrottmann/trakt-java/releases
