package com.uwetrottmann.trakt.v2;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TraktV2Helper {

    private static final SimpleDateFormat ISO_8601_WITH_MILLIS;

    static {
        ISO_8601_WITH_MILLIS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        ISO_8601_WITH_MILLIS.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // trakt exclusively uses ISO 8601 dates with milliseconds in Zulu time (UTC)
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                String value = json.getAsString();
                try {
                    return ISO_8601_WITH_MILLIS.parse(value);
                } catch (ParseException e) {
                    return null;
                }
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
                if (date == null) {
                    return null;
                }
                return new JsonPrimitive(ISO_8601_WITH_MILLIS.format(date));
            }
        });

        return builder;
    }

}
