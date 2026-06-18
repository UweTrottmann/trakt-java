/*
 * Copyright 2026 Uwe Trottmann <uwe@uwetrottmann.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.enums;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumTest {

    private static final String TEST_VALUE_INVALID = "invalid";

    // Note: E extends TraktEnum is not technically necessary, but somewhat guarantees that toString is equal to value
    private <E extends Enum<E> & TraktEnum> void testStringEnumFromValue(E[] values, Function<String, E> fromValue) {
        for (E value : values) {
            assertThat(fromValue.apply(value.toString())).isEqualTo(value);
        }

        assertThat(fromValue.apply(TEST_VALUE_INVALID)).isNull();
    }

    @Test
    public void audio_fromValue() {
        testStringEnumFromValue(Audio.values(), Audio::fromValue);
    }

    @Test
    public void audioChannels_fromValue() {
        testStringEnumFromValue(AudioChannels.values(), AudioChannels::fromValue);
    }

    @Test
    public void hdr_fromValue() {
        testStringEnumFromValue(Hdr.values(), Hdr::fromValue);
    }

    @Test
    public void mediaType_fromValue() {
        testStringEnumFromValue(MediaType.values(), MediaType::fromValue);
    }

    @Test
    public void progressLastActivity_fromValue() {
        testStringEnumFromValue(ProgressLastActivity.values(), ProgressLastActivity::fromValue);
    }

    @Test
    public void rating_fromValue() {
        Rating[] values = Rating.values();
        for (Rating rating : values) {
            assertThat(Rating.fromValue(rating.value)).isEqualTo(rating);
        }

        assertThat(Rating.fromValue(0)).isNull();
        assertThat(Rating.fromValue(11)).isNull();
    }

    @Test
    public void resolution_fromValue() {
        testStringEnumFromValue(Resolution.values(), Resolution::fromValue);
    }

    @Test
    public void status_fromValue() {
        testStringEnumFromValue(Status.values(), Status::fromValue);
    }
}