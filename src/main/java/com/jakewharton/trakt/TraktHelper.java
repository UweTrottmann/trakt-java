package com.jakewharton.trakt;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import com.jakewharton.trakt.entities.ActivityItem;
import com.jakewharton.trakt.entities.ActivityItemBase;
import com.jakewharton.trakt.entities.TvShowEpisode;
import com.jakewharton.trakt.entities.TvShowSeason;
import com.jakewharton.trakt.enumerations.ActivityAction;
import com.jakewharton.trakt.enumerations.ActivityType;
import com.jakewharton.trakt.enumerations.DayOfTheWeek;
import com.jakewharton.trakt.enumerations.ExtendedParam;
import com.jakewharton.trakt.enumerations.Gender;
import com.jakewharton.trakt.enumerations.ListItemType;
import com.jakewharton.trakt.enumerations.ListPrivacy;
import com.jakewharton.trakt.enumerations.MediaType;
import com.jakewharton.trakt.enumerations.Rating;
import com.jakewharton.trakt.enumerations.RatingType;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public abstract class TraktHelper {

    /**
     * Format for decoding JSON dates in string format.
     */
    private static final SimpleDateFormat JSON_STRING_DATE = new SimpleDateFormat("yyy-MM-dd");

    /**
     * Time zone for Trakt dates.
     */
    private static final TimeZone TRAKT_TIME_ZONE = TimeZone.getTimeZone("GMT-8:00");

    /**
     * Create a {@link GsonBuilder} and register all of the custom types needed in order to properly
     * deserialize complex Trakt-specific type.
     *
     * @return Assembled GSON builder instance.
     */
    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        //class types
        builder.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
            @Override
            public Integer deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                try {
                    return Integer.valueOf(json.getAsInt());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                try {
                    long value = json.getAsLong();
                    Calendar date = Calendar.getInstance(TRAKT_TIME_ZONE);
                    date.setTimeInMillis(value * TraktApiBuilder.MILLISECONDS_IN_SECOND);
                    return date.getTime();
                } catch (NumberFormatException outer) {
                    try {
                        return JSON_STRING_DATE.parse(json.getAsString());
                    } catch (ParseException inner) {
                        throw new JsonParseException(outer);
                    }
                }
            }
        });
        builder.registerTypeAdapter(Calendar.class, new JsonDeserializer<Calendar>() {
            @Override
            public Calendar deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                Calendar value = Calendar.getInstance(TRAKT_TIME_ZONE);
                value.setTimeInMillis(json.getAsLong() * TraktApiBuilder.MILLISECONDS_IN_SECOND);
                return value;
            }
        });
        builder.registerTypeAdapter(TvShowSeason.Episodes.class,
                new JsonDeserializer<TvShowSeason.Episodes>() {
                    @Override
                    public TvShowSeason.Episodes deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
                        TvShowSeason.Episodes episodes = new TvShowSeason.Episodes();
                        try {
                            if (json.isJsonArray()) {
                                if (json.getAsJsonArray().get(0).isJsonPrimitive()) {
                                    //Episode number list
                                    Field fieldNumbers = TvShowSeason.Episodes.class
                                            .getDeclaredField("numbers");
                                    fieldNumbers.setAccessible(true);
                                    fieldNumbers.set(episodes, context.deserialize(json,
                                            (new TypeToken<List<Integer>>() {
                                            }).getType()));
                                } else {
                                    //Episode object list
                                    Field fieldList = TvShowSeason.Episodes.class
                                            .getDeclaredField("episodes");
                                    fieldList.setAccessible(true);
                                    fieldList.set(episodes, context.deserialize(json,
                                            (new TypeToken<List<TvShowEpisode>>() {
                                            }).getType()));
                                }
                            } else {
                                //Episode count
                                Field fieldCount = TvShowSeason.Episodes.class
                                        .getDeclaredField("count");
                                fieldCount.setAccessible(true);
                                fieldCount.set(episodes, Integer.valueOf(json.getAsInt()));
                            }
                        } catch (SecurityException e) {
                            throw new JsonParseException(e);
                        } catch (NoSuchFieldException e) {
                            throw new JsonParseException(e);
                        } catch (IllegalArgumentException e) {
                            throw new JsonParseException(e);
                        } catch (IllegalAccessException e) {
                            throw new JsonParseException(e);
                        }
                        return episodes;
                    }
                });
        builder.registerTypeAdapter(ActivityItemBase.class,
                new JsonDeserializer<ActivityItemBase>() {
                    //XXX See: https://groups.google.com/d/topic/traktapi/GQlT9HfAEjw/discussion
                    @Override
                    public ActivityItemBase deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
                        if (json.isJsonArray()) {
                            if (json.getAsJsonArray().size() != 0) {
                                throw new JsonParseException(
                                        "\"watched\" field returned a non-empty array.");
                            }
                            return null;
                        } else {
                            return context.deserialize(json, ActivityItem.class);
                        }
                    }
                });
        //enum types
        builder.registerTypeAdapter(ActivityAction.class, new JsonDeserializer<ActivityAction>() {
            @Override
            public ActivityAction deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return ActivityAction.fromValue(json.getAsString());
            }
        });
        builder.registerTypeAdapter(ActivityType.class, new JsonDeserializer<ActivityType>() {
            @Override
            public ActivityType deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return ActivityType.fromValue(json.getAsString());
            }
        });
        builder.registerTypeAdapter(DayOfTheWeek.class, new JsonDeserializer<DayOfTheWeek>() {
            @Override
            public DayOfTheWeek deserialize(JsonElement arg0, Type arg1,
                    JsonDeserializationContext arg2) throws JsonParseException {
                return DayOfTheWeek.fromValue(arg0.getAsString());
            }
        });
        builder.registerTypeAdapter(ExtendedParam.class, new JsonDeserializer<ExtendedParam>() {
            @Override
            public ExtendedParam deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return ExtendedParam.fromValue(json.getAsString());
            }
        });
        builder.registerTypeAdapter(Gender.class, new JsonDeserializer<Gender>() {
            @Override
            public Gender deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
                    throws JsonParseException {
                return Gender.fromValue(arg0.getAsString());
            }
        });
        builder.registerTypeAdapter(ListItemType.class, new JsonDeserializer<ListItemType>() {
            @Override
            public ListItemType deserialize(JsonElement arg0, Type arg1,
                    JsonDeserializationContext arg2) throws JsonParseException {
                return ListItemType.fromValue(arg0.getAsString());
            }
        });
        builder.registerTypeAdapter(ListPrivacy.class, new JsonDeserializer<ListPrivacy>() {
            @Override
            public ListPrivacy deserialize(JsonElement arg0, Type arg1,
                    JsonDeserializationContext arg2) throws JsonParseException {
                return ListPrivacy.fromValue(arg0.getAsString());
            }
        });
        builder.registerTypeAdapter(MediaType.class, new JsonDeserializer<MediaType>() {
            @Override
            public MediaType deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return MediaType.fromValue(json.getAsString());
            }
        });
        builder.registerTypeAdapter(Rating.class, new JsonDeserializer<Rating>() {
            @Override
            public Rating deserialize(JsonElement json, Type typeOfT,
                    JsonDeserializationContext context) throws JsonParseException {
                return Rating.fromValue(json.getAsString());
            }
        });
        builder.registerTypeAdapter(Rating.class, new JsonSerializer<Rating>() {
            @Override
            public JsonElement serialize(Rating src, Type typeOfSrc,
                    JsonSerializationContext context) {
                return new JsonPrimitive(src.toString());
            }
        });
        builder.registerTypeAdapter(RatingType.class, new JsonDeserializer<RatingType>() {
            @Override
            public RatingType deserialize(JsonElement arg0, Type arg1,
                    JsonDeserializationContext arg2) throws JsonParseException {
                return RatingType.fromValue(arg0.getAsString());
            }
        });

        return builder;
    }
}
