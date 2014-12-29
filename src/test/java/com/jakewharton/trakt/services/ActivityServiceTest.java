package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.Activity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityServiceTest extends BaseTestCase {

    public static final long DAY_IN_SEC = 24 * 60 * 60;
    public static final long WEEK_IN_SEC = DAY_IN_SEC * 7;

    @Test
    public void test_community() {
        Activity activity = getManager().activityService().community();
        assertThat(activity).isNotNull();
        //TODO
    }

    @Test
    public void test_friends() {
        long sixWeeksAgo = (System.currentTimeMillis() / 1000) - 4 * WEEK_IN_SEC;
        Activity activity = getManager().activityService().friends("all", "all", sixWeeksAgo, null, null);
        assertThat(activity).isNotNull();
        assertThat(activity.activity).isNotEmpty();

        activity = getManager().activityService().friends("all", "all", sixWeeksAgo, 1, 1);
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
