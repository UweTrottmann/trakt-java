package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.EpisodeIds;
import com.uwetrottmann.trakt5.entities.GenericProgress;
import com.uwetrottmann.trakt5.entities.MovieIds;
import com.uwetrottmann.trakt5.entities.SyncEpisode;
import com.uwetrottmann.trakt5.entities.SyncMovie;
import org.junit.Test;

import java.io.IOException;

public class ScrobbleTest extends BaseTestCase {

    private GenericProgress genEpisodeProgress() {
        float progress = (float) 10.0;
        GenericProgress episodeProgress = new GenericProgress();
        episodeProgress.progress = progress;
        SyncEpisode episode = new SyncEpisode();
        episode.ids = EpisodeIds.tvdb(TestData.EPISODE_TVDB_ID);
        episodeProgress.episode = episode;
        return episodeProgress;
    }

    private GenericProgress genMovieProgress() {
        float progress = (float) 10.0;
        GenericProgress movieProgress = new GenericProgress();
        movieProgress.progress = progress;
        SyncMovie movie = new SyncMovie();
        movie.ids = MovieIds.tmdb(TestData.MOVIE_TMDB_ID);
        movieProgress.movie = movie;
        return movieProgress;
    }

    @Test
    public void startWatching() throws IOException {
        executeCall(getTrakt().scrobble().startWatching(genEpisodeProgress()));
        executeCall(getTrakt().scrobble().startWatching(genMovieProgress()));
    }

    @Test
    public void stopWatching() throws IOException {
        executeCall(getTrakt().scrobble().stopWatching(genEpisodeProgress()));
        executeCall(getTrakt().scrobble().stopWatching(genMovieProgress()));
    }

    @Test
    public void pauseWatching() throws IOException {
        executeCall(getTrakt().scrobble().pauseWatching(genEpisodeProgress()));
        executeCall(getTrakt().scrobble().pauseWatching(genMovieProgress()));
    }

}
