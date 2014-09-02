package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.CalendarDate;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendarServiceTest extends BaseTestCase {

    @Test
    public void test_premieres() {
        List<CalendarDate> premieres = getManager().calendarService().premieres("2013-09-21", 5);
        assertThat(premieres).isNotNull();
        assertThat(premieres).isNotEmpty();
    }

    @Test
    public void test_shows() {
        List<CalendarDate> shows = getManager().calendarService().shows("2013-09-21", 1);
        assertThat(shows).isNotNull();
        assertThat(shows).isNotEmpty();
    }

}
