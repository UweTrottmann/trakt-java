package com.uwetrottmann.trakt5.entities;

public class ScrobbleProgress extends GenericProgress {

    public String app_version;
    public String app_date;

    private ScrobbleProgress(String app_version, String app_date) {
        this.app_version = app_version;
        this.app_date = app_date;
    }

    public ScrobbleProgress(SyncEpisode episode, double progress, String app_version, String app_date) {
        this(app_version, app_date);
        this.episode = episode;
        this.progress = progress;
    }

    public ScrobbleProgress(SyncMovie movie, double progress, String app_version, String app_date) {
        this(app_version, app_date);
        this.movie = movie;
        this.progress = progress;
    }

}
