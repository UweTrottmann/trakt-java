package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Activity;

import static org.fest.assertions.api.Assertions.assertThat;

public class ActivityServiceTest extends BaseTestCase {

    public void test_community() {
        Activity activity = getManager().activityService().community();
        assertThat(activity).isNotNull();
        //TODO
    }

    //	public void test_episodes() {
//	    Activity activity = getManager().activityService().episodes("the-walking-dead", 2, 1).fire();
//        assertNotNull("Result was null.", activity);
//        //TODO
//	}
//
//    public void test_friends() {
//        Activity activity = getManager().activityService().friends().fire();
//        assertNotNull("Result was null.", activity);
//        //TODO
//    }
//
//    public void test_movies() {
//        Activity activity = getManager().activityService().movies("the-matrix-1999","cloud-atlas-2012").fire();
//        assertNotNull("Result was null.", activity);
//        //TODO
//    }
//
//    public void test_seasons() {
//        Activity activity = getManager().activityService().seasons("dexter", 6).fire();
//        assertNotNull("Result was null.", activity);
//        //TODO
//    }
//
//    public void test_shows() {
//        Activity activity = getManager().activityService().shows("battlestar-galactica-2003", "caprica").fire();
//        assertNotNull("Result was null.", activity);
//        //TODO
//    }
//
    public void test_user() {
        Activity activity = getManager().activityService().user("JakeWharton");
        assertThat(activity).isNotNull();

        activity = getManager().activityService()
                .user("JakeWharton", "all", "checkin,seen", null, null);
        assertThat(activity).isNotNull();

        activity = getManager().activityService()
                .user("JakeWharton", "all", "checkin,seen", 1, 1);
        assertThat(activity).isNotNull();
    }
}
