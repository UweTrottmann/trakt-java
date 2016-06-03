package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.ListPrivacy;
import org.joda.time.DateTime;

public class TraktList {

    public ListIds ids;
    public String name;
    public String description;
    public ListPrivacy privacy;
    public Boolean display_numbers;
    public Boolean allow_comments;
    public DateTime updated_at;
    public Integer item_count;
    public Integer comment_count;
    public Integer likes;

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

}
