package com.uwetrottmann.trakt5.entities;

import javax.annotation.Nonnull;
import java.util.List;

public class ListItemRank {

    public List<Long> rank;

    @Nonnull
    public static ListItemRank from(List<Long> rank) {
        ListItemRank listItemRank = new ListItemRank();
        listItemRank.rank = rank;
        return listItemRank;
    }
}
