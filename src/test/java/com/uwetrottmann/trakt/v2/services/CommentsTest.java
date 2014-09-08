package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.exceptions.OAuthUnauthorizedException;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.EpisodeIds;
import org.junit.Test;
import retrofit.client.Response;

import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentsTest extends BaseTestCase {

    @Test
    public void test_post() throws OAuthUnauthorizedException {
        Comment comment = new Comment(buildTestEpisode(), "This is toasty!", true, false);

        Comment commentResponse = getTrakt().comments().post(comment);
        assertCommentResponse(comment, commentResponse);
    }

    @Test
    public void test_update() throws InterruptedException, OAuthUnauthorizedException {
        // first post a new comment
        Comment comment = new Comment(buildTestEpisode(), "This is toasty!", true, false);
        Comment commentResponse = getTrakt().comments().post(comment);

        // give the server some time to handle the data
        Thread.sleep(3000);

        // update the new comment
        Comment updatedComment = new Comment("This is toasty! I was just updated.", false, false);
        commentResponse = getTrakt().comments().update(commentResponse.id, updatedComment);
        assertCommentResponse(updatedComment, commentResponse);
    }

    private void assertCommentResponse(Comment expected, Comment actual) {
        assertThat(actual.comment).isEqualTo(expected.comment);
        assertThat(actual.spoiler).isEqualTo(expected.spoiler);
        assertThat(actual.review).isEqualTo(expected.review);
    }

    private Episode buildTestEpisode() {
        Episode episode = new Episode();
        episode.ids = new EpisodeIds();
        episode.ids.tvdb = TestData.EPISODE_TVDB_ID;
        return episode;
    }

    @Test
    public void test_delete() throws InterruptedException, OAuthUnauthorizedException {
        // first post a new comment
        Comment comment = new Comment(buildTestEpisode(), "This is toasty! I should be deleted soon.", true, false);
        Comment commentResponse = getTrakt().comments().post(comment);

        // give the server some time to handle the data
        Thread.sleep(3000);

        // delete the comment again
        Response response = getTrakt().comments().delete(commentResponse.id);
        assertThat(response.getStatus()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }
}
