package com.jakewharton.trakt.services;

import java.util.Date;
import java.util.List;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.CalendarDate;

public class CalendarServiceTest extends BaseTestCase {
	public void test_premieres() {
		List<CalendarDate> premieres = getManager().calendarService().premieres().date(new Date()).fire();
		assertNotNull("Result was null.", premieres);
		assertFalse("Result list was empty.", premieres.isEmpty());
	}
	
	public void test_shows() {
		List<CalendarDate> shows = getManager().calendarService().shows().date(new Date()).fire();
		assertNotNull("Result was null.", shows);
		assertFalse("Result list was empty.", shows.isEmpty());
	}
}
