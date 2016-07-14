package com.uwetrottmann.trakt5.entities;

public class Stats {

    public Integer watchers;
    public Integer plays;
    public Integer collectors;
    public Integer comments;
    public Integer lists;
    public Integer votes;

    /** Specific to shows, seasons and episodes. */
    public Integer collected_episodes;

}
