
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Activity;
import retrofit.http.POST;

public interface ActivityService {

    /**
     * Get a list of all public activity for the entire Trakt community. The
     * most recent 100 activities are returned for all types and actions. You
     * can customize the activity stream with only the types and actions you
     * need.
     */
    @POST("/activity/community/{apikey}")
    Activity community();

}
