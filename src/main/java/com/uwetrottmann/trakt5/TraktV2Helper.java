package com.uwetrottmann.trakt5;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.uwetrottmann.trakt5.enums.ListPrivacy;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.Status;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

import java.lang.reflect.Type;

public class TraktV2Helper {

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // trakt exclusively uses ISO 8601 date times with milliseconds and time zone offset
        // such as '2011-12-03T10:15:30.000+01:00' or '2011-12-03T10:15:30.000Z'
        builder.registerTypeAdapter(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return OffsetDateTime.parse(json.getAsString());
            }
        });
        builder.registerTypeAdapter(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
            @Override
            public JsonElement serialize(OffsetDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.toString());
            }
        });
        // dates are in ISO 8601 format as well
        builder.registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return LocalDate.parse(json.getAsString());
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
