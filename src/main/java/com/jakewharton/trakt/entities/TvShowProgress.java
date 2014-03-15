/*
 * Copyright 2014 Sam Malone.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jakewharton.trakt.entities;

import com.jakewharton.trakt.TraktEntity;
import java.util.Map;

/**
 *
 * @author Sam Malone
 */
public class TvShowProgress implements TraktEntity {
    private static final long serialVersionUID = 5278238759952077403L;
    
    public Show show;
    public Progress progress;
    public java.util.List<Season> seasons;
    public java.util.List<Integer> hidden_seasons;
    public Stats stats;
    
    /**
     * Next episode if one exists. Will be null if there is no next episode.
     */
    public NextEpisode next_episode;
    
    public static class Progress {
        public Integer percentage;
        public Integer aired;
        public Integer completed;
        public Integer left;
    }
    
    public static class Season extends Progress {
        public Integer season;
        public Map<String, Boolean> episodes;
    }
    
    public static class Stats {
        public Integer plays;
        public Integer scrobbles;
        public Integer checkins;
        public Integer seen;
    }
    
    public static class Show {
        public String imdb_id;
        public Integer tvdb_id;
        public Integer tvrage_id;
        public String title;
        public Integer year;
    }
    
    public static class NextEpisode {
        public Integer season;
        public Integer number;
        public String title;
        public Integer first_aired;
        public Images images;

        public NextEpisode(Integer season, Integer number, String title, Integer first_aired) {
            this.season = season;
            this.number = number;
            this.title = title;
            this.first_aired = first_aired;
        }
    }
}
