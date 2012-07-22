package com.jakewharton.trakt.services;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Shout;
import com.jakewharton.trakt.entities.TvEntity;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.TvShowEpisode;
import com.jakewharton.trakt.entities.TvShowSeason;
import com.jakewharton.trakt.entities.UserProfile;
import com.jakewharton.trakt.enumerations.DayOfTheWeek;

public class ShowServiceTest extends BaseTestCase {
	public void test_episodeShouts() {
		List<Shout> shouts = getManager().showService().episodeShouts("the-walking-dead", 1, 1).fire();
		assertNotNull("Result was null.", shouts);
		assertFalse("Shout list was empty.", shouts.isEmpty());
		assertNotNull("Shout item was null.", shouts.get(0));
	}
	
	public void test_episodeSummary() {
		TvEntity entity = getManager().showService().episodeSummary("the-league", 1, 1).fire();
		assertNotNull("Result was null.", entity);
		
		TvShow show = entity.show;
		assertNotNull("Show was null.", show);
		assertEquals("Show title does not match.", "The League", show.title);
		assertNotNull("Show year was null.", show.year);
		assertEquals("Show year does not match.", 2009, show.year.intValue());
		assertNotNull("Show URL was null.", show.url);
		//TODO first aired
		assertEquals("Show country does not match.", "United States", show.country);
		assertNotNull("Show overview was null.", show.overview);
		assertNotNull("Show runtime was null.", show.runtime);
		assertEquals("Show runtime does not match.", 30, show.runtime.intValue());
		assertEquals("Show network does not mtach.", "FX", show.network);
		assertEquals("Show air day does not match.", DayOfTheWeek.Thursday, show.airDay);
		assertEquals("Show air time does not mtach.", "10:30pm", show.airTime);
		assertEquals("Show certification does not match.", "TV-MA", show.certification);
		assertEquals("Show IMDB ID does not match.", "tt1480684", show.imdbId);
		assertEquals("Show TVDB ID does not match.", "114701", show.tvdbId);
		assertEquals("Show TV Rage ID does not match.", "24173", show.tvrageId);
		assertNotNull("Show images was null.", show.images); //TODO own test cases
		assertNotNull("Show ratings was null.", show.ratings); //TODO own test cases
		//TODO rating
		assertNotNull("Show in watchlist boolean was null.", show.inWatchlist);
		assertEquals("Show in watchlist boolean does not match.", false, show.inWatchlist.booleanValue());
		
		TvShowEpisode episode = entity.episode;
		assertNotNull("Episode was null.");
		assertNotNull("Episode season was null.", episode.season);
		assertEquals("Episode season does not match.", 1, episode.season.intValue());
		assertNotNull("Episode number was null.", episode.number);
		assertEquals("Episode number does not match.", 1, episode.number.intValue());
		assertEquals("Episode title does not match.", "The Draft", episode.title);
		assertNotNull("Episode overview was null.", episode.overview);
		assertNotNull("Episode URL was null.", episode.url);
		//TODO first aired
		assertNotNull("Episode images was null.", episode.images); //TODO own test cases
		assertNotNull("Episode ratings was null.", episode.ratings); //TODO own test cases
		assertNotNull("Episode watched boolean was null.", episode.watched);
		assertEquals("Episode watched boolean does not match.", false, episode.watched.booleanValue());
		assertNotNull("Episode plays was null.", episode.plays);
		assertEquals("Episode plays does not match.", 0, episode.plays.intValue());
		//TODO rating
		assertNotNull("Episode in watchlist boolean was null.", episode.inWatchlist);
		assertEquals("Episode in watchlist boolean does not match.", false, episode.inWatchlist.booleanValue());
	}
	
	public void test_episodeWatchingNow() {
		List<UserProfile> users = getManager().showService().episodeWatchingNow("the-walking-dead", 1, 1).fire();
		assertNotNull("Result was null.", users);
		
		if (!users.isEmpty()) {
			assertNotNull("User was null.", users.get(0));
		}
	}
	
	public void test_season() {
		List<TvShowEpisode> episodes = getManager().showService().season("the-walking-dead", 1).fire();
		assertNotNull("Result was null.", episodes);
		assertFalse("Episode list was empty.", episodes.isEmpty());
	}
	
	public void test_seasons() {
		List<TvShowSeason> seasons = getManager().showService().seasons("the-walking-dead").fire();
		assertNotNull("Result was null.", seasons);
		assertFalse("Season list was empty.", seasons.isEmpty());
		
		TvShowSeason season = seasons.get(0);
		assertNotNull("Season was null.", season);
		assertNotNull("Season number was null.", season.season);
		assertNotNull("Season episodes was null.", season.episodes);
		assertNotNull("Season episodes count was null.", season.episodes.count);
		assertNotNull("Season URL was null.", season.url);
		assertNotNull("Season images was null.", season.images); //TODO own test cases
	}
	
	public void test_shouts() {
		List<Shout> shouts = getManager().showService().shouts("the-walking-dead").fire();
		assertNotNull("Result was null.", shouts);
		assertFalse("Shout list was empty.", shouts.isEmpty());
		assertNotNull("Shout was null.", shouts.get(0));
	}
	
	public void test_watchingNow() {
		List<UserProfile> users = getManager().showService().watchingNow("the-walking-dead").fire();
		assertNotNull("Result was null.", users);
		
		if (!users.isEmpty()) {
			assertNotNull("User was null.", users.get(0));
		}
	}
	
	public void test_trending() {
		List<TvShow> trending = getManager().showService().trending().fire();
		assertNotNull("Result was null.", trending);
		assertFalse("Trending list was empty.", trending.isEmpty());
		assertNotNull("Trending item was null.", trending.get(0));
	}
	
	public void test_summary() {
	    @SuppressWarnings("deprecation")
		Date firstAired = new Date(110, 9, 31);
		
		TvShow show = getManager().showService().summary("the-walking-dead").extended().fire();
		assertNotNull("Result was null.", show);
		assertEquals("Show title does not match.", "The Walking Dead", show.title);
		assertNotNull("Show year was null.", show.year);
		assertEquals("Show year does not match.", 2010, show.year.intValue());
		assertNotNull("Show URL was null.", show.url);
		assertNotNull("Show first aired was null.", show.firstAired);
		assertTrue("Show first aired does not match.", DateUtils.isSameDay(firstAired, show.firstAired));
		assertEquals("Show country does not match.", "United States", show.country);
		assertNotNull("Show overview was null.", show.overview);
		assertNotNull("Show runtime was null.", show.runtime);
		assertEquals("Show runtime does not match.", 60, show.runtime.intValue());
		assertEquals("Show network does not match.", "AMC", show.network);
		assertEquals("Show air day does not match.", DayOfTheWeek.Sunday, show.airDay);
		assertEquals("Show air time does not match.", "9:00pm", show.airTime);
		assertEquals("Show certification does not match.", "TV-14", show.certification);
		assertEquals("Show IMDB ID does not match.", "tt1520211", show.imdbId);
		assertEquals("Show TVDB ID does not match.", "153021", show.tvdbId);
		assertEquals("Show TV Rage ID does not match.", "25056", show.tvrageId);
	}
    
    public void test_related() {
        List<TvShow> related = getManager().showService().related("the-walking-dead").extended().fire();
        assertNotNull("Result was null.", related);
        assertFalse("Trending list was empty.", related.isEmpty());
        assertNotNull("Trending item was null.", related.get(0));
    }
}
