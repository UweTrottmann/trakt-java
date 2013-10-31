package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;

import java.util.Date;
import java.util.List;

public class CalendarDate implements TraktEntity {
    private static final long serialVersionUID = 5985118362541597172L;

    public static class CalendarTvShowEpisode implements TraktEntity {
        private static final long serialVersionUID = -7066863350641449761L;

        public TvShow show;
        public TvShowEpisode episode;
    }

    public Date date;
    public List<CalendarTvShowEpisode> episodes;

}
