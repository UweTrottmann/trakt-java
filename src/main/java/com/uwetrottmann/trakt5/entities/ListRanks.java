package com.uwetrottmann.trakt5.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListRanks {

    @SerializedName("rank")
    public List<Long> ranks;

    public ListRanks ranks(List<Long> ranks) {
        this.ranks = ranks;
        return this;
    }
}
