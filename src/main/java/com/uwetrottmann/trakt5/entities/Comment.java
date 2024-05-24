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

package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class Comment {

    public Integer id;
    public Integer parent_id;
    public OffsetDateTime created_at;
    public OffsetDateTime updated_at;
    public String comment;
    public Boolean spoiler;
    public Boolean review;
    public Integer replies;
    public Integer likes;
    public Integer user_rating;
    public User user;

    // for posting
    public Movie movie;
    public Show show;
    public Episode episode;

    public Comment() {
    }

    /**
     * Build a movie comment.
     */
    public Comment(Movie movie, String comment, boolean spoiler, boolean review) {
        this(comment, spoiler, review);
        this.movie = movie;
    }

    /**
     * Build a show comment.
     */
    public Comment(Show show, String comment, boolean spoiler, boolean review) {
        this(comment, spoiler, review);
        this.show = show;
    }

    /**
     * Build an episode comment.
     */
    public Comment(Episode episode, String comment, boolean spoiler, boolean review) {
        this(comment, spoiler, review);
        this.episode = episode;
    }

    /**
     * Build an updated comment.
     */
    public Comment(String comment, boolean spoiler, boolean review) {
        this.comment = comment;
        this.spoiler = spoiler;
        this.review = review;
    }

}
