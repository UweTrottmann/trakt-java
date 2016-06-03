package com.uwetrottmann.trakt5.entities;

import com.uwetrottmann.trakt5.enums.ListPrivacy;
import org.joda.time.DateTime;

public class List {

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

    public List name(String name) {
        this.name = name;
        return this;
    }

    public List description(String description) {
        this.description = description;
        return this;
    }

    public List privacy(ListPrivacy privacy) {
        this.privacy = privacy;
        return this;
    }

    public List displayNumbers(boolean displayNumbers) {
        this.display_numbers = displayNumbers;
        return this;
    }

    public List allowComments(boolean allowComments) {
        this.allow_comments = allowComments;
        return this;
    }

}
