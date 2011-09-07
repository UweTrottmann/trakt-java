package com.jakewharton.trakt.services;

import java.util.List;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Genre;

public class GenreServiceTest extends BaseTestCase {
	public void test_movies() {
		List<Genre> requests = getManager().genreService().movies().fire();
		assertNotNull("Result was null.", requests);
		
		if (!requests.isEmpty()) {
			assertNotNull("Request list item was null.", requests.get(0));
		}
	}
	
	public void test_shows() {
		List<Genre> all = getManager().genreService().shows().fire();
		assertNotNull("Result was null.", all);
		
		if (!all.isEmpty()) {
			assertNotNull("All list item was null.", all.get(0));
		}
	}
}