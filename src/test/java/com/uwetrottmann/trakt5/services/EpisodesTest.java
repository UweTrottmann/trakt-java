package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.Ratings;
import com.uwetrottmann.trakt5.enums.Extended;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EpisodesTest extends BaseTestCase {

    @Test
    public void test_summary() throws IOException {
        Response<Episode> response = getTrakt().episodes().summary(String.valueOf(TestData.SHOW_TRAKT_ID),
                TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER, Extended.FULLIMAGES).execute();
        assertSuccessfulResponse(response);
        Episode episode = response.body();
        assertThat(episode.title).isEqualTo(TestData.EPISODE_TITLE);
        assertThat(episode.season).isEqualTo(TestData.EPISODE_SEASON);
        assertThat(episode.number).isEqualTo(TestData.EPISODE_NUMBER);
        assertThat(episode.ids.imdb).isEqualTo(TestData.EPISODE_IMDB_ID);
        assertThat(episode.ids.tmdb).isEqualTo(TestData.EPISODE_TMDB_ID);
        assertThat(episode.ids.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID);
    }

    @Test
    public void test_comments() throws IOException {
        Response<List<Comment>> response = getTrakt().episodes().comments(TestData.SHOW_SLUG, TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER, 1, DEFAULT_PAGE_SIZE, Extended.DEFAULT_MIN).execute();
        assertSuccessfulResponse(response);
    }

    @Test
    public void test_ratings() throws IOException {
        Response<Ratings> response = getTrakt().episodes().ratings(TestData.SHOW_SLUG, TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER).execute();
        assertSuccessfulResponse(response);
        Ratings ratings = response.body();
        assertRatings(ratings);
    }

}
