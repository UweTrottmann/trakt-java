
package com.jakewharton.trakt.services;

import com.google.gson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.Activity;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.enumerations.ActivityAction;
import com.jakewharton.trakt.enumerations.ActivityType;
import retrofit.http.Body;
import retrofit.http.POST;

import java.util.Date;

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
