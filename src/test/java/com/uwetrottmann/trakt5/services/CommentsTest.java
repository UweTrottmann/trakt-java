package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.Comment;
import com.uwetrottmann.trakt5.entities.Episode;
import com.uwetrottmann.trakt5.entities.EpisodeIds;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentsTest extends BaseTestCase {

    @Test
    public void test_postAndUpdate() throws InterruptedException, IOException {
        // first post a new comment
        Comment comment = new Comment(buildTestEpisode(), "This is a toasty comment!", true, false);
        Response<Comment> commentResponse = getTrakt().comments().post(comment).execute();
        assertSuccessfulResponse(commentResponse);

        // give the server some time to handle the data
        Thread.sleep(3000);

        // update the new comment
        Comment updatedComment = new Comment("This is toasty! I was just updated.", false, false);
        commentResponse = getTrakt().comments().update(commentResponse.body().id, updatedComment).execute();
        assertSuccessfulResponse(commentResponse);
        assertCommentResponse(updatedComment, commentResponse.body());

        // give the server some time to handle the data
        Thread.sleep(3000);

        // delete the comment again
        Response response = getTrakt().comments().delete(commentResponse.body().id).execute();
        assertSuccessfulResponse(response);
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }

    @Test
    public void test_delete() throws InterruptedException, IOException {
        // first post a new comment
        Comment comment = new Comment(buildTestEpisode(), "This is toasty! I should be deleted soon.", true, false);
        Response<Comment> commentResponse = getTrakt().comments().post(comment).execute();
        assertSuccessfulResponse(commentResponse);

        // give the server some time to handle the data
        Thread.sleep(3000);

        // delete the comment again
        Response response = getTrakt().comments().delete(commentResponse.body().id).execute();
        assertSuccessfulResponse(response);
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }

    @Test
    public void test_replies() throws InterruptedException, IOException {
        // first post a new comment
        Comment comment = new Comment(buildTestEpisode(), "This is a toasty comment!", true, false);
        Response<Comment> response = getTrakt().comments().post(comment).execute();
        assertSuccessfulResponse(response);
        comment = response.body();

        // give the server some time to handle the data
        Thread.sleep(3000);

        // post a reply to the new comment
        Comment expectedReply = new Comment("This is a reply to the toasty comment!", false, false);
        response = getTrakt().comments().postReply(comment.id, expectedReply).execute();
        assertSuccessfulResponse(response);
        Comment actualReply = response.body();
        assertCommentResponse(expectedReply, actualReply);

        // give the server some time to handle the data
        Thread.sleep(3000);

        // look if the comment replies include our new reply
        Response<List<Comment>> repliesResponse = getTrakt().comments().replies(comment.id).execute();
        assertSuccessfulResponse(response);
        List<Comment> replies = repliesResponse.body();
        for (Comment reply : replies) {
            if (reply.id.equals(actualReply.id)) {
                assertCommentResponse(actualReply, reply);
            }
        }

        // delete the comment and replies (does this work?)
        Response deleteResponse = getTrakt().comments().delete(comment.id).execute();
        assertSuccessfulResponse(deleteResponse);
        assertThat(deleteResponse.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }

    private static void assertCommentResponse(Comment expected, Comment actual) {
        assertThat(actual.comment).isEqualTo(expected.comment);
        assertThat(actual.spoiler).isEqualTo(expected.spoiler);
        assertThat(actual.review).isEqualTo(expected.review);
    }

    private static Episode buildTestEpisode() {
        Episode episode = new Episode();
        episode.ids = EpisodeIds.tvdb(TestData.EPISODE_TVDB_ID);
        return episode;
    }
}
