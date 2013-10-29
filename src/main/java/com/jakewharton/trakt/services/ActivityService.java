
package com.jakewharton.trakt.services;

import com.jakewharton.trakt.entities.Activity;

import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ActivityService {

    /**
     * Get a list of all public activity for the entire Trakt community. The most recent 100
     * activities are returned for all types and actions. You can customize the activity stream with
     * only the types and actions you need.
     */
    @POST("/activity/community/{apikey}")
    Activity community();

    /**
     * Get a list of all activity for a single user. The most recent 100 activities are returned for
     * all types and actions. You can customize the activity stream with only the types and actions
     * you need.
     */
    @GET("/activity/user.json/{apikey}/{username}")
    Activity user(
            @Path("username") String username
    );

    /**
     * Get a list of all activity for a single user. The most recent 100 activities are returned for
     * all types and actions. You can customize the activity stream with only the types and actions
     * you need.
     *
     * @param minimal Set to 1 to return only minimal information like the timestamp, type, action,
     *                username, and IDs. Set to null otherwise.
     * @param images  Set to 1 to include images. Set to null otherwise.
     */
    @GET("/activity/user.json/{apikey}/{username}")
    Activity user(
            @Path("username") String username,
            @Query("min") Integer minimal,
            @Query("images") Integer images
    );

    /**
     * Get a list of all activity for a single user. The most recent 100 activities are returned for
     * all types and actions. You can customize the activity stream with only the types and actions
     * you need.
     *
     * @param types   Send a comma delimited list of types. For supported types see <a
     *                href="http://trakt.tv/api-docs/activity-user">trakt</a>.
     * @param actions Send a comma delimited list of actions. For supported types see <a
     *                href="http://trakt.tv/api-docs/activity-user">trakt</a>.
     * @param minimal Set to 1 to return only minimal information like the timestamp, type, action,
     *                username, and IDs. Set to null otherwise.
     * @param images  Set to 1 to include images. Set to null otherwise.
     */
    @GET("/activity/user.json/{apikey}/{username}/{types}/{actions}")
    Activity user(
            @Path("username") String username,
            @EncodedPath("types") String types,
            @EncodedPath("actions") String actions,
            @Query("min") Integer minimal,
            @Query("images") Integer images
    );

    /**
     * Get a list of all activity for a single user. The most recent 100 activities are returned for
     * all types and actions. You can customize the activity stream with only the types and actions
     * you need.
     *
     * @param types   Send a comma delimited list of types. For supported types see <a
     *                href="http://trakt.tv/api-docs/activity-user">trakt</a>.
     * @param actions Send a comma delimited list of actions. For supported types see <a
     *                href="http://trakt.tv/api-docs/activity-user">trakt</a>.
     * @param minimal Set to 1 to return only minimal information like the timestamp, type, action,
     *                username, and IDs. Set to null otherwise.
     * @param images  Set to 1 to include images. Set to null otherwise.
     */
    @GET("/activity/user.json/{apikey}/{username}/{types}/{actions}/{start_ts}")
    Activity user(
            @Path("username") String username,
            @EncodedPath("types") String types,
            @EncodedPath("actions") String actions,
            @Path("start_ts") Long start_ts,
            @Query("min") Integer minimal,
            @Query("images") Integer images
    );

    /**
     * Get a list of all activity for a single user. The most recent 100 activities are returned for
     * all types and actions. You can customize the activity stream with only the types and actions
     * you need.
     *
     * @param types   Send a comma delimited list of types. For supported types see <a
     *                href="http://trakt.tv/api-docs/activity-user">trakt</a>.
     * @param actions Send a comma delimited list of actions. For supported types see <a
     *                href="http://trakt.tv/api-docs/activity-user">trakt</a>.
     * @param minimal Set to 1 to return only minimal information like the timestamp, type, action,
     *                username, and IDs. Set to null otherwise.
     * @param images  Set to 1 to include images. Set to null otherwise.
     */
    @GET("/activity/user.json/{apikey}/{username}/{types}/{actions}/{start_ts}/{end_ts}")
    Activity user(
            @Path("username") String username,
            @EncodedPath("types") String types,
            @EncodedPath("actions") String actions,
            @Path("start_ts") Long start_ts,
            @Path("end_ts") Long end_ts,
            @Query("min") Integer minimal,
            @Query("images") Integer images
    );

}
