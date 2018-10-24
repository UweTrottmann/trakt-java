package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.ListPrivacy;
import com.uwetrottmann.trakt5.enums.SortBy;
import com.uwetrottmann.trakt5.enums.SortHow;
import org.threeten.bp.OffsetDateTime;

public class TraktList {

    public ListIds ids;
    public String name;
    public String description;
    public ListPrivacy privacy;
    public Boolean display_numbers;
    public Boolean allow_comments;
    public SortBy sort_by;
    public SortHow sort_how;
    public OffsetDateTime created_at;
    public OffsetDateTime updated_at;
    public Integer item_count;
    public Integer comment_count;
    public Integer likes;
    public User user;

    public TraktList id(ListIds id) {
        this.ids = id;
        return this;
    }

    public TraktList name(String name) {
        this.name = name;
        return this;
    }

    public TraktList description(String description) {
        this.description = description;
        return this;
    }

    public TraktList privacy(ListPrivacy privacy) {
        this.privacy = privacy;
        return this;
    }

    public TraktList displayNumbers(boolean displayNumbers) {
        this.display_numbers = displayNumbers;
        return this;
    }

    public TraktList allowComments(boolean allowComments) {
        this.allow_comments = allowComments;
        return this;
    }

    public TraktList sortBy(SortBy sortBy) {
        this.sort_by = sortBy;
        return this;
    }

    public TraktList sortHow(SortHow sortHow) {
        this.sort_how = sortHow;
        return this;
    }

}
