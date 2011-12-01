package com.jakewharton.trakt.services;

import java.util.Calendar;
import java.util.List;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.CalendarDate;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.TvShowEpisode;
import com.jakewharton.trakt.entities.TvShowSeason;
import com.jakewharton.trakt.entities.UserProfile;
import com.jakewharton.trakt.entities.CalendarDate.CalendarTvShowEpisode;

public class UserServiceTest extends BaseTestCase {
	public void test_calendarShows() {
		Calendar d_2002_09_22 = Calendar.getInstance();
		d_2002_09_22.set(2004, 8, 22, 0, 0, 0);
		
		List<CalendarDate> dates = getManager().userService().calendarShows("JakeWharton").date(d_2002_09_22.getTime()).days(1).fire();
		assertNotNull("Result was null.", dates);
		assertFalse("Date list was empty.", dates.isEmpty());
		
		CalendarDate date = dates.get(0);
		assertNotNull("Date was null.", date);
		
		List<CalendarTvShowEpisode> dateEpisodes = date.episodes;
		assertNotNull("Date episode list was null.", dateEpisodes);
		assertFalse("Date episode list was empty.", dateEpisodes.isEmpty());
		
		CalendarTvShowEpisode dateEpisode = dateEpisodes.get(0);
		assertNotNull("Date episode was null.", dateEpisode);
		
		TvShowEpisode showEpisode = dateEpisode.episode;
		assertNotNull("Show episode was null.", showEpisode);
		assertNotNull("Show season was null.", showEpisode.season);
		assertEquals("Show season does not match.", 1, showEpisode.season.intValue());
		assertNotNull("Show number was null.", showEpisode.number);
		assertEquals("Show number does not match.", 1, showEpisode.number.intValue());
		assertEquals("Show title does not match.", "Pilot (1)", showEpisode.title);
		
		TvShow show = dateEpisode.show;
		assertNotNull("Show was null.", show);
		assertEquals("Show title does not match.", "Lost", show.title);
	}
	
	public void test_friends() {
		List<UserProfile> friends = getManager().userService().friends("JakeWharton").fire();
		assertNotNull("Result was null.", friends);
		assertFalse("Friends list was empty.", friends.isEmpty());
		assertNotNull("Friend was null.", friends.get(0));
	}
	
	public void test_libraryMoviesAll() {
		List<Movie> movies = getManager().userService().libraryMoviesAll("JakeWharton").fire();
		assertNotNull("Result was null.", movies);
		assertFalse("Movie list was empty.", movies.isEmpty());
		assertNotNull("Movie was null.", movies.get(0));
	}
	
	public void test_libraryMoviesCollection() {
		List<Movie> movies = getManager().userService().libraryMoviesCollection("JakeWharton").fire();
		assertNotNull("Result was null.", movies);
		assertFalse("Movie list was empty.", movies.isEmpty());
		assertNotNull("Movie was null.", movies.get(0));
	}
	
	public void test_libraryMoviesLoved() {
		List<Movie> movies = getManager().userService().libraryMoviesLoved("JakeWharton").fire();
		assertNotNull("Result was null.", movies);
		assertFalse("Movie list was empty.", movies.isEmpty());
		assertNotNull("Movie was null.", movies.get(0));
	}
	
	public void test_libraryMoviesHated() {
		List<Movie> movies = getManager().userService().libraryMoviesHated("JakeWharton").fire();
		assertNotNull("Result was null.", movies);
		assertFalse("Movie list was empty.", movies.isEmpty());
		assertNotNull("Movie was null.", movies.get(0));
	}
	
	public void test_libraryShowsAll() {
		List<TvShow> shows = getManager().userService().libraryShowsAll("JakeWharton").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Show list was empty.", shows.isEmpty());
		assertNotNull("Show was null.", shows.get(0));
	}
	
	public void test_libraryShowsCollection() {
		List<TvShow> shows = getManager().userService().libraryShowsCollection("JakeWharton").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Show list was empty.", shows.isEmpty());
		assertNotNull("Show was null.", shows.get(0));
	}
	
	public void test_libraryShowsLoved() {
		List<TvShow> shows = getManager().userService().libraryShowsLoved("JakeWharton").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Show list was empty.", shows.isEmpty());
		assertNotNull("Show was null.", shows.get(0));
	}
	
	public void test_libraryShowsHated() {
		List<TvShow> shows = getManager().userService().libraryShowsHated("JakeWharton").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Show list was empty.", shows.isEmpty());
		assertNotNull("Show was null.", shows.get(0));
	}
	
	public void test_libraryShowsWatched() {
		List<TvShow> shows = getManager().userService().libraryShowsWatched("JakeWharton").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Show list was empty.", shows.isEmpty());
		
		TvShow show = shows.get(0);
		assertNotNull("Show was null.", show);
		assertEquals("Show title does not match.", "South Park", show.title);
		assertNotNull("Show year was null.", show.year);
		assertEquals("Show year does not match.", 1997, show.year.intValue());
		assertNotNull("Show URL was null.", show.url);
		assertEquals("Show IMDB ID does not match.", "tt0121955", show.imdbId);
		assertEquals("Show TVDB ID does not match.", "75897", show.tvdbId);
		assertEquals("Show TV Rage ID does not match.", "5266", show.tvrageId);
		assertNotNull("Show images was null.", show.images);
		assertNotNull("Show poster image was null.", show.images.poster);
		assertNotNull("Show fanart image was null.", show.images.fanart);
		
		List<TvShowSeason> seasons = show.seasons;
		assertNotNull("Show seasons list was null.", seasons);
		assertFalse("Show seasons list was empty.", seasons.isEmpty());
		
		TvShowSeason season = seasons.get(0);
		assertNotNull("Show first season was null.", season);
		assertNotNull("Show season number was null.", season.season);
		
		TvShowSeason.Episodes seasonEpisodes = season.episodes;
		assertNotNull("Show episodes was null.", seasonEpisodes);
		assertNotNull("Show episodes list was null.", seasonEpisodes.numbers);
		assertFalse("Show episodes list was empty.", seasonEpisodes.numbers.isEmpty());
	}
	
	public void test_lists() {
		List<com.jakewharton.trakt.entities.List> lists = getManager().userService().lists("justin").fire();
		assertNotNull("Result was null.", lists);
	}
	
	public void test_profile() {
		UserProfile user = getManager().userService().profile("JakeWharton").fire();
		assertNotNull("Result was null.", user);
		//TODO
	}
	
	public void test_watching() {
		//This may return null. Only testing for an unchecked exception.
		getManager().userService().watching("justin").fire();
	}
	
	public void test_watchlistEpisodes() {
		List<TvShow> shows = getManager().userService().watchlistEpisodes("justin").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Show list was empty.", shows.isEmpty());
		assertNotNull("Show item was null.", shows.get(0));
	}
	
	public void test_watchlistMovies() {
		List<Movie> shows = getManager().userService().watchlistMovies("justin").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Movie list was empty.", shows.isEmpty());
		assertNotNull("Movie item was null.", shows.get(0));
	}
	
	public void test_watchlistShows() {
		List<TvShow> shows = getManager().userService().watchlistShows("justin").fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Show list was empty.", shows.isEmpty());
		assertNotNull("Show item was null.", shows.get(0));
	}
}
