package com.jakewharton.trakt.entities;

import com.google.gson.annotations.SerializedName;

public class Ratings {

    public Integer percentage;

    public Integer votes;

    public Integer loved;

    public Integer hated;

    public Distribution distribution;

    public static class Distribution {

        @SerializedName("1")
        public int _1;

        @SerializedName("2")
        public int _2;

        @SerializedName("3")
        public int _3;

        @SerializedName("4")
        public int _4;

        @SerializedName("5")
        public int _5;

        @SerializedName("6")
        public int _6;

        @SerializedName("7")
        public int _7;

        @SerializedName("8")
        public int _8;

        @SerializedName("9")
        public int _9;

        @SerializedName("10")
        public int _10;
    }

}