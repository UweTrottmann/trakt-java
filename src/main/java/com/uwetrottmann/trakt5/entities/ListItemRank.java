package com.uwetrottmann.trakt5.entities;

import java.util.List;

public class ListItemRank {

    public List<Long> rank;

    public static ListItemRank from(List<Long> rank) {
        ListItemRank listItemRank = new ListItemRank();
        listItemRank.rank = rank;
        return listItemRank;
    }
}
