package com.jakewharton.trakt.services;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.TvShow;

public class ShowServiceTest extends BaseTestCase {
	public void test_trending() {
		List<TvShow> trending = getManager().showService().trending().fire();
		assertNotNull("Result was null.", trending);
		assertFalse("Trending list was empty.", trending.isEmpty());
		assertNotNull("Trending item was null.", trending.get(0));
	}
	
	@SuppressWarnings("deprecation")
	public void test_summary() {
		Date firstAired = new Date(110, 9, 31);
		
		TvShow show = getManager().showService().summary("the-walking-dead").extended().fire();
		assertNotNull("Show was null.", show);
		assertEquals("Show title does not match.", "The Walking Dead", show.getTitle());
		assertNotNull("Show year was null.", show.getYear());
		assertEquals("Show year does not match.", 2010, show.getYear().intValue());
		assertNotNull("Show URL was null.", show.getUrl());
		assertNotNull("Show first aired was null.", show.getFirstAired());
		assertTrue("Show first aired does not match.", DateUtils.isSameDay(firstAired, show.getFirstAired()));
		assertEquals("Show country does not match.", "United States", show.getCountry());
		assertNotNull("Show overview was null.", show.getOverview());
		assertNotNull("Show runtime was null.", show.getRuntime());
		assertEquals("Show runtime does not match.", 60, show.getRuntime().intValue());
		assertEquals("Show network does not match.", "AMC", show.getNetwork());
		assertEquals("Show air day does not match.", "Sunday", show.getAirDay());
		assertEquals("Show air time does not match.", "10:00pm", show.getAirTime());
		assertEquals("Show certification does not match.", "TV-MA", show.getCertification());
		assertEquals("Show IMDB ID does not match.", "tt1520211", show.getImdbId());
		assertEquals("Show TVDB ID does not match.", "153021", show.getTvdbId());
		assertEquals("Show TV Rage ID does not match.", "25056", show.getTvRageId());
	}
}
