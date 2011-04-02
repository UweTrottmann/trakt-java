package com.jakewharton.trakt.services;

import java.util.Date;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.CalendarDate;

public class CalendarService extends TraktApiService {
	/**
	 * Returns all shows premiering during the time period specified.
	 * 
	 * @param username You can get a username by browsing the website and
	 * looking at the URL when on a profile page.
	 * @return Builder instance.
	 */
	public PremieresBuilder premieres(String username) {
		return new PremieresBuilder(this, username);
	}
	
	public static final class PremieresBuilder extends TraktApiBuilder<List<CalendarDate>> {
		private static final String URI = "/calendar/premieres.json/{" + FIELD_API_KEY + "}/{" + FIELD_USERNAME + "}/{" + FIELD_DATE + "}/{" + FIELD_DAYS + "}";
		
		private PremieresBuilder(CalendarService service, String username) {
			super(service, new TypeToken<List<CalendarDate>>() {}, URI);
			
			this.field(FIELD_USERNAME, username);
		}
		
		/**
		 * Change the date range for the list.
		 * 
		 * @param date Start date for the calendar.
		 * @param days Number of days to display starting from the date.
		 * @return Builder instance.
		 */
		public PremieresBuilder from(Date date, int days) {
			this.field(FIELD_DATE, date);
			this.field(FIELD_DAYS, days);
			return this;
		}
	}
	
	/**
	 * Returns all shows premiering during the time period specified.
	 * 
	 * @param username You can get a username by browsing the website and
	 * looking at the URL when on a profile page.
	 * @return Builder instance.
	 */
	public ShowsBuilder shows(String username) {
		return new ShowsBuilder(this, username);
	}
	
	public static final class ShowsBuilder extends TraktApiBuilder<List<CalendarDate>> {
		private static final String URI = "/calendar/shows.json/{" + FIELD_API_KEY + "}/{" + FIELD_USERNAME + "}/{" + FIELD_DATE + "}/{" + FIELD_DAYS + "}";
		
		private ShowsBuilder(CalendarService service, String username) {
			super(service, new TypeToken<List<CalendarDate>>() {}, URI);
			
			this.field(FIELD_USERNAME, username);
		}
		
		/**
		 * Change the date range for the list.
		 * 
		 * @param date Start date for the calendar.
		 * @param days Number of days to display starting from the date.
		 * @return Builder instance.
		 */
		public ShowsBuilder from(Date date, int days) {
			this.field(FIELD_DATE, date);
			this.field(FIELD_DAYS, days);
			return this;
		}
	}
}
