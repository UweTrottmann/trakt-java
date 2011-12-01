package com.jakewharton.trakt.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.lang.time.DateUtils;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.MediaEntity;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.Person;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.UserProfile;
import com.jakewharton.trakt.enumerations.Gender;

public class SearchServiceTest extends BaseTestCase {
	public void test_episodes() {
		List<MediaEntity> entities = getManager().searchService().episodes("warfare").fire();
		assertNotNull("Result was null.", entities);
		assertFalse("Episode entity list was empty.", entities.isEmpty());
		
		MediaEntity entity = entities.get(0);
		assertNotNull("Episode entity was null.", entity);
		assertNotNull("Show was null.", entity.show);
		assertNotNull("Episode was null.", entity.episode);
	}
	
	public void test_movies() {
		List<Movie> entities = getManager().searchService().movies("batman").fire();
		assertNotNull("Result was null.", entities);
		assertFalse("Result list was empty.", entities.isEmpty());
		assertNotNull("Result item was null.", entities.get(0));
	}
	
	public void test_people() {
	    @SuppressWarnings("deprecation")
		Date birthday = new Date(74, 0, 30);
		
		List<Person> people = getManager().searchService().people("christian bale").fire();
		assertNotNull("Result was null.", people);
		assertFalse("People list was empty.", people.isEmpty());
		
		Person person = people.get(0);
		assertNotNull("Person was null.", person);
		assertEquals("Person name does not match.", "Christian Bale", person.name);
		assertNotNull("Person URL was null.", person.url);
		assertNotNull("Person biography was null.", person.biography);
		assertTrue("Person birthday does not match.", DateUtils.isSameDay(person.birthday, birthday));
		assertEquals("Person birthplace does not match.", "Haverfordwest, Wales, UK", person.birthplace);
		assertNotNull("Person TMDB was null.", person.tmdbId);
		assertEquals("Person TMDB ID does not match.", 3894, person.tmdbId.intValue());
		assertNotNull("Person images was null.", person.images);
	}
	
	public void test_shows() {
		List<TvShow> shows = getManager().searchService().shows("big bang theory").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Show list was empty.", shows.isEmpty());
		assertNotNull("Show item was null.", shows.get(0));
	}
	
	public void test_users() {
		Calendar joined = Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));
		joined.set(2011, 2, 10, 16, 48, 28);
		joined.set(Calendar.MILLISECOND, 0);
		
		List<UserProfile> users = getManager().searchService().users("JakeWharton").fire();
		assertNotNull("Result was null.", users);
		assertFalse("User list was empty.", users.isEmpty());
		
		UserProfile user = users.get(0);
		assertNotNull("User item was null.", user);
		assertEquals("User username does not match.", "JakeWharton", user.username);
		assertNotNull("User protected was null.", user._protected);
		assertEquals("User protected does not match.", false, user._protected.booleanValue());
		assertEquals("User full name does not match.", "Jake Wharton", user.fullName);
		assertEquals("User gender does not match.", Gender.Male, user.gender);
		assertNotNull("User location was null.", user.location);
		assertTrue("User joined date does not match.", DateUtils.isSameInstant(joined, user.joined));
		assertNotNull("User avatar was null.", user.avatar);
		assertNotNull("User URL was null.", user.url);
	}
}
