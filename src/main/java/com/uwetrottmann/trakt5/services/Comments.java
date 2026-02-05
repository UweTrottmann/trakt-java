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

import com.uwetrottmann.trakt5.TraktV2;
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
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Add a new comment to a movie, show, episode, or list. If you add a review, it needs to be at least 200 words.
     * Also make sure to allow and encourage spoilers to be indicated in your app.
     *
     * @param comment A {@link Comment} with either a movie, show or episode set, plus comment and spoiler or review
     *                flags.
     */
    @POST("comments")
    Call<Comment> post(
            @Body Comment comment
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Returns a single comment and indicates how many replies it has. Use GET /comments/:id/replies to get the actual
     * replies.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @GET("comments/{id}")
    Call<Comment> get(
            @Path("id") int id
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Update a single comment created within the last hour. The OAuth user must match the author of the comment in
     * order to update it.
     *
     * @param id      A specific comment ID. Example: 417.
     * @param comment A {@link Comment} with comment and spoiler or review flags.
     */
    @PUT("comments/{id}")
    Call<Comment> update(
            @Path("id") int id,
            @Body Comment comment
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Delete a single comment created within the last hour. This also effectively removes any replies this comment has.
     * The OAuth user must match the author of the comment in order to delete it.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @DELETE("comments/{id}")
    Call<Void> delete(
            @Path("id") int id
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Returns all replies for a comment. It is possible these replies could have replies themselves, so in that case
     * you would just call GET /comment/:id/replies again with the new comment_id.
     *
     * @param id A specific comment ID. Example: 417.
     */
    @GET("comments/{id}/replies")
    Call<List<Comment>> replies(
            @Path("id") int id
    );

    /**
     * <b>OAuth {@link TraktV2#accessToken(String) access token} required</b>
     * <p>
     * Add a new reply to an existing comment. Also make sure to allow and encourage spoilers to be indicated in your
     * app.
     *
     * @param id      A specific comment ID. Example: 417.
     * @param comment A {@link Comment} with comment and spoiler or review flags.
     */
    @POST("comments/{id}/replies")
    Call<Comment> postReply(
            @Path("id") int id,
            @Body Comment comment
    );

}
