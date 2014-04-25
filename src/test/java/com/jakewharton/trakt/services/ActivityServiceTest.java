package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Activity;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ActivityServiceTest extends BaseTestCase {

    @Test
    public void test_community() {
        Activity activity = getManager().activityService().community();
        assertThat(activity).isNotNull();
        //TODO
    }

    @Test
    public void test_friends() {
        Activity activity = getManager().activityService().friends("all", "all", (long) 0, null, null);
        assertThat(activity).isNotNull();
        assertThat(activity.activity).isNotEmpty();
    }

    @Test
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
