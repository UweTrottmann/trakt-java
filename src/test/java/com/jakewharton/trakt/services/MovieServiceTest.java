package com.jakewharton.trakt.services;

import java.util.List;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Images;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.Ratings;
import com.jakewharton.trakt.entities.Shout;
import com.jakewharton.trakt.entities.UserProfile;
import com.jakewharton.trakt.entities.MediaBase.Stats;

public class MovieServiceTest extends BaseTestCase {
	public void test_watchingNow() {
		List<UserProfile> users = getManager().movieService().watchingNow("tt1285016").fire();
		assertNotNull("Result was null.", users);
		
		if (!users.isEmpty()) {
			assertNotNull("User item was null.", users.get(0));
		}
	}
	
	public void test_shouts() {
		List<Shout> shouts = getManager().movieService().shouts("tt0079470").fire();
		assertNotNull("Result was null.", shouts);
		assertFalse("Shout list was empty.", shouts.isEmpty());
		assertNotNull("Shout item was null.", shouts.get(0));
	}
	
	public void test_trending() {
		List<Movie> trending = getManager().movieService().trending().fire();
		assertNotNull("Result was null.", trending);
		assertFalse("Trending list was empty.", trending.isEmpty());
		assertNotNull("Trending item was null.", trending.get(0));
	}
	
	public void test_summary() {
		Movie movie = getManager().movieService().summary("tt1285016").fire();
		assertNotNull("Result was null.", movie);
		assertEquals("Move title does not match.", "The Social Network", movie.title);
		assertNotNull("Movie year was null.", movie.year);
		assertEquals("Movie year does not match.", 2010, movie.year.intValue());
		assertNotNull("Movie URL was null.", movie.url);
		assertNotNull("Movie trailer was null.", movie.trailer);
		assertNotNull("Movie runtime was null.", movie.runtime);
		assertEquals("Movie runtime does not match.", 120, movie.runtime.intValue());
		assertNotNull("Movie tagline was null.", movie.tagline);
		assertNotNull("Movie overview was null.", movie.overview);
		assertEquals("Movie certification does not match.", "PG-13", movie.certification);
		assertEquals("Movie IMDB ID does not match.", "tt1285016", movie.imdbId);
		assertEquals("Movie TMDB ID does not match.", "37799", movie.tmdbId);
		
		Images movieImages = movie.images;
		assertNotNull("Movie images was null.", movieImages);
		assertNotNull("Movie poster image was null.", movieImages.poster);
		assertNotNull("Movie fan art image was null.", movieImages.fanart);
		
		List<UserProfile> topWatchers = movie.topWatchers;
		assertNotNull("Movie top watchers list was null.", topWatchers);
		assertFalse("Movie top watchers list was empty.", topWatchers.isEmpty());
		
		Ratings ratings = movie.ratings;
		assertNotNull("Movie ratings was null.", ratings);
		assertNotNull("Movie percentage rating was null.", ratings.percentage);
		assertNotNull("Movie votes rating was null.", ratings.votes);
		assertNotNull("Movie loved rating was null.", ratings.loved);
		assertNotNull("Movie hated rating was null.", ratings.hated);
		
		Stats stats = movie.stats;
		assertNotNull("Movie stats was null.", stats);
		assertNotNull("Movie watchers stats was null.", stats.watchers);
		assertNotNull("Movie plays stats was null.", stats.plays);
	}
	
	public void test_related() {
	    List<Movie> related = getManager().movieService().related("tt1285016").fire();
        assertNotNull("Result was null.", related);
        assertFalse("Trending list was empty.", related.isEmpty());
        assertNotNull("Trending item was null.", related.get(0));
	}
}
