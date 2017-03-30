package com.uwetrottmann.trakt5;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link Date} wrapper which overrides {@link #toString()} to format date to what trakt expects.
 */
public class TraktDate {

    private final SimpleDateFormat iso8601WithMillis;
    private final Date date;

    public TraktDate(Date date) {
        this.date = date;
        this.iso8601WithMillis = new SimpleDateFormat(TraktV2Helper.DATE_FORMAT_TRAKT);
        this.iso8601WithMillis.setTimeZone(TraktV2Helper.DEFAULT_TIME_ZONE_TRAKT);
    }

    @Override
    public String toString() {
        return iso8601WithMillis.format(date);
    }
}
