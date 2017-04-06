package com.uwetrottmann.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

public class Comment {

    public Integer id;
    public Integer parent_id;
    public OffsetDateTime created_at;
    public String comment;
    public Boolean spoiler;
    public Boolean review;
    public Integer replies;
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
