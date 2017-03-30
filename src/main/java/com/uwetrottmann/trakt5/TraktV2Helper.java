package com.uwetrottmann.trakt5;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.uwetrottmann.trakt5.enums.ListPrivacy;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.Status;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TraktV2Helper {

    private static class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

        private final SimpleDateFormat iso8601WithMillis;

        private DateTypeAdapter() {
            iso8601WithMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            iso8601WithMillis.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            synchronized (iso8601WithMillis) {
                try {
                    return iso8601WithMillis.parse(json.getAsString());
                } catch (ParseException e) {
                    throw new JsonSyntaxException(json.getAsString(), e);
                }
            }
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            synchronized (iso8601WithMillis) {
                return new JsonPrimitive(iso8601WithMillis.format(src));
            }
        }
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        // trakt exclusively uses ISO 8601 dates with milliseconds in Zulu time (UTC)
        builder.registerTypeAdapter(Date.class, new DateTypeAdapter());

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
