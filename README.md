trakt-java
============

A Java wrapper around the [Trakt RESTful API][1] and a simple DSL for easy
interaction.

Remote services are grouped into local service objects which can be centrally
managed by a `ServiceManager` instance. The manager will act as a factory for
all of the services and will automatically initialize them with your
credentials and API key.

Each service contains methods which correspond to a remote method. Each of
these methods instantiates a class that will allow for you to build the
parameters using the Java builder pattern.

Required remote method parameters will be arguments to the service method and
all of the methods in the returned builder are optional.

When fully assembled, you can trigger the remote execution by calling the
`fire()` method. This will return a native object which represents the result
of the execution. All returned objects are immutable and should be handled
as such.



Usage
=====

Quick example:

    ServiceManager manager = new ServiceManager();
    manager.setAuthentication("username", "sha1_of_password");
    manager.setApiKey("api_key");
    
    //Recently watched
    List<MediaEntity> watchedList =
    	manager.userService().watched("justin").fire();
    for (MediaEntity watched : watchedList) {
    	System.out.println("Type: " + watched.getType());
    	System.out.println("Date: " + watched.getWatched());
    	
    	switch (watched.getType()) {
    		case Movie:
    			System.out.println("Title: " + watched.getMovie().getTitle());
    			break;
    		case TvShow:
    			System.out.println("Title: " + watched.getShow().getTitle());
    			System.out.println("Ep.#: " + watched.getEpisode().getTitle());
    			break;
    	}
    	System.out.println();
    }
    
    //Upcoming show episodes
    Date today = new Date();
    List<CalendarDate> calendarShows =
    	manager.userService().calendarShows("justin").from(today, 3).fire();
    for (CalendarDate calendarDate : calendarShows) {
    	System.out.println(calendarDate.getDate());
    	System.out.println("-------------------------");
    	for (CalendarTvShowEpisode episode : calendarDate.getEpisodes()) {
    		System.out.print(episode.getShow().getTitle());
    		System.out.print(" - ");
    		System.out.print(episode.getEpisode().getSeason());
    		System.out.print("x");
    		System.out.print(episode.getEpisode().getNumber());
    		System.out.print(" - ");
    		System.out.println(episode.getEpisode().getTitle());
    	}
    	System.out.println();
    }

Real examples and tests to come soon.


Documentation
-------------

 * Javadocs are available at [jakewharton.github.com/trakt-java/][2].
 * Repository is hosted on [github.com/JakeWharton/trakt-java/][3].
 * Native API is documented on [trakt.tv/api-docs][1].



Developed By
============

* Jake Wharton - <jakewharton@gmail.com>


Contributors
------------

The overall package and class layout as well as approximately 50% of the code
in the `Trakt*` classes are based on [nabeelmukhtar's github-java-sdk
library][4].



License
=======

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




 [1]: http://trakt.tv/api-docs
 [2]: http://jakewharton.github.com/trakt-java/
 [3]: https://github.com/JakeWharton/trakt-java/
 [4]: https://github.com/nabeelmukhtar/github-java-sdk
