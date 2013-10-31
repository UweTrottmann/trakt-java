package com.jakewharton.trakt.services;

import java.util.List;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.CalendarDate;

import static org.fest.assertions.api.Assertions.assertThat;

public class CalendarServiceTest extends BaseTestCase {

    public void test_premieres() {
        List<CalendarDate> premieres = getManager().calendarService().premieres("2013-09-21", 5);
        assertThat(premieres).isNotNull();
        assertThat(premieres).isNotEmpty();
    }

    public void test_shows() {
        List<CalendarDate> shows = getManager().calendarService().shows("2013-09-21", 1);
        assertThat(shows).isNotNull();
        assertThat(shows).isNotEmpty();
    }

}
