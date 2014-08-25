package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import com.uwetrottmann.trakt.v2.TestData;
import com.uwetrottmann.trakt.v2.entities.Comment;
import com.uwetrottmann.trakt.v2.entities.Episode;
import com.uwetrottmann.trakt.v2.entities.EpisodeIds;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class CommentsTest extends BaseTestCase {

    @Test
    public void test_post() {
        Episode episode = new Episode();
        episode.ids= new EpisodeIds();
        episode.ids.tvdb = TestData.EPISODE_TVDB_ID;
        Comment comment = new Comment(episode, "This is toasty!", true, false);

        Comment commentResponse = getTrakt().comments().post(comment);
        assertThat(commentResponse.comment).isEqualTo(comment.comment);
        assertThat(commentResponse.spoiler).isEqualTo(comment.spoiler);
        assertThat(commentResponse.spoiler).isEqualTo(comment.review);
    }

}
