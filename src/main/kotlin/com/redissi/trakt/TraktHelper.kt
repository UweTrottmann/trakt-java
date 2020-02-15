package com.redissi.trakt

import com.google.gson.*
import com.redissi.moshi.adapter.iso8601.jdk8.LocalDateAdapter
import com.redissi.moshi.adapter.iso8601.jdk8.OffsetDateTimeAdapter
import com.squareup.moshi.Moshi
import com.uwetrottmann.trakt5.enums.*
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import java.lang.reflect.Type

object TraktHelper {

    val moshiBuilder: Moshi.Builder = Moshi.Builder()
            .add(OffsetDateTimeAdapter())
            .add(LocalDateAdapter())

    // trakt exclusively uses ISO 8601 date times with milliseconds and time zone offset
// such as '2011-12-03T10:15:30.000+01:00' or '2011-12-03T10:15:30.000Z'
    // dates are in ISO 8601 format as well
    // privacy
    // rating
    // sort by
    // sort how
    // status
    val gsonBuilder: GsonBuilder
        get() {
            val builder = GsonBuilder()
            // trakt exclusively uses ISO 8601 date times with milliseconds and time zone offset
// such as '2011-12-03T10:15:30.000+01:00' or '2011-12-03T10:15:30.000Z'
            builder.registerTypeAdapter(OffsetDateTime::class.java,
                    JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? -> OffsetDateTime.parse(json.asString) } as JsonDeserializer<OffsetDateTime>)
            builder.registerTypeAdapter(OffsetDateTime::class.java,
                    JsonSerializer { src: OffsetDateTime, typeOfSrc: Type?, context: JsonSerializationContext? -> JsonPrimitive(src.toString()) } as JsonSerializer<OffsetDateTime>)
            // dates are in ISO 8601 format as well
            builder.registerTypeAdapter(LocalDate::class.java,
                    JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? -> LocalDate.parse(json.asString) } as JsonDeserializer<LocalDate>)
            // privacy
            builder.registerTypeAdapter(ListPrivacy::class.java,
                    JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? -> ListPrivacy.fromValue(json.asString) } as JsonDeserializer<ListPrivacy>)
            // rating
            builder.registerTypeAdapter(Rating::class.java,
                    JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? -> Rating.fromValue(json.asInt) } as JsonDeserializer<Rating>)
            builder.registerTypeAdapter(Rating::class.java,
                    JsonSerializer { src: Rating, typeOfSrc: Type?, context: JsonSerializationContext? -> JsonPrimitive(src.value) } as JsonSerializer<Rating>)
            // sort by
            builder.registerTypeAdapter(SortBy::class.java,
                    JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? -> SortBy.fromValue(json.asString) } as JsonDeserializer<SortBy>)
            // sort how
            builder.registerTypeAdapter(SortHow::class.java,
                    JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? -> SortHow.fromValue(json.asString) } as JsonDeserializer<SortHow>)
            // status
            builder.registerTypeAdapter(Status::class.java,
                    JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? -> Status.fromValue(json.asString) } as JsonDeserializer<Status>)
            return builder
        }
}