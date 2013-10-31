package com.jakewharton.trakt.entities;

import java.util.List;

public class ActionResponse extends Response {

    public int inserted;

    public int already_exist;

    public int skipped;

    public List<Movie> skipped_movies;

}
