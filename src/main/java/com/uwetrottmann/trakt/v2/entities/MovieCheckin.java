package com.uwetrottmann.trakt.v2.entities;

public class MovieCheckin extends BaseCheckin {

    public SyncMovie movie;

    public static class Builder {

        private SyncMovie movie;
        protected ShareSettings sharing;
        protected String message;
        protected String venue_id;
        protected String venue_name;
        protected String app_version;
        protected String app_date;

        public Builder(SyncMovie movie, String appVersion, String appDate) {
            if (movie == null) {
                throw new IllegalArgumentException("Movie must not be null");
            }
            this.movie = movie;
            this.app_version = appVersion;
            this.app_date = appDate;
        }

        public Builder sharing(ShareSettings shareSettings) {
            this.sharing = shareSettings;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder venueId(String venueId) {
            this.venue_id = venueId;
            return this;
        }

        public Builder venueName(String venueName) {
            this.venue_name = venueName;
            return this;
        }

        public MovieCheckin build() {
            MovieCheckin checkin = new MovieCheckin();
            checkin.movie = movie;
            checkin.sharing = sharing;
            checkin.message = message;
            checkin.venue_id = venue_id;
            checkin.venue_name = venue_name;
            checkin.app_date = app_date;
            checkin.app_version = app_version;
            return checkin;
        }
    }

}
