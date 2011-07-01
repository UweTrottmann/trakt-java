package com.jakewharton.trakt.services;

import java.util.Calendar;
import java.util.List;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.CalendarDate;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.TvShowEpisode;
import com.jakewharton.trakt.entities.CalendarDate.CalendarTvShowEpisode;

public class UserServiceTest extends BaseTestCase {
	public void test_calendarShows() {
		Calendar d_2002_09_22 = Calendar.getInstance();
		d_2002_09_22.set(2004, 8, 22, 0, 0, 0);
		
		List<CalendarDate> dates = getManager().userService().calendarShows("JakeWharton").date(d_2002_09_22.getTime()).days(1).fire();
		assertNotNull("Result list was null", dates);
		assertFalse("Result list was empty.", dates.isEmpty());
		
		CalendarDate date = dates.get(0);
		assertNotNull("First result was null.", date);
		
		List<CalendarTvShowEpisode> dateEpisodes = date.getEpisodes();
		assertNotNull("Date episode list was null.", dateEpisodes);
		assertFalse("Date episode list was empty.", dateEpisodes.isEmpty());
		
		CalendarTvShowEpisode dateEpisode = dateEpisodes.get(0);
		assertNotNull("Date episode was null.", dateEpisode);
		
		TvShowEpisode showEpisode = dateEpisode.getEpisode();
		assertNotNull("Show episode was null.", showEpisode);
		assertNotNull("Show season was null.", showEpisode.getSeason());
		assertEquals("Show season does not match.", showEpisode.getSeason().intValue(), 1);
		assertNotNull("Show number was null.", showEpisode.getNumber());
		assertEquals("Show number does not match.", showEpisode.getNumber().intValue(), 1);
		assertNotNull("Show title was null.", showEpisode.getTitle());
		assertEquals("Show title does not match.", showEpisode.getTitle(), "Pilot (1)");
		
		TvShow show = dateEpisode.getShow();
		assertNotNull("Show was null.", show);
		assertNotNull("Show title was null.", show.getTitle());
		assertEquals("Show title does not match.", show.getTitle(), "Lost");
	}
}
