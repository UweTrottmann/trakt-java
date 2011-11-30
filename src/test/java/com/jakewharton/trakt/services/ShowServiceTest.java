package com.jakewharton.trakt.services;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.MediaEntity;
import com.jakewharton.trakt.entities.Shout;
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
		MediaEntity entity = getManager().showService().episodeSummary("the-league", 1, 1).fire();
		assertNotNull("Result was null.", entity);
		
		TvShow show = entity.getShow();
		assertNotNull("Show was null.", show);
		assertEquals("Show title does not match.", "The League", show.getTitle());
		assertNotNull("Show year was null.", show.getYear());
		assertEquals("Show year does not match.", 2009, show.getYear().intValue());
		assertNotNull("Show URL was null.", show.getUrl());
		//TODO first aired
		assertEquals("Show country does not match.", "United States", show.getCountry());
		assertNotNull("Show overview was null.", show.getOverview());
		assertNotNull("Show runtime was null.", show.getRuntime());
		assertEquals("Show runtime does not match.", 30, show.getRuntime().intValue());
		assertEquals("Show network does not mtach.", "FX", show.getNetwork());
		assertEquals("Show air day does not match.", DayOfTheWeek.Thursday, show.getAirDay());
		assertEquals("Show air time does not mtach.", "10:30pm", show.getAirTime());
		assertEquals("Show certification does not match.", "TV-MA", show.getCertification());
		assertEquals("Show IMDB ID does not match.", "tt1480684", show.getImdbId());
		assertEquals("Show TVDB ID does not match.", "114701", show.getTvdbId());
		assertEquals("Show TV Rage ID does not match.", "24173", show.getTvRageId());
		assertNotNull("Show images was null.", show.getImages()); //TODO own test cases
		assertNotNull("Show ratings was null.", show.getRatings()); //TODO own test cases
		//TODO rating
		assertNotNull("Show in watchlist boolean was null.", show.getInWatchlist());
		assertEquals("Show in watchlist boolean does not match.", false, show.getInWatchlist().booleanValue());
		
		TvShowEpisode episode = entity.getEpisode();
		assertNotNull("Episode was null.");
		assertNotNull("Episode season was null.", episode.getSeason());
		assertEquals("Episode season does not match.", 1, episode.getSeason().intValue());
		assertNotNull("Episode number was null.", episode.getNumber());
		assertEquals("Episode number does not match.", 1, episode.getNumber().intValue());
		assertEquals("Episode title does not match.", "The Draft", episode.getTitle());
		assertNotNull("Episode overview was null.", episode.getOverview());
		assertNotNull("Episode URL was null.", episode.getUrl());
		//TODO first aired
		assertNotNull("Episode images was null.", episode.getImages()); //TODO own test cases
		assertNotNull("Episode ratings was null.", episode.getRatings()); //TODO own test cases
		assertNotNull("Episode watched boolean was null.", episode.getWatched());
		assertEquals("Episode watched boolean does not match.", false, episode.getWatched().booleanValue());
		assertNotNull("Episode plays was null.", episode.getPlays());
		assertEquals("Episode plays does not match.", 0, episode.getPlays().intValue());
		//TODO rating
		assertNotNull("Episode in watchlist boolean was null.", episode.getInWatchlist());
		assertEquals("Episode in watchlist boolean does not match.", false, episode.getInWatchlist().booleanValue());
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
		assertNotNull("Season number was null.", season.getSeason());
		assertNotNull("Season episodes was null.", season.getEpisodes());
		assertNotNull("Season episodes count was null.", season.getEpisodes().getCount());
		assertNotNull("Season URL was null.", season.getUrl());
		assertNotNull("Season images was null.", season.getImages()); //TODO own test cases
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
	
	@SuppressWarnings("deprecation")
	public void test_summary() {
		Date firstAired = new Date(110, 9, 31);
		
		TvShow show = getManager().showService().summary("the-walking-dead").extended().fire();
		assertNotNull("Result was null.", show);
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
		assertEquals("Show air day does not match.", DayOfTheWeek.Sunday, show.getAirDay());
		assertEquals("Show air time does not match.", "9:00pm", show.getAirTime());
		assertEquals("Show certification does not match.", "TV-MA", show.getCertification());
		assertEquals("Show IMDB ID does not match.", "tt1520211", show.getImdbId());
		assertEquals("Show TVDB ID does not match.", "153021", show.getTvdbId());
		assertEquals("Show TV Rage ID does not match.", "25056", show.getTvRageId());
	}
    
    public void test_related() {
        List<TvShow> related = getManager().showService().related("the-walking-dead").extended().fire();
        assertNotNull("Result was null.", related);
        assertFalse("Trending list was empty.", related.isEmpty());
        assertNotNull("Trending item was null.", related.get(0));
    }
}
