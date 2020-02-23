package com.redissi.trakt.services

import com.redissi.trakt.BaseTestCase
import com.redissi.trakt.TestData
import com.redissi.trakt.TestResponse
import com.redissi.trakt.entities.Comment
import com.redissi.trakt.entities.Episode
import com.redissi.trakt.entities.EpisodeIds
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import java.io.IOException
import java.net.HttpURLConnection

class CommentsTest : BaseTestCase(), TestResponse {
    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun `post and update a command`() = runBlocking<Unit> {
        // first post a new comment
        val comment = Comment.build(
            buildTestEpisode(),
            "This is a toasty comment!",
            spoiler = true,
            review = false
        )
        var commentResponse = trakt.comments().post(comment)

        commentResponse.shouldNotBeNull()
        commentResponse.id.shouldNotBeNull()

        // update the new comment
        val updatedComment = Comment.build("This is toasty! I was just updated.", spoiler = false, review = false)
        commentResponse = trakt.comments().update(commentResponse.id!!, updatedComment)

        commentResponse.shouldNotBeNull()

        assertCommentResponse(updatedComment, commentResponse)

        // delete the comment again
        val response = trakt.comments().delete(commentResponse.id!!)
        assertSuccessfulResponse(response, trakt)
        response.code().shouldBeEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun `delete a comment`() = runBlocking<Unit> {
        // first post a new comment
        val comment = Comment.build(
            buildTestEpisode(),
            "This is toasty! I should be deleted soon.",
            spoiler = true,
            review = false
        )
        val commentResponse = trakt.comments().post(comment)

        commentResponse.shouldNotBeNull()
        commentResponse.id.shouldNotBeNull()

        // delete the comment again
        val response = trakt.comments().delete(commentResponse.id!!)
        assertSuccessfulResponse(response, trakt)
        response.code().shouldBeEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun `reply to a comment`() = runBlocking<Unit> {
        // first post a new comment
        val comment = Comment.build(
            buildTestEpisode(),
            "This is a toasty comment!",
            spoiler = true,
            review = false
        )
        val response = trakt.comments().post(comment)
        response.shouldNotBeNull()
        response.id.shouldNotBeNull()
        // post a reply to the new comment
        val expectedReply = Comment.build("This is a reply to the toasty comment!", spoiler = false, review = false)
        val actualReply = trakt.comments().postReply(response.id!!, expectedReply)
        actualReply.shouldNotBeNull()
        actualReply.id.shouldNotBeNull()
        assertCommentResponse(expectedReply, actualReply)
        // look if the comment replies include our new reply
        val replies = trakt.comments().replies(response.id!!)
        replies.shouldNotBeNull()
        for (reply in replies) {
            if (reply.id == actualReply.id) {
                assertCommentResponse(actualReply, reply)
            }
        }
        // delete the comment and replies (does this work?)
        val deleteResponse = trakt.comments().delete(response.id!!)
        assertSuccessfulResponse(deleteResponse, trakt)
        deleteResponse.code().shouldBeEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    companion object {
        private fun assertCommentResponse(
            expected: Comment,
            actual: Comment
        ) {
            actual.comment.shouldBeEqualTo(expected.comment)
            actual.spoiler.shouldBeEqualTo(expected.spoiler)
            actual.review.shouldBeEqualTo(expected.review)
        }

        private fun buildTestEpisode(): Episode {
            return Episode(ids = EpisodeIds.tvdb(TestData.EPISODE_TVDB_ID))
        }
    }
}