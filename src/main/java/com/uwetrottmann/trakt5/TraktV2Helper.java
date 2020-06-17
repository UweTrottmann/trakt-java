package com.uwetrottmann.trakt5;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.uwetrottmann.trakt5.enums.ProgressLastActivity;
import com.uwetrottmann.trakt5.enums.ListPrivacy;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.SortBy;
import com.uwetrottmann.trakt5.enums.SortHow;
import com.uwetrottmann.trakt5.enums.Status;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

public class TraktV2Helper {

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // Note: for enums always add a serializer, GSON does not use .toString() by default like retrofit!
        // Or use @SerializedName as an alternative.

        // trakt exclusively uses ISO 8601 date times with milliseconds and time zone offset
        // such as '2011-12-03T10:15:30.000+01:00' or '2011-12-03T10:15:30.000Z'
        builder.registerTypeAdapter(OffsetDateTime.class,
                (JsonDeserializer<OffsetDateTime>) (json, typeOfT, context) -> OffsetDateTime.parse(json.getAsString()));
        builder.registerTypeAdapter(OffsetDateTime.class,
                (JsonSerializer<OffsetDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()));
        // dates are in ISO 8601 format as well
        builder.registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(json.getAsString()));

        // rating
        builder.registerTypeAdapter(Rating.class,
                (JsonDeserializer<Rating>) (json, typeOfT, context) -> Rating.fromValue(json.getAsInt()));
        builder.registerTypeAdapter(Rating.class,
                (JsonSerializer<Rating>) (src, typeOfSrc, context) -> new JsonPrimitive(src.value));

        // sort by
        builder.registerTypeAdapter(SortBy.class,
                (JsonDeserializer<SortBy>) (json, typeOfT, context) -> SortBy.fromValue(json.getAsString()));

        // sort how
        builder.registerTypeAdapter(SortHow.class,
                (JsonDeserializer<SortHow>) (json, typeOfT, context) -> SortHow.fromValue(json.getAsString()));

        // status
        builder.registerTypeAdapter(Status.class,
                (JsonDeserializer<Status>) (json, typeOfT, context) -> Status.fromValue(json.getAsString()));

        // progress last activity
        builder.registerTypeAdapter(ProgressLastActivity.class,
                (JsonDeserializer<ProgressLastActivity>) (json, typeOfT, context) -> ProgressLastActivity.fromValue(json.getAsString()));

        return builder;
    }

}
