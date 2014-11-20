package com.uwetrottmann.trakt.v2;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.uwetrottmann.trakt.v2.enums.ListPrivacy;
import com.uwetrottmann.trakt.v2.enums.Rating;
import com.uwetrottmann.trakt.v2.enums.Status;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

public class TraktV2Helper {

    private static final DateTimeFormatter ISO_8601_WITH_MILLIS;

    static {
        ISO_8601_WITH_MILLIS = ISODateTimeFormat.dateTimeParser().withZoneUTC();
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // trakt exclusively uses ISO 8601 dates with milliseconds in Zulu time (UTC)
        builder.registerTypeAdapter(DateTime.class, new JsonDeserializer<DateTime>() {
            @Override
            public DateTime deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                // using the correct parser right away should save init time compared to new DateTime(<string>)
                return ISO_8601_WITH_MILLIS.parseDateTime(json.getAsString());
            }
        });
        builder.registerTypeAdapter(DateTime.class, new JsonSerializer<DateTime>() {
            @Override
            public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.toString());
            }
        });

        // privacy
        builder.registerTypeAdapter(ListPrivacy.class, new JsonDeserializer<ListPrivacy>() {
            @Override
            public ListPrivacy deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return ListPrivacy.fromValue(json.getAsString());
            }
        });

        // rating
        builder.registerTypeAdapter(Rating.class, new JsonDeserializer<Rating>() {
            @Override
            public Rating deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return Rating.fromValue(json.getAsInt());
            }
        });
        builder.registerTypeAdapter(Rating.class, new JsonSerializer<Rating>() {
            @Override
            public JsonElement serialize(Rating src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.value);
            }
        });

        // status
        builder.registerTypeAdapter(Status.class, new JsonDeserializer<Status>() {
            @Override
            public Status deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return Status.fromValue(json.getAsString());
            }
        });

        return builder;
    }

}
