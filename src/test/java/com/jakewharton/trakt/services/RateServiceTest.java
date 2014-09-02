package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.RatingResponse;
import com.jakewharton.trakt.enumerations.Rating;
import com.jakewharton.trakt.enumerations.RatingType;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RateServiceTest extends BaseTestCase {

    @Test
    public void test_episode() {
        RatingResponse response = getManager().rateService()
                .episode(new RateService.EpisodeRating(213221, 1, 1, Rating.Meh));
        assertThat(response).isNotNull();
        assertThat(response.type).isEqualTo(RatingType.Episode);
        assertThat(response.rating).isEqualTo(Rating.Meh);
    }

    @Test
    public void test_movie() {
        RatingResponse response = getManager().rateService()
                .movie(new RateService.MovieRating("tt0082971", Rating.Meh));
        assertThat(response).isNotNull();
        assertThat(response.type).isEqualTo(RatingType.Movie);
        assertThat(response.rating).isEqualTo(Rating.Meh);
    }

    @Test
    public void test_show() {
        RatingResponse response = getManager().rateService()
                .show(new RateService.ShowRating(213221, Rating.Meh));
        assertThat(response).isNotNull();
        assertThat(response.type).isEqualTo(RatingType.Show);
        assertThat(response.rating).isEqualTo(Rating.Meh);
    }

}
