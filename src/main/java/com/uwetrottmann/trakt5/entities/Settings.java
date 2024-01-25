package com.uwetrottmann.trakt5.entities;

public class Settings {

    public User user;
    public Account account;
    public Connections connections;
    public SharingText sharing_text;
    public Limits limits;

    public static class Limits {

        public CountAndItemCount list;
        public ItemCount watchlist;
        public ItemCount favorites;
        public ItemCount recommendations;

        public static class ItemCount {
            public Integer item_count;
        }

        public static class CountAndItemCount extends ItemCount {
            public Integer count;
        }

    }
}
