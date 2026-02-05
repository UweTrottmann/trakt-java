/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        Comment commentResponse = executeCall(getTrakt().comments().post(comment));

        // give the server some time to handle the data
        Thread.sleep(3000);

        // update the new comment
        Comment updatedComment = new Comment("This is toasty! I was just updated.", false, false);
        commentResponse = executeCall(getTrakt().comments().update(commentResponse.id, updatedComment));
        assertCommentResponse(updatedComment, commentResponse);
        // give the server some time to handle the data
        Thread.sleep(3000);

        // delete the comment again
        Response<Void> response = getTrakt().comments().delete(commentResponse.id).execute();
        assertSuccessfulResponse(response);
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }

    @Test
    public void test_delete() throws InterruptedException, IOException {
        // first post a new comment
        Comment comment = new Comment(buildTestEpisode(), "This is toasty! I should be deleted soon.", true, false);
        Comment commentResponse = executeCall(getTrakt().comments().post(comment));

        // give the server some time to handle the data
        Thread.sleep(3000);

        // delete the comment again
        Response<Void> response = getTrakt().comments().delete(commentResponse.id).execute();
        assertSuccessfulResponse(response);
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT);
    }

    @Test
    public void test_replies() throws InterruptedException, IOException {
        // first post a new comment
        Comment comment = new Comment(buildTestEpisode(), "This is a toasty comment!", true, false);
        Comment response = executeCall(getTrakt().comments().post(comment));

        // give the server some time to handle the data
        Thread.sleep(3000);

        // post a reply to the new comment
        Comment expectedReply = new Comment("This is a reply to the toasty comment!", false, false);
        Comment actualReply = executeCall(getTrakt().comments().postReply(response.id, expectedReply));
        assertCommentResponse(expectedReply, actualReply);

        // give the server some time to handle the data
        Thread.sleep(3000);

        // look if the comment replies include our new reply
        List<Comment> replies = executeCall(getTrakt().comments().replies(response.id));
        for (Comment reply : replies) {
            if (reply.id.equals(actualReply.id)) {
                assertCommentResponse(actualReply, reply);
            }
        }

        // delete the comment and replies (does this work?)
        Response<Void> deleteResponse = getTrakt().comments().delete(response.id).execute();
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
