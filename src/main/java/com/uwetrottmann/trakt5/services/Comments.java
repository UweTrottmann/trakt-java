package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.Comment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface Comments {

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add a new comment to a movie, show, episode, or list. If you add a review, it needs to be at least 200 words.
     * Also make sure to allow and encourage spoilers to be indicated in your app.
     *
     * @param comment A {@link Comment} with either a movie, show or episode set, plus comment and spoiler or review
     * flags.
     */
    @POST("/comments")
    Call<Comment> post(
            @Body Comment comment
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns a single comment and indicates how many replies it has. Use GET /comments/:id/replies to get the
     * actual replies.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @GET("/comments/{id}")
    Call<Comment> get(
            @Path("id") int id
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Update a single comment created within the last hour. The OAuth user must match the author of the comment in
     * order to update it.
     *
     * @param id A specific comment ID. Example: 417.
     * @param comment A {@link Comment} with comment and spoiler or review flags.
     */
    @PUT("/comments/{id}")
    Call<Comment> update(
            @Path("id") int id,
            @Body Comment comment
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Delete a single comment created within the last hour. This also effectively removes any replies this comment
     * has. The OAuth user must match the author of the comment in order to delete it.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @DELETE("/comments/{id}")
    Call<Void> delete(
            @Path("id") int id
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Returns all replies for a comment. It is possible these replies could have replies themselves, so in that
     * case you would just call GET /comment/:id/replies again with the new comment_id.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @GET("/comments/{id}/replies")
    Call<List<Comment>> replies(
            @Path("id") int id
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Add a new reply to an existing comment. Also make sure to allow and encourage spoilers to be indicated in
     * your app.
     *
     * @param id A specific comment ID. Example: 417.
     * @param comment A {@link Comment} with comment and spoiler or review flags.
     */
    @POST("/comments/{id}/replies")
    Call<Comment> postReply(
            @Path("id") int id,
            @Body Comment comment
    );

}
