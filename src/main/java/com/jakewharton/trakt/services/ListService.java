package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.ListResponse;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.enumerations.ListPrivacy;

import retrofit.http.Body;
import retrofit.http.POST;

public interface ListService {

    @POST("/lists/add/{apikey}")
    ListResponse add(
            @Body NewList list
    );

    @POST("/lists/delete/{apikey}")
    Response delete(
            @Body DeleteList list
    );

    @POST("/lists/update/{apikey}")
    ListResponse update(
            @Body UpdatedList list
    );

    public static class NewList {

        public String name;

        public String description;

        public ListPrivacy privacy;

        public Boolean show_numbers;

        public Boolean allow_shouts;

        public NewList(String name, ListPrivacy privacy) {
            this.name = name;
            this.privacy = privacy;
        }

        public NewList description(String description) {
            this.description = description;
            return this;
        }

        public NewList showNumbers(boolean showNumbers) {
            this.show_numbers = showNumbers;
            return this;
        }

        public NewList allowShouts(boolean allowShouts) {
            this.allow_shouts = allowShouts;
            return this;
        }
    }

    public static class DeleteList {

        public String slug;

        public DeleteList(String slug) {
            this.slug = slug;
        }
    }

    public static class UpdatedList extends NewList {

        public String slug;

        private UpdatedList(String name, ListPrivacy privacy) {
            super(name, privacy);
        }

        public UpdatedList slug(String slug) {
            this.slug = slug;
            return this;
        }

        public UpdatedList name(String name) {
            this.name = name;
            return this;
        }
    }

}
