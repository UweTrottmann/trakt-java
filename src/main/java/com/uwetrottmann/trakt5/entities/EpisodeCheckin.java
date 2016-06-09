package com.uwetrottmann.trakt5.entities;

public class EpisodeCheckin extends BaseCheckin {

    public Show show;
    public SyncEpisode episode;

    public static class Builder {

        private Show show;
        private SyncEpisode episode;
        protected ShareSettings sharing;
        protected String message;
        protected String venue_id;
        protected String venue_name;
        protected String app_version;
        protected String app_date;

        public Builder(SyncEpisode episode, String appVersion, String appDate) {
            if (episode == null) {
                throw new IllegalArgumentException("Episode must not be null");
            }
            this.episode = episode;
            this.app_version = appVersion;
            this.app_date = appDate;
        }

        public Builder show(Show show) {
            this.show = show;
            return this;
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

        public EpisodeCheckin build() {
            EpisodeCheckin checkin = new EpisodeCheckin();
            checkin.show = show;
            checkin.episode = episode;
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
