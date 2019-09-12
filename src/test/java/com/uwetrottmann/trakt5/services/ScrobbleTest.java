package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.EpisodeIds;
import com.uwetrottmann.trakt5.entities.MovieIds;
import com.uwetrottmann.trakt5.entities.PlaybackResponse;
import com.uwetrottmann.trakt5.entities.ScrobbleProgress;
import com.uwetrottmann.trakt5.entities.SyncEpisode;
import com.uwetrottmann.trakt5.entities.SyncMovie;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ScrobbleTest extends BaseTestCase {

    private ScrobbleProgress genEpisodeProgress(double progress) {
        // Use a different episode than for other tests to avoid conflicts.
        SyncEpisode episode = new SyncEpisode().id( EpisodeIds.tvdb(4647887)); /* Agents of Shield S01E02 */
        return new ScrobbleProgress(episode, progress, null, null);
    }

    private ScrobbleProgress genMovieProgress(double progress) {
        // Use a different movie than for other tests to avoid conflicts.
        SyncMovie movie = new SyncMovie().id(MovieIds.tmdb(333339)); /* Ready Player One */
        return new ScrobbleProgress(movie, progress, null, null);
    }

    @Test
    public void scrobble_episode() throws IOException, InterruptedException {
        PlaybackResponse playbackResponse = executeCall(getTrakt().scrobble().startWatching(genEpisodeProgress(20.0)));
        assertThat(playbackResponse.action).isEqualTo("start");

        // Give the server some time to process the request.
        Thread.sleep(1500);

        playbackResponse = executeCall(getTrakt().scrobble().pauseWatching(genEpisodeProgress(50.0)));
        assertThat(playbackResponse.action).isEqualTo("pause");

        // Give the server some time to process the request.
        Thread.sleep(1500);

        playbackResponse = executeCall(getTrakt().scrobble().stopWatching(genEpisodeProgress(75.0)));
        // Above 80% progress action is "scrobble", below "pause".
        // Pause to avoid blocking the next test until the episode runtime has passed.
        assertThat(playbackResponse.action).isEqualTo("pause");
    }

    @Test
    public void scrobble_movie() throws IOException, InterruptedException {
        PlaybackResponse playbackResponse = executeCall(getTrakt().scrobble().startWatching(genMovieProgress(20.0)));
        assertThat(playbackResponse.action).isEqualTo("start");

        // Give the server some time to process the request.
        Thread.sleep(1500);

        playbackResponse = executeCall(getTrakt().scrobble().pauseWatching(genMovieProgress(50.0)));
        assertThat(playbackResponse.action).isEqualTo("pause");

        // Give the server some time to process the request.
        Thread.sleep(1500);

        playbackResponse = executeCall(getTrakt().scrobble().stopWatching(genMovieProgress(75.0)));
        // Above 80% progress action is "scrobble", below "pause".
        // Pause to avoid blocking the next test until the movie runtime has passed.
        assertThat(playbackResponse.action).isEqualTo("pause");
    }

}
