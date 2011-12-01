package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Activity;

public class ActivityServiceTest extends BaseTestCase {
	public void test_community() {
		Activity activity = getManager().activityService().community().fire();
        assertNotNull("Result was null.", activity);
        //TODO
	}
	
	public void test_episodes() {
	    Activity activity = getManager().activityService().episodes("the-walking-dead", 2, 1).fire();
        assertNotNull("Result was null.", activity);
        //TODO
	}
    
    public void test_friends() {
        Activity activity = getManager().activityService().friends().fire();
        assertNotNull("Result was null.", activity);
        //TODO
    }
    
    public void test_movies() {
        Activity activity = getManager().activityService().movies("the-matrix-1999").fire();
        assertNotNull("Result was null.", activity);
        //TODO
    }
    
    public void test_seasons() {
        Activity activity = getManager().activityService().seasons("dexter", 6).fire();
        assertNotNull("Result was null.", activity);
        //TODO
    }
    
    public void test_shows() {
        Activity activity = getManager().activityService().shows("battlestar-galactica-2003", "caprica").fire();
        assertNotNull("Result was null.", activity);
        //TODO
    }
    
    public void test_user() {
        Activity activity = getManager().activityService().user("JakeWharton").fire();
        assertNotNull("Result was null.", activity);
        //TODO
    }
}
