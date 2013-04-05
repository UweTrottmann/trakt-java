package com.jakewharton.trakt.entities;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

public class ListItemsResponse extends Response {
    private static final long serialVersionUID = 8123553856114248596L;

    public Integer inserted;
    @SerializedName("already_exist") public Integer alreadyExist;
    public Integer skipped;
    @SerializedName("skipped_array") public JsonArray skippedArray;

}
