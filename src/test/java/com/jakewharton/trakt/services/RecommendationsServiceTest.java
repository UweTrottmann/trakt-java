package com.jakewharton.trakt.services;

import java.util.List;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;

public class RecommendationsServiceTest extends BaseTestCase {
	public void test_movie() {
		List<Movie> movies = getManager().recommendationsService().movies().fire();
		assertNotNull("Result was null.", movies);
		
		if (!movies.isEmpty()) {
			assertNotNull("Result list item was null.", movies.get(0));
		}
	}
	
	public void test_tvShow() {
		List<TvShow> tvShows = getManager().recommendationsService().shows().fire();
		assertNotNull("Result was null.", tvShows);
		
		if (!tvShows.isEmpty()) {
			assertNotNull("Result list item was null.", tvShows.get(0));
		}
	}
}
